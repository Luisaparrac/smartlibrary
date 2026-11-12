package co.edu.ucc.smartlibrary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad del dominio. Es experta en su propio estado de disponibilidad (GRASP Experto).
 */
@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private boolean disponible;

    /** Constructor requerido por JPA. */
    protected Libro() {
    }

    public Libro(String titulo) {
        this.titulo = titulo;
        this.disponible = true;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    /** R3: al prestarse, el libro deja de estar disponible. */
    public void marcarPrestado() {
        this.disponible = false;
    }
}
