package com.duoc.bff.controller;

import com.duoc.bff.model.Curso;
import com.duoc.bff.service.CursosClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursosClientService cursosClientService;

    public CursoController(CursosClientService cursosClientService) {
        this.cursosClientService = cursosClientService;
    }

    @GetMapping
    public List<Curso> listarCursos() {
        return cursosClientService.listarCursos();
    }
}
