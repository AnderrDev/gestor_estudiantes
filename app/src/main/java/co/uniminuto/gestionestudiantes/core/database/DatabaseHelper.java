package co.uniminuto.gestionestudiantes.core.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import co.uniminuto.gestionestudiantes.data.models.Usuario;
import co.uniminuto.gestionestudiantes.data.models.Estudiante;
import co.uniminuto.gestionestudiantes.data.models.Docente;
import co.uniminuto.gestionestudiantes.data.models.Materia;
import co.uniminuto.gestionestudiantes.data.models.Calificacion;

/**
 * DatabaseHelper gestiona la conexión y operaciones CRUD sobre SQLite para el proyecto de gestión de estudiantes.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Nombre y versión de la base de datos
    private static final String DATABASE_NAME = "dbGestionEstudiantes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DatabaseHelper";

    // Nombres de las tablas
    private static final String TABLE_USUARIO = "Usuario";
    private static final String TABLE_ESTUDIANTE = "Estudiante";
    private static final String TABLE_DOCENTE = "Docente";
    private static final String TABLE_MATERIA = "Materia";
    private static final String TABLE_CALIFICACION = "Calificacion";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Método llamado cuando la base de datos es creada por primera vez.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createUsuarioTable(db);
        createEstudianteTable(db);
        createDocenteTable(db);
        createMateriaTable(db);
        createCalificacionTable(db);
    }

    /**
     * Método llamado cuando la base de datos necesita ser actualizada.
     */
    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALIFICACION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTUDIANTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        onCreate(db);
    }

    // ========== Métodos de creación de tablas ==========

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

    // ========== Operaciones CRUD ==========

    // ========== Usuario ==========

    /**
     * Inserta un nuevo usuario en la base de datos.
     * @param usuario objeto Usuario a insertar.
     * @return true si se insertó correctamente, false si falló.
     */
    public boolean insertarUsuario(@NonNull Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", usuario.getUsername());
        values.put("password", usuario.getPassword());

        long result = db.insert(TABLE_USUARIO, null, values);
        if (result == -1) {
            Log.e(TAG, "Error insertando Usuario: " + usuario.getUsername());
        }
        return result != -1;
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     * @return lista de usuarios.
     */
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.query(TABLE_USUARIO, null, null, null, null, null, "username ASC")) {
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

    // ========== Estudiante ==========

    /**
     * Inserta un nuevo estudiante.
     */
    public boolean insertarEstudiante(@NonNull Estudiante estudiante) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", estudiante.getNombre());
        values.put("apellido", estudiante.getApellido());
        values.put("codigo", estudiante.getCodigo());
        values.put("email", estudiante.getEmail());

        long result = db.insertWithOnConflict(TABLE_ESTUDIANTE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        if (result == -1) {
            Log.e(TAG, "Error insertando Estudiante: " + estudiante.getCodigo());
        }
        return result != -1;
    }

    /**
     * Obtiene todos los estudiantes.
     */
    public List<Estudiante> obtenerEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.query(TABLE_ESTUDIANTE, null, null, null, null, null, "nombre ASC")) {
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

    /**
     * Obtiene un estudiante por su código único.
     */
    public Estudiante obtenerEstudiantePorCodigo(String codigo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ESTUDIANTE,
                null,
                "codigo = ?",
                new String[]{codigo},
                null, null, null);

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

    // ========== Docente ==========

    /**
     * Inserta un nuevo docente.
     */
    public boolean insertarDocente(@NonNull Docente docente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("documento", docente.getDocumento());
        values.put("nombre", docente.getNombre());
        values.put("correo", docente.getCorreo());

        long result = db.insert(TABLE_DOCENTE, null, values);
        if (result == -1) {
            Log.e(TAG, "Error insertando Docente: " + docente.getDocumento());
        }
        return result != -1;
    }

    /**
     * Obtiene todos los docentes.
     */
    public List<Docente> obtenerDocentes() {
        List<Docente> docentes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.query(TABLE_DOCENTE, null, null, null, null, null, "nombre ASC")) {
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

    // ========== Materia ==========

    /**
     * Inserta una nueva materia.
     */
    public boolean insertarMateria(@NonNull Materia materia) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("codigo", materia.getCodigo());
        values.put("nombre", materia.getNombre());
        values.put("id_docente", materia.getIdDocente());

        long result = db.insert(TABLE_MATERIA, null, values);
        if (result == -1) {
            Log.e(TAG, "Error insertando Materia: " + materia.getCodigo());
        }
        return result != -1;
    }

    /**
     * Obtiene todas las materias.
     */
    public List<Materia> obtenerMaterias() {
        List<Materia> materias = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.query(TABLE_MATERIA, null, null, null, null, null, "codigo ASC")) {
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

    // ========== Calificación ==========

    /**
     * Inserta una nueva calificación.
     */
    public boolean insertarCalificacion(@NonNull Calificacion calificacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_estudiante", calificacion.getIdEstudiante());
        values.put("id_materia", calificacion.getIdMateria());
        values.put("nota", calificacion.getNota());

        long result = db.insert(TABLE_CALIFICACION, null, values);
        if (result == -1) {
            Log.e(TAG, "Error insertando Calificación");
        }
        return result != -1;
    }

    /**
     * Obtiene todas las calificaciones registradas.
     */
    public List<Calificacion> obtenerCalificaciones() {
        List<Calificacion> calificaciones = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.query(TABLE_CALIFICACION, null, null, null, null, null, "id ASC")) {
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
