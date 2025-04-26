package co.uniminuto.gestionestudiantes;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import co.uniminuto.gestionestudiantes.database.DBHelper;
import co.uniminuto.gestionestudiantes.models.Materia;

import java.util.ArrayList;
import java.util.List;

public class MateriaActivity extends AppCompatActivity {

    private EditText etCodigo, etNombre;
    private Button btnGuardar, btnBuscar;
    private ListView lvMaterias;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_materia);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // Inicializar vistas
        etCodigo  = findViewById(R.id.etCodigoMateria);
        etNombre  = findViewById(R.id.etNombreMateria);
        btnGuardar = findViewById(R.id.btnGuardarMateria);
        btnBuscar  = findViewById(R.id.btnListarMaterias);
        lvMaterias = findViewById(R.id.lvMaterias);

        // Base de datos
        dbHelper = new DBHelper(this);

        // Listener
        btnGuardar.setOnClickListener(v -> guardarMateria());
        btnBuscar.setOnClickListener(v -> buscarMaterias());
    }

    private void guardarMateria() {
        String codigo = etCodigo.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();

        if (codigo.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(this, "Completa ambos campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Materia m = new Materia();
        m.setCodigo(codigo);
        m.setNombre(nombre);
        boolean ok = dbHelper.insertarMateria(m);

        Toast.makeText(this,
                ok ? "Materia guardada" : "Error al guardar materia",
                Toast.LENGTH_SHORT
        ).show();

        if (ok) {
            etCodigo.setText("");
            etNombre.setText("");
        }
    }

    private void buscarMaterias() {
        List<Materia> lista = dbHelper.obtenerMaterias();
        List<String> items = new ArrayList<>();

        for (Materia m : lista) {
            items.add(m.getCodigo() + " - " + m.getNombre());
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvMaterias.setAdapter(adapter);
    }

}