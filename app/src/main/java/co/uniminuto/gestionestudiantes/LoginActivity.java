package co.uniminuto.gestionestudiantes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import co.uniminuto.gestionestudiantes.database.DBHelper;
import co.uniminuto.gestionestudiantes.models.Usuario;
import co.uniminuto.gestionestudiantes.utils.HashUtil;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin    = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        dbHelper    = new DBHelper(this);

        btnLogin.setOnClickListener(v -> {
            String user = etUsername.getText().toString();
            String pass = etPassword.getText().toString();
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Debe completar usuario y contraseña", Toast.LENGTH_SHORT).show();
                return;
            }
            String hash = HashUtil.sha256(pass);
            List<Usuario> lista = dbHelper.obtenerUsuarios();
            for (Usuario u : lista) {
                if (u.getUsername().equals(user) && u.getPassword().equals(hash)) {
                    // Login correcto
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    return;
                }
            }
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        });

        btnRegister.setOnClickListener(v -> {
            String user = etUsername.getText().toString();
            String pass = etPassword.getText().toString();
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Debe completar usuario y contraseña", Toast.LENGTH_SHORT).show();
                return;
            }
            String hash = HashUtil.sha256(pass);
            Usuario nuevo = new Usuario();
            nuevo.setUsername(user);
            nuevo.setPassword(hash);
            if (dbHelper.insertarUsuario(nuevo)) {
                Toast.makeText(this, "Usuario registrado, ahora inicie sesión", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al registrar (usuario existe?)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}