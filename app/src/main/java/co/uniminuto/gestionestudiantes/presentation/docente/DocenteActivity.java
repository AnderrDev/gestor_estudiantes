package co.uniminuto.gestionestudiantes.presentation.docente;

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
import co.uniminuto.gestionestudiantes.data.models.Docente;
import co.uniminuto.gestionestudiantes.data.repositories.DocenteRepositoryImpl;
import co.uniminuto.gestionestudiantes.domain.usecases.docente.AddDocenteUseCase;
import co.uniminuto.gestionestudiantes.domain.usecases.docente.GetDocentesUseCase;

/**
 * DocenteActivity permite agregar, validar y listar docentes registrados en la base de datos local.
 */
public class DocenteActivity extends AppCompatActivity {

    private EditText etDocumento;
    private EditText etNombre;
    private EditText etCorreo;
    private Button btnGuardar;
    private Button btnListar;
    private ListView lvDocentes;

    private AddDocenteUseCase addDocenteUseCase;
    private GetDocentesUseCase getDocentesUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);

        initViews();
        initDependencies();
        setupListeners();
    }

    /**
     * Inicializa las vistas de la pantalla.
     */
    private void initViews() {
        etDocumento = findViewById(R.id.etDocumentoDocente);
        etNombre = findViewById(R.id.etNombreDocente);
        etCorreo = findViewById(R.id.etCorreoDocente);
        btnGuardar = findViewById(R.id.btnGuardarDocente);
        btnListar = findViewById(R.id.btnListarDocentes);
        lvDocentes = findViewById(R.id.lvDocentes);
    }

    /**
     * Inicializa las dependencias necesarias para operar los casos de uso.
     */
    private void initDependencies() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        DocenteRepositoryImpl docenteRepository = new DocenteRepositoryImpl(dbHelper);
        addDocenteUseCase = new AddDocenteUseCase(docenteRepository);
        getDocentesUseCase = new GetDocentesUseCase(docenteRepository);
    }

    /**
     * Configura los listeners de botones para las acciones principales.
     */
    private void setupListeners() {
        btnGuardar.setOnClickListener(v -> guardarDocente());
        btnListar.setOnClickListener(v -> listarDocentes());
    }

    /**
     * Guarda un nuevo docente luego de validar los campos ingresados.
     */
    private void guardarDocente() {
        String documento = etDocumento.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();

        if (!validarCampos(documento, nombre, correo)) {
            return;
        }

        Docente docente = new Docente();
        docente.setDocumento(documento);
        docente.setNombre(nombre);
        docente.setCorreo(correo);

        boolean success = addDocenteUseCase.execute(docente);

        mostrarMensaje(success ? "Docente guardado exitosamente" : "Error al guardar docente");

        if (success) {
            limpiarCampos();
            quitarFocus();
            cerrarTeclado();
        }
    }

    /**
     * Valida los campos ingresados en el formulario.
     *
     * @return true si los campos son válidos, false si hay errores.
     */
    private boolean validarCampos(String documento, String nombre, String correo) {
        if (documento.isEmpty() || nombre.isEmpty() || correo.isEmpty()) {
            mostrarMensaje("Completa todos los campos");
            return false;
        }

        if (!documento.matches("[a-zA-Z0-9]+")) {
            mostrarMensaje("El documento debe ser alfanumérico");
            return false;
        }

        if (nombre.length() < 2 || nombre.length() > 50) {
            mostrarMensaje("El nombre debe tener entre 2 y 50 caracteres");
            return false;
        }

        if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            mostrarMensaje("El correo electrónico no es válido");
            return false;
        }

        return true;
    }

    /**
     * Lista todos los docentes registrados.
     */
    private void listarDocentes() {
        List<Docente> docentes = getDocentesUseCase.execute();

        if (docentes.isEmpty()) {
            mostrarMensaje("No hay docentes registrados");
            return;
        }

        DocenteAdapter adapter = new DocenteAdapter(this, docentes);
        lvDocentes.setAdapter(adapter);
    }

    /**
     * Limpia los campos de entrada.
     */
    private void limpiarCampos() {
        etDocumento.setText("");
        etNombre.setText("");
        etCorreo.setText("");
    }

    /**
     * Quita el foco de los campos de entrada.
     */
    private void quitarFocus() {
        etDocumento.clearFocus();
        etNombre.clearFocus();
        etCorreo.clearFocus();
    }

    /**
     * Cierra el teclado virtual si está abierto.
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
     * @param mensaje Texto del mensaje a mostrar.
     */
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
