package com.example.gestionreparacionesapp.ui.login;

/**
 * Clase "Wrapper" para el resultado del LiveData de Login.
 * Es un "sobre" que nos dice si el login fue exitoso (con el nombre)
 * o si fue un error (con un mensaje).
 */
public class LoginResult {
    private final String successUserName;
    private final String errorMessage;

    // Hacemos el constructor privado para controlar cómo se crean los objetos.
    private LoginResult(String successUserName, String errorMessage) {
        this.successUserName = successUserName;
        this.errorMessage = errorMessage;
    }

    /**
     * Método de fábrica para crear un resultado de ÉXITO.
     */
    public static LoginResult success(String userName) {
        return new LoginResult(userName, null);
    }

    /**
     * Método de fábrica para crear un resultado de ERROR.
     */
    public static LoginResult error(String message) {
        return new LoginResult(null, message);
    }

    // Getters
    public boolean isError() { return errorMessage != null; }
    public String getSuccessUserName() { return successUserName; }
    public String getErrorMessage() { return errorMessage; }
}