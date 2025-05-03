package co.uniminuto.gestionestudiantes.data.repositories;

import java.util.List;

import co.uniminuto.gestionestudiantes.core.database.DatabaseHelper;
import co.uniminuto.gestionestudiantes.data.models.Usuario;
import co.uniminuto.gestionestudiantes.domain.repositories.UsuarioRepository;

public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final DatabaseHelper dbHelper;

    public UsuarioRepositoryImpl(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public boolean insertarUsuario(Usuario usuario) {
        return dbHelper.insertarUsuario(usuario);
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return dbHelper.obtenerUsuarios();
    }
}
