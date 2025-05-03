package co.uniminuto.gestionestudiantes.presentation.estudiante;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

import java.util.ArrayList;
import java.util.List;

import co.uniminuto.gestionestudiantes.R;
import co.uniminuto.gestionestudiantes.core.database.DatabaseHelper;
import co.uniminuto.gestionestudiantes.core.network.EstudianteApiService;
import co.uniminuto.gestionestudiantes.data.models.Estudiante;
import co.uniminuto.gestionestudiantes.data.repositories.EstudianteRepositoryImpl;
import co.uniminuto.gestionestudiantes.domain.repositories.EstudianteRepository;
import co.uniminuto.gestionestudiantes.domain.usecases.estudiante.AddEstudianteUseCase;
import co.uniminuto.gestionestudiantes.domain.usecases.estudiante.DeleteEstudianteUseCase;
import co.uniminuto.gestionestudiantes.domain.usecases.estudiante.GetEstudiantesUseCase;
import co.uniminuto.gestionestudiantes.domain.usecases.estudiante.GetEstudianteByCodigoUseCase;
import co.uniminuto.gestionestudiantes.domain.usecases.estudiante.LoadEstudiantesFromApiUseCase;

/**
 * EstudianteActivity gestiona la creación, búsqueda, listado y eliminación de estudiantes.
 */
