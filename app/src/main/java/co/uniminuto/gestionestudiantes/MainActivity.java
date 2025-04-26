package co.uniminuto.gestionestudiantes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnEstudiantes, btnMaterias, btnDocentes, btnCalificaciones, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        btnEstudiantes   = findViewById(R.id.btnEstudiantes);
        btnMaterias      = findViewById(R.id.btnMaterias);
        btnDocentes      = findViewById(R.id.btnDocentes);
        btnCalificaciones= findViewById(R.id.btnCalificaciones);
        btnLogout        = findViewById(R.id.btnLogout);

        btnEstudiantes.setOnClickListener(v ->
                startActivity(new Intent(this, EstudianteActivity.class))
        );
        btnMaterias.setOnClickListener(v ->
                startActivity(new Intent(this, MateriaActivity.class))
        );
        btnDocentes.setOnClickListener(v ->
                startActivity(new Intent(this, DocenteActivity.class))
        );
        btnCalificaciones.setOnClickListener(v ->
                startActivity(new Intent(this, CalificacionActivity.class))
        );
        btnLogout.setOnClickListener(v -> {
            //  volver al login
            startActivity(new Intent(this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        });
    }
}