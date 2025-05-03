package co.uniminuto.gestionestudiantes.presentation.estudiante;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

import java.util.List;

import co.uniminuto.gestionestudiantes.R;
import co.uniminuto.gestionestudiantes.core.database.DatabaseHelper;
import co.uniminuto.gestionestudiantes.core.network.EstudianteApiService;
import co.uniminuto.gestionestudiantes.data.models.Estudiante;
import co.uniminuto.gestionestudiantes.data.repositories.EstudianteRepositoryImpl;
import co.uniminuto.gestionestudiantes.domain.repositories.EstudianteRepository;
import co.uniminuto.gestionestudiantes.domain.usecases.estudiante.AddEstudianteUseCase;
import co.uniminuto.gestionestudiantes.domain.usecases.estudiante.GetEstudiantesUseCase;
import co.uniminuto.gestionestudiantes.domain.usecases.estudiante.GetEstudianteByCodigoUseCase;
import co.uniminuto.gestionestudiantes.domain.usecases.estudiante.LoadEstudiantesFromApiUseCase;

/**
 * EstudianteActivity permite agregar nuevos estudiantes, listar estudiantes existentes
 * y cargar datos desde un servicio API externo.
 */
public class EstudianteActivity extends AppCompatActivity {

    private EditText etNombre;
    private EditText etApellido;
    private EditText etCodigo;
    private EditText etCorreo;
    private Button btnGuardar;
    private Button btnListar;
    private Button btnCargarDesdeApi;
    private ListView lvEstudiantes;
    private ProgressBar progressBar;

    private AddEstudianteUseCase addEstudianteUseCase;
    private GetEstudiantesUseCase getEstudiantesUseCase;
    private LoadEstudiantesFromApiUseCase loadEstudiantesFromApiUseCase;
    private GetEstudianteByCodigoUseCase getEstudianteByCodigoUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);

        configurarInsets();
        inicializarVistas();
        inicializarDependencias();
        configurarEventos();
    }

    /**
     * Configura los márgenes de la vista principal respetando las barras del sistema.
     */
    private void configurarInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    /**
     * Inicializa las vistas de la pantalla.
     */
    private void inicializarVistas() {
        etNombre         = findViewById(R.id.etNombreEstudiante);
        etApellido       = findViewById(R.id.etApellidoEstudiante);
        etCodigo         = findViewById(R.id.etCodigoEstudiante);
        etCorreo         = findViewById(R.id.etCorreoEstudiante);
        btnGuardar       = findViewById(R.id.btnGuardarEstudiante);
        btnListar        = findViewById(R.id.btnListarEstudiantes);
        btnCargarDesdeApi= findViewById(R.id.btnCargarDesdeApi);
        lvEstudiantes    = findViewById(R.id.listViewEstudiantes);
        progressBar      = findViewById(R.id.progressBar);
    }

    /**
     * Inicializa las dependencias necesarias de la base de datos y servicios API.
     */
    private void inicializarDependencias() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        EstudianteRepository estudianteRepository = new EstudianteRepositoryImpl(dbHelper);
        addEstudianteUseCase = new AddEstudianteUseCase(estudianteRepository);
        getEstudiantesUseCase = new GetEstudiantesUseCase(estudianteRepository);
        loadEstudiantesFromApiUseCase = new LoadEstudiantesFromApiUseCase(new EstudianteApiService());
        getEstudianteByCodigoUseCase = new GetEstudianteByCodigoUseCase(estudianteRepository);
    }

    /**
     * Configura los listeners de los botones.
     */
    private void configurarEventos() {
        btnGuardar.setOnClickListener(v -> guardarEstudiante());
        btnListar.setOnClickListener(v -> listarEstudiantes());
        btnCargarDesdeApi.setOnClickListener(v -> cargarDesdeApi());
    }

    /**
     * Guarda un nuevo estudiante en la base de datos tras validar los datos ingresados.
     * Si el código ya existe, muestra un mensaje de error.
     */
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

    /**
     * Valida los campos ingresados en el formulario.
     *
     * @return true si son válidos, false si hay errores.
     */
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

    /**
     * Lista todos los estudiantes registrados en la base de datos.
     * Muestra un loader mientras se consulta.
     */
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

    /**
     * Carga estudiantes desde un servicio web y los guarda en la base de datos local.
     */
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

    /**
     * Limpia los campos de entrada del formulario.
     */
    private void limpiarCampos() {
        etNombre.setText("");
        etApellido.setText("");
        etCodigo.setText("");
        etCorreo.setText("");
        quitarFocus();
    }

    /**
     * Quita el foco de los campos de entrada.
     */
    private void quitarFocus() {
        etNombre.clearFocus();
        etApellido.clearFocus();
        etCodigo.clearFocus();
        etCorreo.clearFocus();
    }

    /**
     * Muestra u oculta el loader de progreso.
     *
     * @param mostrar true para mostrar el loader, false para ocultarlo.
     */
    private void mostrarLoader(boolean mostrar) {
        progressBar.setVisibility(mostrar ? ProgressBar.VISIBLE : ProgressBar.GONE);
        findViewById(R.id.scrollView).setVisibility(mostrar ? ScrollView.GONE : ScrollView.VISIBLE);
    }

    /**
     * Muestra un mensaje breve en pantalla.
     *
     * @param mensaje Texto del mensaje a mostrar.
     */
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
