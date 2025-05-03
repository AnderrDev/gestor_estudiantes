package co.uniminuto.gestionestudiantes.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import co.uniminuto.gestionestudiantes.R;
import co.uniminuto.gestionestudiantes.core.database.DatabaseHelper;
import co.uniminuto.gestionestudiantes.core.utils.HashUtil;
import co.uniminuto.gestionestudiantes.core.utils.StorageHelper;
import co.uniminuto.gestionestudiantes.data.models.Usuario;
import co.uniminuto.gestionestudiantes.data.repositories.UsuarioRepositoryImpl;
import co.uniminuto.gestionestudiantes.domain.usecases.login.LoginUseCase;
import co.uniminuto.gestionestudiantes.domain.usecases.login.RegisterUserUseCase;
import co.uniminuto.gestionestudiantes.presentation.main.MainActivity;

/**
 * LoginActivity gestiona el flujo de inicio de sesión y registro de nuevos usuarios.
 * Implementa protección de contraseñas utilizando SHA-256 para mayor seguridad.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;

    private LoginUseCase loginUseCase;
    private RegisterUserUseCase registerUserUseCase;
    private StorageHelper storageHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        configurarInsets();
        inicializarDependencias();
        inicializarVistas();
        configurarEventos();
        verificarSesionExistente();
    }

    /**
     * Configura márgenes respetando barras del sistema (barra de estado, navegación).
     */
    private void configurarInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    /**
     * Inicializa las dependencias de base de datos, repositorio, casos de uso y almacenamiento local.
     */
    private void inicializarDependencias() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        UsuarioRepositoryImpl usuarioRepository = new UsuarioRepositoryImpl(dbHelper);
        loginUseCase = new LoginUseCase(usuarioRepository);
        registerUserUseCase = new RegisterUserUseCase(usuarioRepository);
        storageHelper = new StorageHelper(this);
    }

    /**
     * Inicializa las vistas de entrada de datos y botones.
     */
    private void inicializarVistas() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin   = findViewById(R.id.btnLogin);
        btnRegister= findViewById(R.id.btnRegister);
    }

    /**
     * Configura los eventos de los botones de login y registro.
     */
    private void configurarEventos() {
        btnLogin.setOnClickListener(v -> login());
        btnRegister.setOnClickListener(v -> registrarUsuario());
    }

    /**
     * Verifica si ya existe una sesión activa y redirige al usuario a la pantalla principal.
     */
    private void verificarSesionExistente() {
        if (storageHelper.obtenerUsuarioLogueado() != null) {
            navegarAlMain();
            finish();
        }
    }

    /**
     * Ejecuta el flujo de inicio de sesión.
     * Valida credenciales, aplica hash y compara contra base de datos.
     */
    private void login() {
        String user = etUsername.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (!validarCredenciales(user, pass)) return;

        // Aplica hash a la contraseña antes de validar
        String hashedPassword = HashUtil.sha256(pass);

        boolean success = loginUseCase.execute(user, hashedPassword);

        if (success) {
            storageHelper.guardarUsuarioLogueado(user);
            navegarAlMain();
            finish();
        } else {
            mostrarMensaje("Usuario o contraseña incorrectos");
        }
    }

    /**
     * Ejecuta el flujo de registro de un nuevo usuario.
     * Protege la contraseña aplicando hash antes de guardarla.
     */
    private void registrarUsuario() {
        String user = etUsername.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (!validarCredenciales(user, pass)) return;

        // Aplica hash a la contraseña antes de guardar
        String hashedPassword = HashUtil.sha256(pass);

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(user);
        nuevoUsuario.setPassword(hashedPassword);

        boolean success = registerUserUseCase.execute(nuevoUsuario);

        mostrarMensaje(success ?
                "Usuario registrado, ahora puede iniciar sesión" :
                "Error al registrar (¿usuario ya existe?)"
        );
    }

    /**
     * Valida que los campos de usuario y contraseña cumplan las reglas mínimas.
     *
     * @return true si los datos son válidos, false si hay errores.
     */
    private boolean validarCredenciales(@NonNull String usuario, String contraseña) {
        if (usuario.isEmpty() || contraseña.isEmpty()) {
            mostrarMensaje("Debe completar usuario y contraseña");
            return false;
        }

        if (usuario.length() < 4) {
            mostrarMensaje("El nombre de usuario debe tener al menos 4 caracteres");
            return false;
        }

        if (contraseña.length() < 6) {
            mostrarMensaje("La contraseña debe tener al menos 6 caracteres");
            return false;
        }

        return true;
    }

    /**
     * Navega a la pantalla principal de la aplicación.
     */
    private void navegarAlMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    /**
     * Muestra un mensaje de tipo Toast en pantalla.
     *
     * @param mensaje Mensaje que se desea mostrar.
     */
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
