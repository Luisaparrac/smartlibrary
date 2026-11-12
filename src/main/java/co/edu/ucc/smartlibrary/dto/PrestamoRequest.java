package co.edu.ucc.smartlibrary.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Datos de entrada de POST /api/prestamos.
 */
public record PrestamoRequest(
        @NotNull(message = "El id del estudiante es obligatorio") Long estudianteId,
        @NotNull(message = "El id del libro es obligatorio") Long libroId) {
}
