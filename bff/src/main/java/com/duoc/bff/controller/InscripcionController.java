package com.duoc.bff.controller;

import com.duoc.bff.model.Curso;
import com.duoc.bff.model.InscripcionRequest;
import com.duoc.bff.model.InscripcionResumen;
import com.duoc.bff.service.CursosClientService;
import com.duoc.bff.service.InscripcionesClientService;
import com.duoc.bff.service.ProductorService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final CursosClientService cursosClientService;
    private final ProductorService productorService;
    private final InscripcionesClientService inscripcionesClientService;

    public InscripcionController(CursosClientService cursosClientService,
                                  ProductorService productorService,
                                  InscripcionesClientService inscripcionesClientService) {
        this.cursosClientService = cursosClientService;
        this.productorService = productorService;
        this.inscripcionesClientService = inscripcionesClientService;
    }

    // Endpoint "productor": arma el resumen consultando ms-cursos,
    // y publica el evento en RabbitMQ. La persistencia en Oracle
    // ocurre despues, de forma asincrona, cuando el ConsumidorService
    // reciba el mensaje.
    @PostMapping
    public InscripcionResumen inscribirEstudiante(@RequestBody InscripcionRequest request) {

        List<Curso> cursosDisponibles = cursosClientService.listarCursos();

        List<Curso> cursosSeleccionados = new ArrayList<>();
        double total = 0;

        for (Curso curso : cursosDisponibles) {
            if (request.getCursosIds().contains(curso.getId())) {
                cursosSeleccionados.add(curso);
                total += curso.getCosto();
            }
        }

        InscripcionResumen resumen = new InscripcionResumen(
                request.getEstudiante(),
                cursosSeleccionados,
                total
        );

        productorService.enviarResumen(resumen);

        return resumen;
    }

    // Evidencia: lo que ya quedo persistido (via el consumidor) en Oracle.
    @GetMapping
    public List<Map<String, Object>> listarResumenes() {
        return inscripcionesClientService.listarResumenes();
    }
}
