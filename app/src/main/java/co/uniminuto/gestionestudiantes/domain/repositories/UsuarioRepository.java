package co.uniminuto.gestionestudiantes.domain.repositories;

import java.util.List;
import co.uniminuto.gestionestudiantes.data.models.Usuario;

public interface UsuarioRepository {
    boolean insertarUsuario(Usuario usuario);
    List<Usuario> obtenerUsuarios();
}
