package co.uniminuto.gestionestudiantes.data.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.uniminuto.gestionestudiantes.core.database.DatabaseHelper;
import co.uniminuto.gestionestudiantes.data.models.Materia;
import co.uniminuto.gestionestudiantes.domain.repositories.MateriaRepository;

public class MateriaRepositoryImpl implements MateriaRepository {

    private final DatabaseHelper dbHelper;

    public MateriaRepositoryImpl(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public boolean insertarMateria(Materia materia) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("codigo", materia.getCodigo());
        values.put("nombre", materia.getNombre());
        values.put("id_docente", materia.getIdDocente()); // puede ser null si no se asigna

        long result = db.insert("Materia", null, values);
        return result != -1;
    }

    @Override
    public List<Materia> obtenerMaterias() {
        List<Materia> materias = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor cursor = db.query("Materia", null, null, null, null, null, "nombre ASC")) {
            if (cursor.moveToFirst()) {
                do {
                    Materia materia = new Materia();
                    materia.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    materia.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow("codigo")));
                    materia.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    materia.setIdDocente(cursor.getInt(cursor.getColumnIndexOrThrow("id_docente")));
                    materias.add(materia);
                } while (cursor.moveToNext());
            }
        }

        return materias;
    }
}
