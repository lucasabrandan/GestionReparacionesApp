package com.example.gestionreparacionesapp.data.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase ayudante para manejar la seguridad de contraseñas.
 */
public class SecurityUtils {

    /**
     * Convierte una contraseña en texto plano a un hash SHA-256.
     * @param password Contraseña en texto plano.
     * @return Hash SHA-256 como un string hexadecimal.
     */
    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            return null;
        }
        try {
            // Obtiene una instancia del algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Calcula el hash
            byte[] hashedBytes = digest.digest(password.getBytes());
            // Convierte el array de bytes a un string hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); // Manejo de error en caso de que SHA-256 no exista
            return null;
        }
    }

    /**
     * Compara una contraseña en texto plano con un hash guardado.
     * @param plainPassword Contraseña tipeada por el usuario.
     * @param hashedPassword Hash guardado en la base de datos.
     * @return true si coinciden, false si no.
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        // Hashea la contraseña tipeada y la compara con el hash guardado
        String hashedInput = hashPassword(plainPassword);
        return hashedInput != null && hashedInput.equals(hashedPassword);
    }
}