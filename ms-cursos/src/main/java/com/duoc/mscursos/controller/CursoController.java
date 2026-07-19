package com.duoc.mscursos.controller;

import com.duoc.mscursos.model.Curso;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Este servicio NO valida JWT: solo el BFF es publico y es el unico
// que puede llamar a este endpoint (en Docker, este puerto no se
// publica hacia afuera, solo es visible dentro de la red interna).
@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final List<Curso> cursos = new ArrayList<>();

    public CursoController() {
        cursos.add(new Curso(1L, "Spring Boot", "Felipe Cabrera", 40, 79990));
        cursos.add(new Curso(2L, "Docker", "Juan Perez", 20, 59990));
        cursos.add(new Curso(3L, "AWS Cloud", "Maria Lopez", 30, 89990));
        cursos.add(new Curso(4L, "CI/CD Pipeline", "Felipe Castro", 25, 99990));
    }

    @GetMapping
    public List<Curso> listarCursos() {
        return cursos;
    }

    @GetMapping("/{id}")
    public Curso obtenerCurso(@PathVariable Long id) {
        Optional<Curso> curso = cursos.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return curso.orElse(null);
    }

    @PostMapping
    public Curso agregarCurso(@RequestBody Curso curso) {
        cursos.add(curso);
        return curso;
    }
}
