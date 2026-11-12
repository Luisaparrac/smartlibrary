# SmartLibrary – API REST por capas

Caso de uso **«Registrar préstamo»** y consulta de préstamos (Taller práctico – Bloque 7, Diseño de Software).

- Java 21 · Maven · Spring Boot 3.5 (Web, Data JPA, Validation) · H2 en memoria
- Paquetes: `controller`, `service`, `repository`, `model`, `dto`, `exception`
- Dirección de dependencias: `Controller → Service → Repository`

## Ejecutar

```bash
mvn spring-boot:run
```

La API queda en `http://localhost:8080`. Consola H2: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:smartlibrary`, usuario `sa`, sin contraseña).

Datos iniciales (`data.sql`): estudiantes 1 y 2; libros 1 y 2 disponibles; libro 3 ya prestado (préstamo 1 activo).

## Endpoints

| Método | Ruta | Respuesta |
|---|---|---|
| POST | `/api/prestamos` con `{"estudianteId": 1, "libroId": 1}` | 201 Created · 404 si no existe el estudiante o el libro · 409 si el libro ya está prestado · 400 si faltan datos |
| GET | `/api/prestamos` | 200 OK con la lista de préstamos |

## Pruebas

- Automáticas (los 5 escenarios del taller + solicitud inválida): `mvn test`
- Manuales: importar `pruebas/SmartLibrary.postman_collection.json` en Postman o usar `pruebas/pruebas.http`.
  Ejecutar las peticiones en orden (la 2 depende de la 1).

## Diagramas

La carpeta `diagramas/` contiene los diagramas UML del diseño (Visual Paradigm).
