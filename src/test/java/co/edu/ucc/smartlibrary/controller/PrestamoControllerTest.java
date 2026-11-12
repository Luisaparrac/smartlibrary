package co.edu.ucc.smartlibrary.controller;

import co.edu.ucc.smartlibrary.repository.LibroRepository;
import co.edu.ucc.smartlibrary.repository.PrestamoRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Escenarios de la Actividad 6. Se ejecutan en orden porque comparten la misma base H2:
 * datos iniciales -> estudiantes 1 y 2; libros 1 y 2 disponibles; libro 3 prestado (préstamo 1).
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroRepository libroRepository;

    private void registrar(String json, int estadoEsperado) throws Exception {
        mockMvc.perform(post("/api/prestamos").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().is(estadoEsperado));
    }

    @Test
    @Order(1)
    void prestamoDeLibroDisponibleDevuelve201() throws Exception {
        long antes = prestamoRepository.count();

        mockMvc.perform(post("/api/prestamos").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"estudianteId\": 1, \"libroId\": 1}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estudianteId").value(1))
                .andExpect(jsonPath("$.libroId").value(1))
                .andExpect(jsonPath("$.activo").value(true));

        assertThat(prestamoRepository.count()).isEqualTo(antes + 1);
        assertThat(libroRepository.findById(1L).orElseThrow().isDisponible()).isFalse();   // R3
    }

    @Test
    @Order(2)
    void libroYaPrestadoDevuelve409SinCrearPrestamo() throws Exception {
        long antes = prestamoRepository.count();

        registrar("{\"estudianteId\": 2, \"libroId\": 1}", 409);

        assertThat(prestamoRepository.count()).isEqualTo(antes);                            // R4
    }

    @Test
    @Order(3)
    void estudianteInexistenteDevuelve404() throws Exception {
        long antes = prestamoRepository.count();

        registrar("{\"estudianteId\": 99, \"libroId\": 2}", 404);

        assertThat(prestamoRepository.count()).isEqualTo(antes);
        assertThat(libroRepository.findById(2L).orElseThrow().isDisponible()).isTrue();
    }

    @Test
    @Order(4)
    void libroInexistenteDevuelve404() throws Exception {
        long antes = prestamoRepository.count();

        registrar("{\"estudianteId\": 1, \"libroId\": 99}", 404);

        assertThat(prestamoRepository.count()).isEqualTo(antes);
    }

    @Test
    @Order(5)
    void consultaDePrestamosDevuelve200ConRegistros() throws Exception {
        mockMvc.perform(get("/api/prestamos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @Order(6)
    void solicitudSinDatosDevuelve400() throws Exception {
        long antes = prestamoRepository.count();

        registrar("{}", 400);

        assertThat(prestamoRepository.count()).isEqualTo(antes);                            // R4
    }
}
