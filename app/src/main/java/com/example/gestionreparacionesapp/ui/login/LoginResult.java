package com.example.gestionreparacionesapp.ui.login;

/**
 * Clase contenedora ("wrapper") para representar el resultado de un intento de login.
 * Puede ser éxito (nombre del usuario) o error (mensaje de error).
 */
public class LoginResult {

    private final String successUserName;
    private final String errorMessage;

    // Constructor privado: solo se crean a través de los métodos estáticos de fábrica.
    private LoginResult(String successUserName, String errorMessage) {
        this.successUserName = successUserName;
        this.errorMessage = errorMessage;
    }

    /** Crea un resultado de éxito, conteniendo el nombre del usuario logueado. */
    public static LoginResult success(String userName) {
        return new LoginResult(userName, null);
    }

    /** Crea un resultado de error, con un mensaje descriptivo. */
    public static LoginResult error(String message) {
        return new LoginResult(null, message);
    }

    /** Devuelve true si el resultado representa un error. */
    public boolean isError() {
        return errorMessage != null;
    }

    /** Devuelve el nombre del usuario logueado (solo válido si no hay error). */
    public String getSuccessUserName() {
        return successUserName;
    }

    /** Devuelve el mensaje de error si ocurrió un fallo. */
    public String getErrorMessage() {
        return errorMessage;
    }
}
