package co.uniminuto.gestionestudiantes.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

/**
 * StorageHelper facilita el acceso y la manipulación de datos persistentes simples,
 * como el estado de sincronización y la sesión de usuario, usando SharedPreferences.
 */
public class StorageHelper {

    private static final String PREF_NAME = "gestion_estudiantes_prefs";
    private static final String KEY_SINCRONIZADO = "sincronizado";
    private static final String KEY_USUARIO_LOGUEADO = "usuario_logueado";

    private final SharedPreferences sharedPreferences;

    /**
     * Constructor que inicializa el StorageHelper con el contexto proporcionado.
     *
     * @param context Contexto de la aplicación.
     */
    public StorageHelper(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Guarda el estado de sincronización en las preferencias.
     *
     * @param sincronizado true si los datos están sincronizados; false en caso contrario.
     */
    public void guardarSincronizado(boolean sincronizado) {
        sharedPreferences.edit().putBoolean(KEY_SINCRONIZADO, sincronizado).apply();
    }

    /**
     * Recupera el estado de sincronización guardado.
     *
     * @return true si los datos estaban sincronizados; false en caso contrario.
     */
    public boolean estaSincronizado() {
        return sharedPreferences.getBoolean(KEY_SINCRONIZADO, false);
    }

    /**
     * Guarda el nombre del usuario logueado en las preferencias.
     *
     * @param usuario Nombre de usuario.
     */
    public void guardarUsuarioLogueado(String usuario) {
        sharedPreferences.edit().putString(KEY_USUARIO_LOGUEADO, usuario).apply();
    }

    /**
     * Obtiene el nombre del usuario que ha iniciado sesión.
     *
     * @return Nombre del usuario logueado, o null si no hay usuario guardado.
     */
    public String obtenerUsuarioLogueado() {
        return sharedPreferences.getString(KEY_USUARIO_LOGUEADO, null);
    }

    /**
     * Cierra la sesión del usuario eliminando su información de las preferencias.
     */
    public void cerrarSesion() {
        sharedPreferences.edit().remove(KEY_USUARIO_LOGUEADO).apply();
    }
}
