package co.uniminuto.gestionestudiantes.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

import co.uniminuto.gestionestudiantes.R;
import co.uniminuto.gestionestudiantes.core.utils.StorageHelper;
import co.uniminuto.gestionestudiantes.presentation.calificacion.CalificacionActivity;
import co.uniminuto.gestionestudiantes.presentation.docente.DocenteActivity;
import co.uniminuto.gestionestudiantes.presentation.estudiante.EstudianteActivity;
import co.uniminuto.gestionestudiantes.presentation.login.LoginActivity;
import co.uniminuto.gestionestudiantes.presentation.materia.MateriaActivity;

/**
 * MainActivity es la pantalla de inicio después de un login exitoso.
 * Ofrece acceso a la gestión de estudiantes, materias, docentes y calificaciones.
 * Permite también cerrar sesión del usuario actual.
 */
public class MainActivity extends AppCompatActivity {

    private MaterialCardView cardEstudiantes, cardMaterias, cardDocentes, cardCalificaciones;
    private Button btnLogout;
    private StorageHelper storageHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurarInsets();
        storageHelper = new StorageHelper(this);
        initViews();
        setupListeners();
    }

    /**
     * Configura el manejo de insets del sistema para que la UI no choque con la barra de estado o navegación.
     */
    private void configurarInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    /**
     * Inicializa las referencias a las vistas de la interfaz de usuario.
     */
    private void initViews() {
        cardEstudiantes    = findViewById(R.id.cardEstudiantes);
        cardMaterias       = findViewById(R.id.cardMaterias);
        cardDocentes       = findViewById(R.id.cardDocentes);
        cardCalificaciones = findViewById(R.id.cardCalificaciones);
        btnLogout          = findViewById(R.id.btnLogout);
    }

    /**
     * Configura los listeners para manejar los clics de los botones y tarjetas.
     */
    private void setupListeners() {
        cardEstudiantes.setOnClickListener(v -> goToEstudiantes());
        cardMaterias.setOnClickListener(v -> goToMaterias());
        cardDocentes.setOnClickListener(v -> goToDocentes());
        cardCalificaciones.setOnClickListener(v -> goToCalificaciones());
        btnLogout.setOnClickListener(v -> logout());
    }

    /**
     * Navega a la pantalla de gestión de estudiantes.
     */
    private void goToEstudiantes() {
        startActivity(new Intent(this, EstudianteActivity.class));
    }

    /**
     * Navega a la pantalla de gestión de materias.
     */
    private void goToMaterias() {
        startActivity(new Intent(this, MateriaActivity.class));
    }

    /**
     * Navega a la pantalla de gestión de docentes.
     */
    private void goToDocentes() {
        startActivity(new Intent(this, DocenteActivity.class));
    }

    /**
     * Navega a la pantalla de gestión de calificaciones.
     */
    private void goToCalificaciones() {
        startActivity(new Intent(this, CalificacionActivity.class));
    }

    /**
     * Cierra la sesión del usuario actual y redirige al login.
     */
    private void logout() {
        storageHelper.cerrarSesion();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
