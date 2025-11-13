package com.example.gestionreparacionesapp.ui.registro;

/**
 * Clase contenedora ("wrapper") para representar el resultado del registro.
 * Puede ser éxito o error con mensaje.
 */
public class RegistroResult {

    private final boolean isError;
    private final String errorMessage;

    // Constructor privado: se usa a través de los métodos estáticos.
    private RegistroResult(boolean isError, String errorMessage) {
        this.isError = isError;
        this.errorMessage = errorMessage;
    }

    /** Crea un resultado de éxito (sin mensaje de error). */
    public static RegistroResult success() {
        return new RegistroResult(false, null);
    }

    /** Crea un resultado de error con un mensaje descriptivo. */
    public static RegistroResult error(String message) {
        return new RegistroResult(true, message);
    }

    /** True si hubo error. */
    public boolean isError() {
        return isError;
    }

    /** Devuelve el mensaje del error, si lo hubo. */
    public String getErrorMessage() {
        return errorMessage;
    }
}
