-- Datos iniciales de prueba (los ids se generan en orden: 1, 2, 3...)
INSERT INTO estudiante (nombre, correo) VALUES ('Ana Torres', 'ana.torres@campusucc.edu.co');
INSERT INTO estudiante (nombre, correo) VALUES ('Carlos Rojas', 'carlos.rojas@campusucc.edu.co');

INSERT INTO libro (titulo, disponible) VALUES ('Clean Code', TRUE);
INSERT INTO libro (titulo, disponible) VALUES ('Patrones de diseño', TRUE);
INSERT INTO libro (titulo, disponible) VALUES ('Refactoring', FALSE);

-- El libro 3 ya está prestado: tiene un préstamo activo (fecha_devolucion vacía), por eso disponible = FALSE
INSERT INTO prestamo (fecha_prestamo, fecha_devolucion, estudiante_id, libro_id) VALUES ('2026-11-03', NULL, 2, 3);
