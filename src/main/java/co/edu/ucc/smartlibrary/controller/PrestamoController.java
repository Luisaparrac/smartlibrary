package co.edu.ucc.smartlibrary.controller;

import co.edu.ucc.smartlibrary.dto.PrestamoRequest;
import co.edu.ucc.smartlibrary.dto.PrestamoResponse;
import co.edu.ucc.smartlibrary.model.Prestamo;
import co.edu.ucc.smartlibrary.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Capa de presentación: recibe peticiones HTTP, valida el formato y delega en el servicio.
 * No contiene reglas de negocio ni accede a los repositorios.
 */
@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<PrestamoResponse> registrarPrestamo(@Valid @RequestBody PrestamoRequest request) {
        Prestamo prestamo = prestamoService.registrarPrestamo(request.estudianteId(), request.libroId());
        return ResponseEntity.status(HttpStatus.CREATED).body(PrestamoResponse.desde(prestamo));
    }

    @GetMapping
    public ResponseEntity<List<PrestamoResponse>> listarPrestamos() {
        List<PrestamoResponse> prestamos = prestamoService.listarPrestamos().stream()
                .map(PrestamoResponse::desde)
                .toList();
        return ResponseEntity.ok(prestamos);
    }
}
