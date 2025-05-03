package co.uniminuto.gestionestudiantes.data.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.uniminuto.gestionestudiantes.core.database.DatabaseHelper;
import co.uniminuto.gestionestudiantes.data.models.Docente;
import co.uniminuto.gestionestudiantes.domain.repositories.DocenteRepository;

public class DocenteRepositoryImpl implements DocenteRepository {

    private final DatabaseHelper dbHelper;

    public DocenteRepositoryImpl(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public boolean insertarDocente(Docente docente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("documento", docente.getDocumento());
        values.put("nombre", docente.getNombre());
        values.put("correo", docente.getCorreo());

        long result = db.insert("Docente", null, values);
        return result != -1;
    }

    @Override
    public List<Docente> obtenerDocentes() {
        List<Docente> docentes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor cursor = db.query("Docente", null, null, null, null, null, "nombre ASC")) {
            if (cursor.moveToFirst()) {
                do {
                    Docente docente = new Docente();
                    docente.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    docente.setDocumento(cursor.getString(cursor.getColumnIndexOrThrow("documento")));
                    docente.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    docente.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow("correo")));
                    docentes.add(docente);
                } while (cursor.moveToNext());
            }
        }

        return docentes;
    }
}
