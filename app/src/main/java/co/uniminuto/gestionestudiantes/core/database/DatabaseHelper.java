package co.uniminuto.gestionestudiantes.core.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

/**
 * DatabaseHelper gestiona la creación y actualización de las tablas en SQLite.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbGestionEstudiantes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USUARIO = "Usuario";
    private static final String TABLE_ESTUDIANTE = "Estudiante";
    private static final String TABLE_DOCENTE = "Docente";
    private static final String TABLE_MATERIA = "Materia";
    private static final String TABLE_CALIFICACION = "Calificacion";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUsuarioTable(db);
        createEstudianteTable(db);
        createDocenteTable(db);
        createMateriaTable(db);
        createCalificacionTable(db);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALIFICACION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTUDIANTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        onCreate(db);
    }

    private void createUsuarioTable(@NonNull SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_USUARIO + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "username TEXT NOT NULL UNIQUE, " +
                        "password TEXT NOT NULL)"
        );
    }

    private void createEstudianteTable(@NonNull SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_ESTUDIANTE + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT, " +
                        "apellido TEXT, " +
                        "codigo TEXT UNIQUE, " +
                        "email TEXT)"
        );
    }

    private void createDocenteTable(@NonNull SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_DOCENTE + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "documento TEXT, " +
                        "nombre TEXT, " +
                        "correo TEXT)"
        );
    }

    private void createMateriaTable(@NonNull SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_MATERIA + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "codigo TEXT UNIQUE, " +
                        "nombre TEXT, " +
                        "id_docente INTEGER, " +
                        "FOREIGN KEY(id_docente) REFERENCES " + TABLE_DOCENTE + "(id) ON DELETE SET NULL)"
        );
    }

    private void createCalificacionTable(@NonNull SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_CALIFICACION + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "id_estudiante INTEGER, " +
                        "id_materia INTEGER, " +
                        "nota REAL, " +
                        "FOREIGN KEY(id_estudiante) REFERENCES " + TABLE_ESTUDIANTE + "(id) ON DELETE CASCADE, " +
                        "FOREIGN KEY(id_materia) REFERENCES " + TABLE_MATERIA + "(id) ON DELETE CASCADE)"
        );
    }
}
