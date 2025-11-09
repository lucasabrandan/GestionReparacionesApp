package com.example.gestionreparacionesapp.ui.registro;

/**
 * Clase "Wrapper" para el resultado del LiveData de Registro.
 */
public class RegistroResult {
    private final boolean isError;
    private final String errorMessage;

    private RegistroResult(boolean isError, String errorMessage) {
        this.isError = isError;
        this.errorMessage = errorMessage;
    }

    // Método de fábrica para ÉXITO
    public static RegistroResult success() {
        return new RegistroResult(false, null);
    }

    // Método de fábrica para ERROR
    public static RegistroResult error(String message) {
        return new RegistroResult(true, message);
    }

    // Getters
    public boolean isError() { return isError; }
    public String getErrorMessage() { return errorMessage; }
}