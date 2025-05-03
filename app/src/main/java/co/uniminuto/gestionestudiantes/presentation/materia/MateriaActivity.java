package co.uniminuto.gestionestudiantes.presentation.materia;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import co.uniminuto.gestionestudiantes.R;
import co.uniminuto.gestionestudiantes.core.database.DatabaseHelper;
import co.uniminuto.gestionestudiantes.data.models.Materia;
import co.uniminuto.gestionestudiantes.data.repositories.MateriaRepositoryImpl;
import co.uniminuto.gestionestudiantes.domain.usecases.materia.AddMateriaUseCase;
import co.uniminuto.gestionestudiantes.domain.usecases.materia.GetMateriasUseCase;

/**
 * MateriaActivity permite gestionar las materias en la base de datos local.
 * Funcionalidades: agregar nuevas materias y listar todas las materias registradas.
 */
public class MateriaActivity extends AppCompatActivity {

    private EditText etCodigo, etNombre;
    private Button btnGuardar, btnListar;
    private ListView lvMaterias;

    private AddMateriaUseCase addMateriaUseCase;
    private GetMateriasUseCase getMateriasUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);

        initViews();
        initDependencies();
        setupListeners();
    }

    /**
     * Inicializa las referencias a las vistas de la interfaz de usuario.
     */
    private void initViews() {
        etCodigo = findViewById(R.id.etCodigoMateria);
        etNombre = findViewById(R.id.etNombreMateria);
        btnGuardar = findViewById(R.id.btnGuardarMateria);
        btnListar = findViewById(R.id.btnListarMaterias);
        lvMaterias = findViewById(R.id.lvMaterias);
    }

    /**
     * Inicializa las dependencias necesarias para los casos de uso.
     */
    private void initDependencies() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        MateriaRepositoryImpl materiaRepository = new MateriaRepositoryImpl(dbHelper);
        addMateriaUseCase = new AddMateriaUseCase(materiaRepository);
        getMateriasUseCase = new GetMateriasUseCase(materiaRepository);
    }

    /**
     * Configura los eventos de los botones.
     */
    private void setupListeners() {
        btnGuardar.setOnClickListener(v -> guardarMateria());
        btnListar.setOnClickListener(v -> listarMaterias());
    }

    /**
     * Guarda una nueva materia después de validar los campos ingresados.
     */
    private void guardarMateria() {
        String codigo = etCodigo.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();

        if (!validarCampos(codigo, nombre)) {
            return;
        }

        Materia materia = new Materia();
        materia.setCodigo(codigo);
        materia.setNombre(nombre);

        boolean success = addMateriaUseCase.execute(materia);

        mostrarMensaje(success ? "Materia guardada exitosamente" : "Error al guardar materia");

        if (success) {
            limpiarCampos();
            quitarFocus();
            cerrarTeclado();
        }
    }

    /**
     * Valida los datos ingresados para crear una nueva materia.
     *
     * @param codigo Código de la materia
     * @param nombre Nombre de la materia
     * @return true si los campos son válidos, false en caso contrario.
     */
    private boolean validarCampos(String codigo, String nombre) {
        if (codigo.isEmpty() || nombre.isEmpty()) {
            mostrarMensaje("Completa ambos campos");
            return false;
        }

        if (!codigo.matches("[a-zA-Z0-9]+")) {
            mostrarMensaje("El código debe ser alfanumérico");
            return false;
        }

        if (nombre.length() < 2 || nombre.length() > 50) {
            mostrarMensaje("El nombre debe tener entre 2 y 50 caracteres");
            return false;
        }

        return true;
    }

    /**
     * Obtiene todas las materias registradas y las muestra en la lista.
     */
    private void listarMaterias() {
        List<Materia> materias = getMateriasUseCase.execute();

        if (materias.isEmpty()) {
            mostrarMensaje("No hay materias registradas");
            return;
        }

        MateriaAdapter adapter = new MateriaAdapter(this, materias);
        lvMaterias.setAdapter(adapter);
    }

    /**
     * Limpia los campos de texto después de guardar una materia.
     */
    private void limpiarCampos() {
        etCodigo.setText("");
        etNombre.setText("");
    }

    /**
     * Quita el enfoque de los campos de texto.
     */
    private void quitarFocus() {
        etCodigo.clearFocus();
        etNombre.clearFocus();
    }

    /**
     * Cierra el teclado si está abierto.
     */
    private void cerrarTeclado() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Muestra un mensaje breve en pantalla.
     *
     * @param mensaje Texto del mensaje.
     */
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
