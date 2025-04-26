package co.uniminuto.gestionestudiantes;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import co.uniminuto.gestionestudiantes.database.DBHelper;
import co.uniminuto.gestionestudiantes.models.Docente;

import java.util.ArrayList;
import java.util.List;

public class DocenteActivity extends AppCompatActivity {
    private EditText etDocumento, etNombre, etCorreo;
    private Button btnGuardar, btnListar;
    private ListView lvDocentes;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);  // Asegúrate que este nombre coincide

        // Vincular vistas
        etDocumento = findViewById(R.id.etDocumentoDocente);
        etNombre    = findViewById(R.id.etNombreDocente);
        etCorreo    = findViewById(R.id.etCorreoDocente);
        btnGuardar  = findViewById(R.id.btnGuardarDocente);
        btnListar   = findViewById(R.id.btnListarDocentes);
        lvDocentes  = findViewById(R.id.lvDocentes);

        dbHelper = new DBHelper(this);

        // Listener para Guardar
        btnGuardar.setOnClickListener(v -> {
            String documento = etDocumento.getText().toString().trim();
            String nombre    = etNombre.getText().toString().trim();
            String correo    = etCorreo.getText().toString().trim();

            if (documento.isEmpty() || nombre.isEmpty() || correo.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Docente d = new Docente();
            d.setDocumento(documento);
            d.setNombre(nombre);
            d.setCorreo(correo);

            boolean ok = dbHelper.insertarDocente(d);
            Toast.makeText(this,
                    ok ? "Docente guardado" : "Error al guardar docente",
                    Toast.LENGTH_SHORT
            ).show();

            if (ok) {
                etDocumento.setText("");
                etNombre.setText("");
                etCorreo.setText("");
            }
        });

        // Listener para Listar
        btnListar.setOnClickListener(v -> {
            List<Docente> lista = dbHelper.obtenerDocentes();
            List<String> items = new ArrayList<>();
            for (Docente d : lista) {
                items.add(d.getDocumento() + " - " + d.getNombre() + " (" + d.getCorreo() + ")");
            }
            lvDocentes.setAdapter(
                    new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items)
            );
        });
    }
}