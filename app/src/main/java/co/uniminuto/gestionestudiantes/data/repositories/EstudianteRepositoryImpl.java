package co.uniminuto.gestionestudiantes.data.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.uniminuto.gestionestudiantes.core.database.DatabaseHelper;
import co.uniminuto.gestionestudiantes.data.models.Estudiante;
import co.uniminuto.gestionestudiantes.domain.repositories.EstudianteRepository;

public class EstudianteRepositoryImpl implements EstudianteRepository {

    private final DatabaseHelper dbHelper;

    public EstudianteRepositoryImpl(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public boolean insertarEstudiante(Estudiante estudiante) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", estudiante.getNombre());
        values.put("apellido", estudiante.getApellido());
        values.put("codigo", estudiante.getCodigo());
        values.put("email", estudiante.getEmail());

        long result = db.insertWithOnConflict("Estudiante", null, values, SQLiteDatabase.CONFLICT_IGNORE);
        return result != -1;
    }

    @Override
    public List<Estudiante> obtenerEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor cursor = db.query("Estudiante", null, null, null, null, null, "nombre ASC")) {
            if (cursor.moveToFirst()) {
                do {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    estudiante.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    estudiante.setApellido(cursor.getString(cursor.getColumnIndexOrThrow("apellido")));
                    estudiante.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow("codigo")));
                    estudiante.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                    estudiantes.add(estudiante);
                } while (cursor.moveToNext());
            }
        }
        return estudiantes;
    }

    @Override
    public Estudiante getEstudianteByCodigo(String codigo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "Estudiante",
                null,
                "codigo = ?",
                new String[]{codigo},
                null, null, null
        );

        Estudiante estudiante = null;
        if (cursor.moveToFirst()) {
            estudiante = new Estudiante();
            estudiante.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            estudiante.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            estudiante.setApellido(cursor.getString(cursor.getColumnIndexOrThrow("apellido")));
            estudiante.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow("codigo")));
            estudiante.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
        }

        cursor.close();
        return estudiante;
    }

    @Override
    public boolean deleteEstudianteByCodigo(String codigo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = db.delete("Estudiante", "codigo = ?", new String[]{codigo});
        return deletedRows > 0;
    }
}
