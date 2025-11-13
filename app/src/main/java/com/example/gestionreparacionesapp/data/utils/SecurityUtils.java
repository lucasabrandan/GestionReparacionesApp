package com.example.gestionreparacionesapp.data.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase utilitaria para manejar el hash y la verificación de contraseñas.
 * Se usa en UserRepository antes de guardar o comparar contraseñas.
 */
public class SecurityUtils {

    /**
     * Convierte una contraseña en texto plano a un hash SHA-256.
     *
     * @param password Contraseña original en texto plano.
     * @return Hash SHA-256 en formato hexadecimal, o null si ocurre un error.
     */
    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            return null;
        }
        try {
            // 1️⃣ Crea instancia del algoritmo de hashing SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 2️⃣ Calcula el hash en bytes
            byte[] hashedBytes = digest.digest(password.getBytes());

            // 3️⃣ Convierte los bytes a string hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            // 4️⃣ Devuelve el hash final (ej: "a3f5d...c91")
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Compara una contraseña ingresada con el hash guardado en la base de datos.
     *
     * @param plainPassword   Contraseña tipeada por el usuario.
     * @param hashedPassword  Hash almacenado en la base de datos.
     * @return true si coinciden, false si no.
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }

        // Hashea la contraseña ingresada
        String hashedInput = hashPassword(plainPassword);

        // Compara hashes (forma segura)
        return hashedInput != null && hashedInput.equals(hashedPassword);
    }
}