public class EstudianteActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etCodigo, etCorreo;
    private Button btnGuardar, btnListar, btnCargarDesdeApi, btnBuscar, btnEliminar;
    private ListView lvEstudiantes;
    private ProgressBar progressBar;

    private AddEstudianteUseCase addEstudianteUseCase;
    private GetEstudiantesUseCase getEstudiantesUseCase;
    private LoadEstudiantesFromApiUseCase loadEstudiantesFromApiUseCase;
    private GetEstudianteByCodigoUseCase getEstudianteByCodigoUseCase;
    private DeleteEstudianteUseCase deleteEstudianteUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);

        configurarInsets();
        inicializarVistas();
        inicializarDependencias();
        configurarEventos();
    }

    private void configurarInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    private void inicializarVistas() {
        etNombre          = findViewById(R.id.etNombreEstudiante);
        etApellido        = findViewById(R.id.etApellidoEstudiante);
        etCodigo          = findViewById(R.id.etCodigoEstudiante);
        etCorreo          = findViewById(R.id.etCorreoEstudiante);
        btnGuardar        = findViewById(R.id.btnGuardarEstudiante);
        btnListar         = findViewById(R.id.btnListarEstudiantes);
        btnCargarDesdeApi = findViewById(R.id.btnCargarDesdeApi);
        btnBuscar         = findViewById(R.id.btnBuscarEstudiante);
        btnEliminar       = findViewById(R.id.btnEliminarEstudiante);
        lvEstudiantes     = findViewById(R.id.listViewEstudiantes);
        progressBar       = findViewById(R.id.progressBar);
    }

    private void inicializarDependencias() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        EstudianteRepository estudianteRepository = new EstudianteRepositoryImpl(dbHelper);

        addEstudianteUseCase          = new AddEstudianteUseCase(estudianteRepository);
        getEstudiantesUseCase         = new GetEstudiantesUseCase(estudianteRepository);
        loadEstudiantesFromApiUseCase = new LoadEstudiantesFromApiUseCase(new EstudianteApiService());
        getEstudianteByCodigoUseCase  = new GetEstudianteByCodigoUseCase(estudianteRepository);
        deleteEstudianteUseCase       = new DeleteEstudianteUseCase(estudianteRepository);
    }

    private void configurarEventos() {
        btnGuardar.setOnClickListener(v -> guardarEstudiante());
        btnListar.setOnClickListener(v -> listarEstudiantes());
        btnCargarDesdeApi.setOnClickListener(v -> cargarDesdeApi());
        btnBuscar.setOnClickListener(v -> buscarEstudiante());
        btnEliminar.setOnClickListener(v -> eliminarEstudiante());
    }

    private void guardarEstudiante() {
        String nombre   = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String codigo   = etCodigo.getText().toString().trim();
        String correo   = etCorreo.getText().toString().trim();

        if (!validarCampos(nombre, apellido, codigo, correo)) return;

        if (getEstudianteByCodigoUseCase.execute(codigo) != null) {
            mostrarMensaje("Ya existe un estudiante registrado con ese código");
            return;
        }

        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(nombre);
        estudiante.setApellido(apellido);
        estudiante.setCodigo(codigo);
        estudiante.setEmail(correo);

        boolean success = addEstudianteUseCase.execute(estudiante);
        mostrarMensaje(success ? "Estudiante guardado exitosamente" : "Error al guardar el estudiante");

        if (success) {
            limpiarCampos();
        }
    }

    private boolean validarCampos(String nombre, String apellido, String codigo, String correo) {
        if (nombre.isEmpty() || apellido.isEmpty() || codigo.isEmpty() || correo.isEmpty()) {
            mostrarMensaje("Completa todos los campos");
            return false;
        }

        if (nombre.length() < 2) {
            mostrarMensaje("El nombre debe tener al menos 2 caracteres");
            return false;
        }

        if (apellido.length() < 2) {
            mostrarMensaje("El apellido debe tener al menos 2 caracteres");
            return false;
        }

        if (!correo.contains("@") || !correo.contains(".")) {
            mostrarMensaje("El correo electrónico no es válido");
            return false;
        }

        return true;
    }

    private void listarEstudiantes() {
        mostrarLoader(true);
        new Thread(() -> {
            List<Estudiante> estudiantes = getEstudiantesUseCase.execute();
            runOnUiThread(() -> {
                mostrarLoader(false);
                if (estudiantes.isEmpty()) {
                    mostrarMensaje("No hay estudiantes registrados");
                } else {
                    EstudianteAdapter adapter = new EstudianteAdapter(this, estudiantes);
                    lvEstudiantes.setAdapter(adapter);
                }
            });
        }).start();
    }

    private void cargarDesdeApi() {
        mostrarLoader(true);
        new Thread(() -> {
            try {
                List<Estudiante> estudiantes = loadEstudiantesFromApiUseCase.execute();
                for (Estudiante estudiante : estudiantes) {
                    addEstudianteUseCase.execute(estudiante);
                }
                runOnUiThread(() -> {
                    mostrarMensaje("Datos cargados exitosamente");
                    listarEstudiantes();
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    mostrarMensaje("Error al cargar datos: " + e.getMessage());
                    mostrarLoader(false);
                });
            }
        }).start();
    }

    private void buscarEstudiante() {
        String codigo = etCodigo.getText().toString().trim();

        if (codigo.isEmpty()) {
            mostrarMensaje("Ingrese el código del estudiante para buscar");
            return;
        }

        Estudiante estudiante = getEstudianteByCodigoUseCase.execute(codigo);

        if (estudiante != null) {
            // Crear una lista temporal para mostrar solo el estudiante encontrado
            List<Estudiante> resultado = new ArrayList<>();
            resultado.add(estudiante);

            EstudianteAdapter adapter = new EstudianteAdapter(this, resultado);
            lvEstudiantes.setAdapter(adapter);
            limpiarCampos();
            mostrarMensaje("Estudiante encontrado");
        } else {
            mostrarMensaje("No se encontró ningún estudiante con ese código");
        }
    }


    private void eliminarEstudiante() {
        String codigo = etCodigo.getText().toString().trim();

        if (codigo.isEmpty()) {
            mostrarMensaje("Ingrese el código del estudiante a eliminar");
            return;
        }

        // Mostrar diálogo de confirmación
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que quieres eliminar este estudiante?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    boolean eliminado = deleteEstudianteUseCase.execute(codigo);

                    if (eliminado) {
                        mostrarMensaje("Estudiante eliminado correctamente");
                        limpiarCampos();
                        listarEstudiantes();
                    } else {
                        mostrarMensaje("No se pudo eliminar el estudiante");
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                    mostrarMensaje("Operación cancelada");
                })
                .show();
    }


    private void limpiarCampos() {
        etNombre.setText("");
        etApellido.setText("");
        etCodigo.setText("");
        etCorreo.setText("");
        quitarFocus();
    }

    private void quitarFocus() {
        etNombre.clearFocus();
        etApellido.clearFocus();
        etCodigo.clearFocus();
        etCorreo.clearFocus();
    }

    private void mostrarLoader(boolean mostrar) {
        progressBar.setVisibility(mostrar ? ProgressBar.VISIBLE : ProgressBar.GONE);
        findViewById(R.id.scrollView).setVisibility(mostrar ? ScrollView.GONE : ScrollView.VISIBLE);
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
