package com.duoc.plataforma_cursos_duoc.controller;

import com.duoc.plataforma_cursos_duoc.model.Curso;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CursoController {

    private List<Curso> cursos = new ArrayList<>();

    public CursoController() {

        cursos.add(new Curso(1L, "Spring Boot", "Felipe Cabrera", 40, 79990));
        cursos.add(new Curso(2L, "Docker", "Juan Perez", 20, 59990));
        cursos.add(new Curso(3L, "AWS Cloud", "Maria Lopez", 30, 89990));
    }

    @GetMapping("/api/cursos")
    public List<Curso> listarCursos() {

        return cursos;
    }

    @PostMapping("/api/cursos")
    public Curso agregarCurso(@RequestBody Curso curso) {

        cursos.add(curso);

        return curso;
    }
}