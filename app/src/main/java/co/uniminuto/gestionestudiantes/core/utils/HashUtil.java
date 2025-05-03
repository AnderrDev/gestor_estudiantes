package co.uniminuto.gestionestudiantes.core.utils;

import androidx.annotation.NonNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * HashUtil proporciona métodos estáticos para generar hashes criptográficos.
 * <p>
 * Esta clase es final y no puede ser instanciada.
 */
public final class HashUtil {

    /**
     * Constructor privado para prevenir la creación de instancias de la clase de utilidades.
     */
    private HashUtil() {
        // Previene la creación de instancias
    }

    /**
     * Genera el hash SHA-256 de un texto dado.
     *
     * @param input El texto que se desea convertir a hash.
     * @return Una cadena hexadecimal representando el hash SHA-256 del input.
     * @throws RuntimeException si el algoritmo SHA-256 no está disponible en el entorno.
     */
    @NonNull
    public static String sha256(@NonNull String input) {
        try {
            // Obtener instancia de MessageDigest para SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            // Convertir hash en array de bytes a representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Lanzar excepción de tiempo de ejecución si el algoritmo no está disponible
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
