package co.uniminuto.gestionestudiantes.data.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
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
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", usuario.getUsername());
        values.put("password", usuario.getPassword());

        long result = db.insert("Usuario", null, values);
        return result != -1;
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor cursor = db.query("Usuario", null, null, null, null, null, "username ASC")) {
            if (cursor.moveToFirst()) {
                do {
                    Usuario usuario = new Usuario();
                    usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    usuario.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
                    usuario.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
                    usuarios.add(usuario);
                } while (cursor.moveToNext());
            }
        }

        return usuarios;
    }
}
