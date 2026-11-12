package co.edu.ucc.smartlibrary.exception;

/**
 * Se lanza cuando se intenta prestar un libro que ya tiene un préstamo activo (409 Conflict).
 */
public class LibroNoDisponibleException extends RuntimeException {

    public LibroNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
