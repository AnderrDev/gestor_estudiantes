package co.uniminuto.gestionestudiantes;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import co.uniminuto.gestionestudiantes.api.EstudianteAPI;
import co.uniminuto.gestionestudiantes.database.DBHelper;
import co.uniminuto.gestionestudiantes.models.Estudiante;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EstudianteActivity extends AppCompatActivity {
    private EditText etNombre, etApellido, etCodigo;
    private Button btnGuardar, btnListar, btnCargarDesdeApi;
    private ListView lvEstudiantes;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // Inicializa vistas
        etNombre    = findViewById(R.id.etNombreEstudiante);
        etApellido  = findViewById(R.id.etApellidoEstudiante);
        etCodigo    = findViewById(R.id.etCodigoEstudiante);
        btnGuardar  = findViewById(R.id.btnGuardarEstudiante);
        btnListar   = findViewById(R.id.btnListarEstudiantes);
        btnCargarDesdeApi = findViewById(R.id.btnCargarDesdeApi);
        lvEstudiantes = findViewById(R.id.listViewEstudiantes);

        dbHelper = new DBHelper(this);

        // Guardar un estudiante
        btnGuardar.setOnClickListener(v -> {
            String nom = etNombre.getText().toString();
            String ape = etApellido.getText().toString();
            String cod = etCodigo.getText().toString();
            if (nom.isEmpty() || ape.isEmpty() || cod.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            Estudiante e = new Estudiante();
            e.setNombre(nom);
            e.setApellido(ape);
            e.setCodigo(cod);
            boolean ok = dbHelper.insertarEstudiante(e);
            Toast.makeText(this,
                    ok ? "Estudiante guardado" : "Error al guardar",
                    Toast.LENGTH_SHORT
            ).show();
            if (ok) {
                etNombre.setText("");
                etApellido.setText("");
                etCodigo.setText("");
            }
        });

        // Listar estudiantes
        btnListar.setOnClickListener(v -> listarEstudiantes());

        // Cargar estudiantes desde la API simulada
        btnCargarDesdeApi.setOnClickListener(v -> {
            String url = "https://jsonplaceholder.typicode.com/users"; // API de JSONPlaceholder
            EstudianteAPI.obtenerEstudiantes(url, new EstudianteAPI.EstudianteAPICallback() {
                @Override
                public void onSuccess(String response) {
                    try {
                        // Convierte respuesta JSON a objetos Estudiante
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonEst = jsonArray.getJSONObject(i);
                            Estudiante e = new Estudiante();
                            e.setCodigo(jsonEst.getString("id")); // Asigna el id como código
                            e.setNombre(jsonEst.getString("name")); // Nombre de usuario
                            e.setApellido(jsonEst.getString("username")); // Nombre de usuario
                            e.setEmail(jsonEst.getString("email")); // Asignar correo
                            dbHelper.insertarEstudiante(e); // Guardar en base de datos
                        }

                        //agregar dentro for la lista de la repsuesta de la api
                        Toast.makeText(EstudianteActivity.this, "Datos cargados exitosamente", Toast.LENGTH_SHORT).show();
                        listarEstudiantes(); // Actualiza lista de estudiantes
                    } catch (Exception e) {
                        Toast.makeText(EstudianteActivity.this, "Error al procesar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(EstudianteActivity.this, "Error al conectar con API: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void listarEstudiantes() {
        List<Estudiante> lista = dbHelper.obtenerEstudiantes();
        List<String> items = new ArrayList<>();
        for (Estudiante e : lista) {
            System.out.println(" estudiantes:   "+e);
            items.add(e.getCodigo() + " - " + e.getNombre() + " " + e.getApellido() + " - " + e.getEmail()); // Mostrar correo
        }
        lvEstudiantes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));
    }
}