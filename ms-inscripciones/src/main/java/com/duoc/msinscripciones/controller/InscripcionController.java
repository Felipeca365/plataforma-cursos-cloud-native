package com.duoc.msinscripciones.controller;

import com.duoc.msinscripciones.model.InscripcionResumen;
import com.duoc.msinscripciones.model.InscripcionResumenEntity;
import com.duoc.msinscripciones.repository.InscripcionResumenRepository;
import com.duoc.msinscripciones.service.InscripcionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Servicio interno: no valida JWT, solo lo llama el BFF.
@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;
    private final InscripcionResumenRepository repository;

    public InscripcionController(InscripcionService inscripcionService,
                                  InscripcionResumenRepository repository) {
        this.inscripcionService = inscripcionService;
        this.repository = repository;
    }

    // Llamado por el BFF despues de consumir el mensaje de RabbitMQ.
    @PostMapping
    public InscripcionResumenEntity registrarResumen(@RequestBody InscripcionResumen resumen) {
        return inscripcionService.procesarResumen(resumen);
    }

    // Evidencia: permite ver lo que ya quedo guardado en Oracle.
    @GetMapping
    public List<InscripcionResumenEntity> listarResumenes() {
        return repository.findAll();
    }
}
