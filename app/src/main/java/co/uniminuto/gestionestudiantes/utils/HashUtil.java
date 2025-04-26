package co.uniminuto.gestionestudiantes.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class HashUtil {

    // Metodo estático que recibe un texto plano (como una contraseña)
    // y devuelve su versión en hash SHA-256 como String hexadecimal.
    public static String sha256(String input) {
        try {
            // Obtener una instancia de MessageDigest con el algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Aplicar el hash al input y obtener el array de bytes resultante
            byte[] hash = digest.digest(input.getBytes());

            // Convertir los bytes a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                // Cada byte se convierte a 2 caracteres hexadecimales
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0'); // Padding
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // Este error se lanza si el algoritmo no existe
            e.printStackTrace();
            return null;
        }
    }
}
