package co.uniminuto.gestionestudiantes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import co.uniminuto.gestionestudiantes.models.Docente;
import co.uniminuto.gestionestudiantes.models.Estudiante;
import co.uniminuto.gestionestudiantes.models.Materia;
import co.uniminuto.gestionestudiantes.models.Calificacion;
import co.uniminuto.gestionestudiantes.models.Usuario;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbUsers.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE Usuario (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "username TEXT NOT NULL UNIQUE, " +
                        "password TEXT NOT NULL" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE Estudiante (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT, " +
                        "apellido TEXT, " +
                        "codigo TEXT UNIQUE" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE Docente (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "documento TEXT, " +
                        "nombre TEXT, " +
                        "correo TEXT" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE Materia (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "codigo TEXT UNIQUE, " +
                        "nombre TEXT, " +
                        "id_docente INTEGER, " +
                        "FOREIGN KEY(id_docente) REFERENCES Docente(id) ON DELETE SET NULL" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE Calificacion (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "id_estudiante INTEGER, " +
                        "id_materia INTEGER, " +
                        "nota REAL, " +
                        "FOREIGN KEY(id_estudiante) REFERENCES Estudiante(id) ON DELETE CASCADE, " +
                        "FOREIGN KEY(id_materia) REFERENCES Materia(id) ON DELETE CASCADE" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Calificacion");
        db.execSQL("DROP TABLE IF EXISTS Materia");
        db.execSQL("DROP TABLE IF EXISTS Docente");
        db.execSQL("DROP TABLE IF EXISTS Estudiante");
        db.execSQL("DROP TABLE IF EXISTS Usuario");
        onCreate(db);
    }
//no sobre cargar el heklper crud en otra clase. reitar
    // Usuario CRUD
    public boolean insertarUsuario(Usuario u) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", u.getUsername());
        cv.put("password", u.getPassword());
        long id = db.insert("Usuario", null, cv);
        return id != -1;
    }

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("Usuario", new String[]{"id","username","password"}, null, null, null, null, "username ASC");
        if (c.moveToFirst()) {
            do {
                Usuario u = new Usuario();
                u.setId(c.getInt(0));
                u.setUsername(c.getString(1));
                u.setPassword(c.getString(2));
                lista.add(u);
            } while (c.moveToNext());
        }
        c.close();
        return lista;
    }

    // Estudiante CRUD
    public boolean insertarEstudiante(Estudiante e) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nombre", e.getNombre());
        cv.put("apellido", e.getApellido());
        cv.put("codigo", e.getCodigo());
        long id = db.insert("Estudiante", null, cv);
        return id != -1;
    }

    public List<Estudiante> obtenerEstudiantes() {
        List<Estudiante> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("Estudiante", new String[]{"id","nombre","apellido","codigo"}, null, null, null, null, "nombre ASC");
        if (c.moveToFirst()) {
            do {
                Estudiante e = new Estudiante();
                e.setId(c.getInt(0));
                e.setNombre(c.getString(1));
                e.setApellido(c.getString(2));
                e.setCodigo(c.getString(3));
                lista.add(e);
            } while (c.moveToNext());
        }
        c.close();
        return lista;
    }

    // Docente CRUD
    public boolean insertarDocente(Docente d) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("documento", d.getDocumento());
        cv.put("nombre",     d.getNombre());
        cv.put("correo",     d.getCorreo());
        long id = db.insert("Docente", null, cv);
        return id != -1;
    }

    public List<Docente> obtenerDocentes() {
        List<Docente> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("Docente", new String[]{"id","documento","nombre","correo"}, null, null, null, null, "nombre ASC");
        if (c.moveToFirst()) {
            do {
                Docente d = new Docente();
                d.setId(c.getInt(0));
                d.setDocumento(c.getString(1));
                d.setNombre(c.getString(2));
                d.setCorreo(c.getString(3));
                lista.add(d);
            } while (c.moveToNext());
        }
        c.close();
        return lista;
    }

    // Materia CRUD
    public boolean insertarMateria(Materia m) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("codigo", m.getCodigo());
        cv.put("nombre", m.getNombre());
        cv.put("id_docente", m.getIdDocente());
        long id = db.insert("Materia", null, cv);
        return id != -1;
    }

    public List<Materia> obtenerMaterias() {
        List<Materia> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("Materia", new String[]{"id","codigo","nombre","id_docente"}, null, null, null, null, "codigo ASC");
        if (c.moveToFirst()) {
            do {
                Materia m = new Materia();
                m.setId(c.getInt(0));
                m.setCodigo(c.getString(1));
                m.setNombre(c.getString(2));
                m.setIdDocente(c.getInt(3));
                lista.add(m);
            } while (c.moveToNext());
        }
        c.close();
        return lista;
    }

    // Calificacion CRUD
    public boolean insertarCalificacion(Calificacion cal) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_estudiante", cal.getIdEstudiante());
        cv.put("id_materia",    cal.getIdMateria());
        cv.put("nota",         cal.getNota());
        long id = db.insert("Calificacion", null, cv);
        return id != -1;
    }

    public List<Calificacion> obtenerCalificaciones() {
        List<Calificacion> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("Calificacion", new String[]{"id","id_estudiante","id_materia","nota"}, null, null, null, null, "id ASC");
        if (c.moveToFirst()) {
            do {
                Calificacion cal = new Calificacion();
                cal.setId(c.getInt(0));
                cal.setIdEstudiante(c.getInt(1));
                cal.setIdMateria(c.getInt(2));
                cal.setNota(c.getDouble(3));
                lista.add(cal);
            } while (c.moveToNext());
        }
        c.close();
        return lista;
    }
}