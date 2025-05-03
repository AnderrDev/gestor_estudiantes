package co.uniminuto.gestionestudiantes.data.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.uniminuto.gestionestudiantes.core.database.DatabaseHelper;
import co.uniminuto.gestionestudiantes.data.models.Calificacion;
import co.uniminuto.gestionestudiantes.domain.repositories.CalificacionRepository;

public class CalificacionRepositoryImpl implements CalificacionRepository {

    private final DatabaseHelper dbHelper;

    public CalificacionRepositoryImpl(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public boolean insertarCalificacion(Calificacion calificacion) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_estudiante", calificacion.getIdEstudiante());
        values.put("id_materia", calificacion.getIdMateria());
        values.put("nota", calificacion.getNota());

        long result = db.insert("Calificacion", null, values);
        return result != -1;
    }

    @Override
    public List<Calificacion> obtenerCalificaciones() {
        List<Calificacion> calificaciones = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor cursor = db.query("Calificacion", null, null, null, null, null, "id ASC")) {
            if (cursor.moveToFirst()) {
                do {
                    Calificacion calificacion = new Calificacion();
                    calificacion.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    calificacion.setIdEstudiante(cursor.getInt(cursor.getColumnIndexOrThrow("id_estudiante")));
                    calificacion.setIdMateria(cursor.getInt(cursor.getColumnIndexOrThrow("id_materia")));
                    calificacion.setNota(cursor.getDouble(cursor.getColumnIndexOrThrow("nota")));
                    calificaciones.add(calificacion);
                } while (cursor.moveToNext());
            }
        }

        return calificaciones;
    }
}
