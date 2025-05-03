package co.uniminuto.gestionestudiantes.domain.usecases.login;

import java.util.List;

import co.uniminuto.gestionestudiantes.data.models.Usuario;
import co.uniminuto.gestionestudiantes.domain.repositories.UsuarioRepository;

public class LoginUseCase {
    private final UsuarioRepository repository;

    public LoginUseCase(UsuarioRepository repository) {
        this.repository = repository;
    }

    public boolean execute(String username, String passwordHash) {
        List<Usuario> usuarios = repository.obtenerUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getPassword().equals(passwordHash)) {
                return true;
            }
        }
        return false;
    }
}
