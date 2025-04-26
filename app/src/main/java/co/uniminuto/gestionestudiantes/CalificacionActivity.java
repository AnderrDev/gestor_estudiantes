package co.uniminuto.gestionestudiantes;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.*;
import co.uniminuto.gestionestudiantes.database.DBHelper;
import co.uniminuto.gestionestudiantes.models.Calificacion;

import java.util.ArrayList;
import java.util.List;

public class CalificacionActivity extends AppCompatActivity {

    private EditText etIdEstudiante, etIdMateria, etNota;
    private Button btnGuardar, btnBuscar;
    private ListView lvCalificaciones;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calificacion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etIdEstudiante = findViewById(R.id.etIdEstudiante);
        etIdMateria    = findViewById(R.id.etIdMateria);
        etNota         = findViewById(R.id.etNota);
        btnGuardar     = findViewById(R.id.btnGuardarCalificacion);
        btnBuscar      = findViewById(R.id.btnBuscarCalificacion);
        lvCalificaciones = findViewById(R.id.lvCalificaciones);

        dbHelper = new DBHelper(this);

        btnGuardar.setOnClickListener(v -> {
            String idEst = etIdEstudiante.getText().toString();
            String idMat = etIdMateria.getText().toString();
            String notaS = etNota.getText().toString();
            if (idEst.isEmpty() || idMat.isEmpty() || notaS.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            Calificacion c = new Calificacion();
            c.setIdEstudiante(Integer.parseInt(idEst));
            c.setIdMateria(Integer.parseInt(idMat));
            c.setNota(Double.parseDouble(notaS));
            boolean ok = dbHelper.insertarCalificacion(c);
            Toast.makeText(this,
                    ok ? "Calificación guardada" : "Error al guardar",
                    Toast.LENGTH_SHORT
            ).show();
            if (ok) {
                etIdEstudiante.setText("");
                etIdMateria.setText("");
                etNota.setText("");
            }
        });

        btnBuscar.setOnClickListener(v -> {
            List<Calificacion> lista = dbHelper.obtenerCalificaciones();
            List<String> items = new ArrayList<>();
            for (Calificacion c : lista) {
                items.add("E:"+c.getIdEstudiante()+" M:"+c.getIdMateria()+" = "+c.getNota());
            }
            lvCalificaciones.setAdapter(
                    new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items)
            );
        });
    }
}