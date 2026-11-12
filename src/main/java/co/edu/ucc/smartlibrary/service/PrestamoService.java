package co.edu.ucc.smartlibrary.service;

import co.edu.ucc.smartlibrary.exception.LibroNoDisponibleException;
import co.edu.ucc.smartlibrary.exception.RecursoNoEncontradoException;
import co.edu.ucc.smartlibrary.model.Estudiante;
import co.edu.ucc.smartlibrary.model.Libro;
import co.edu.ucc.smartlibrary.model.Prestamo;
import co.edu.ucc.smartlibrary.repository.EstudianteRepository;
import co.edu.ucc.smartlibrary.repository.LibroRepository;
import co.edu.ucc.smartlibrary.repository.PrestamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Coordina el caso de uso «Registrar préstamo» (GRASP Controlador de caso de uso)
 * y aplica las reglas de negocio R1 a R5.
 */
@Service
public class PrestamoService {

    private final EstudianteRepository estudianteRepository;
    private final LibroRepository libroRepository;
    private final PrestamoRepository prestamoRepository;

    public PrestamoService(EstudianteRepository estudianteRepository,
                           LibroRepository libroRepository,
                           PrestamoRepository prestamoRepository) {
        this.estudianteRepository = estudianteRepository;
        this.libroRepository = libroRepository;
        this.prestamoRepository = prestamoRepository;
    }

    /**
     * Cambiar la disponibilidad y crear el préstamo ocurren en una sola transacción:
     * si algo falla, no queda ningún cambio a medias (R3, R4).
     */
    @Transactional
    public Prestamo registrarPrestamo(Long estudianteId, Long libroId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un estudiante con id " + estudianteId));

        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un libro con id " + libroId));

        // R1 y R5: solo un libro disponible (sin préstamo activo) puede prestarse
        if (!libro.isDisponible()) {
            throw new LibroNoDisponibleException(
                    "El libro '" + libro.getTitulo() + "' no está disponible: ya tiene un préstamo activo");
        }

        libro.marcarPrestado();                 // R3
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo(estudiante, libro, LocalDate.now());   // R2 (GRASP Creador)
        return prestamoRepository.save(prestamo);
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }
}
