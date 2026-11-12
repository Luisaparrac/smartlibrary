package co.edu.ucc.smartlibrary.repository;

import co.edu.ucc.smartlibrary.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Acceso a datos de Prestamo. Spring Data genera la implementación en tiempo de ejecución.
 */
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
}
