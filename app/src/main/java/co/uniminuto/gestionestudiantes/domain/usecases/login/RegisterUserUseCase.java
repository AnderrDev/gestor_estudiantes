package co.uniminuto.gestionestudiantes.domain.usecases.login;

import co.uniminuto.gestionestudiantes.data.models.Usuario;
import co.uniminuto.gestionestudiantes.domain.repositories.UsuarioRepository;

public class RegisterUserUseCase {
    private final UsuarioRepository repository;

    public RegisterUserUseCase(UsuarioRepository repository) {
        this.repository = repository;
    }

    public boolean execute(Usuario usuario) {
        return repository.insertarUsuario(usuario);
    }
}
