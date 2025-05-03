package co.uniminuto.gestionestudiantes.presentation.calificacion;

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
import co.uniminuto.gestionestudiantes.data.models.Calificacion;
import co.uniminuto.gestionestudiantes.data.repositories.CalificacionRepositoryImpl;
import co.uniminuto.gestionestudiantes.domain.usecases.calificacion.AddCalificacionUseCase;
import co.uniminuto.gestionestudiantes.domain.usecases.calificacion.GetCalificacionesUseCase;

/**
 * CalificacionActivity permite agregar, listar y validar calificaciones para estudiantes y materias.
 */
public class CalificacionActivity extends AppCompatActivity {

    private EditText etIdEstudiante;
    private EditText etIdMateria;
    private EditText etNota;
    private Button btnGuardar;
    private Button btnListar;
    private ListView lvCalificaciones;

    private AddCalificacionUseCase addCalificacionUseCase;
    private GetCalificacionesUseCase getCalificacionesUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificacion);

        initViews();
        initDependencies();
        setupListeners();
    }

    /**
     * Inicializa las vistas de la pantalla.
     */
    private void initViews() {
        etIdEstudiante = findViewById(R.id.etIdEstudiante);
        etIdMateria = findViewById(R.id.etIdMateria);
        etNota = findViewById(R.id.etNota);
        btnGuardar = findViewById(R.id.btnGuardarCalificacion);
        btnListar = findViewById(R.id.btnBuscarCalificacion);
        lvCalificaciones = findViewById(R.id.lvCalificaciones);
    }

    /**
     * Inicializa las dependencias necesarias para la operación.
     */
    private void initDependencies() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        CalificacionRepositoryImpl calificacionRepository = new CalificacionRepositoryImpl(dbHelper);
        addCalificacionUseCase = new AddCalificacionUseCase(calificacionRepository);
        getCalificacionesUseCase = new GetCalificacionesUseCase(calificacionRepository);
    }

    /**
     * Configura los listeners de los botones de acción.
     */
    private void setupListeners() {
        btnGuardar.setOnClickListener(v -> guardarCalificacion());
        btnListar.setOnClickListener(v -> listarCalificaciones());
    }

    /**
     * Guarda una nueva calificación después de validar los campos.
     */
    private void guardarCalificacion() {
        String idEstudianteStr = etIdEstudiante.getText().toString().trim();
        String idMateriaStr = etIdMateria.getText().toString().trim();
        String notaStr = etNota.getText().toString().trim();

        if (!validarCampos(idEstudianteStr, idMateriaStr, notaStr)) {
            return;
        }

        try {
            int idEstudiante = Integer.parseInt(idEstudianteStr);
            int idMateria = Integer.parseInt(idMateriaStr);
            double nota = Double.parseDouble(notaStr);

            Calificacion calificacion = new Calificacion();
            calificacion.setIdEstudiante(idEstudiante);
            calificacion.setIdMateria(idMateria);
            calificacion.setNota(nota);

            boolean success = addCalificacionUseCase.execute(calificacion);

            mostrarMensaje(success ? "Calificación guardada exitosamente" : "Error al guardar calificación");

            if (success) {
                limpiarCampos();
                quitarFocus();
                cerrarTeclado();
            }

        } catch (NumberFormatException e) {
            mostrarMensaje("IDs y nota deben ser valores numéricos válidos");
        }
    }

    /**
     * Valida los campos de entrada antes de guardar la calificación.
     *
     * @return true si los campos son válidos, false de lo contrario.
     */
    private boolean validarCampos(String idEstudianteStr, String idMateriaStr, String notaStr) {
        if (idEstudianteStr.isEmpty() || idMateriaStr.isEmpty() || notaStr.isEmpty()) {
            mostrarMensaje("Completa todos los campos");
            return false;
        }

        try {
            int idEstudiante = Integer.parseInt(idEstudianteStr);
            int idMateria = Integer.parseInt(idMateriaStr);
            double nota = Double.parseDouble(notaStr);

            if (idEstudiante <= 0 || idMateria <= 0) {
                mostrarMensaje("IDs deben ser números positivos");
                return false;
            }

            if (nota < 0 || nota > 5) {
                mostrarMensaje("La nota debe estar entre 0 y 5");
                return false;
            }

        } catch (NumberFormatException e) {
            mostrarMensaje("IDs y nota deben ser valores numéricos válidos");
            return false;
        }

        return true;
    }

    /**
     * Lista todas las calificaciones registradas.
     */
    private void listarCalificaciones() {
        List<Calificacion> calificaciones = getCalificacionesUseCase.execute();

        if (calificaciones.isEmpty()) {
            mostrarMensaje("No hay calificaciones registradas");
            return;
        }

        CalificacionAdapter adapter = new CalificacionAdapter(this, calificaciones);
        lvCalificaciones.setAdapter(adapter);
    }

    /**
     * Limpia los campos de entrada.
     */
    private void limpiarCampos() {
        etIdEstudiante.setText("");
        etIdMateria.setText("");
        etNota.setText("");
    }

    /**
     * Quita el foco de los campos de entrada.
     */
    private void quitarFocus() {
        etIdEstudiante.clearFocus();
        etIdMateria.clearFocus();
        etNota.clearFocus();
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
     * @param mensaje Mensaje a mostrar.
     */
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
