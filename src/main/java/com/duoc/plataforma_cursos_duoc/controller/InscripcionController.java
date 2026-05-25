package com.duoc.plataforma_cursos_duoc.controller;

import com.duoc.plataforma_cursos_duoc.model.Curso;
import com.duoc.plataforma_cursos_duoc.model.InscripcionRequest;
import com.duoc.plataforma_cursos_duoc.model.InscripcionResumen;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InscripcionController {

    @PostMapping("/api/inscripciones")
    public InscripcionResumen inscribirEstudiante(@RequestBody InscripcionRequest request) {

        List<Curso> cursosDisponibles = new ArrayList<>();

        cursosDisponibles.add(new Curso(1L, "Spring Boot", "Felipe Cabrera", 40, 79990));
        cursosDisponibles.add(new Curso(2L, "Docker", "Juan Perez", 20, 59990));
        cursosDisponibles.add(new Curso(3L, "AWS Cloud", "Maria Lopez", 30, 89990));
        cursosDisponibles.add(new Curso(4L, "CI/CD Pipeline", "Felipe Castro", 25, 99990));

        List<Curso> cursosSeleccionados = new ArrayList<>();
        double total = 0;

        for (Curso curso : cursosDisponibles) {
            if (request.getCursosIds().contains(curso.getId())) {
                cursosSeleccionados.add(curso);
                total += curso.getCosto();
            }
        }

        return new InscripcionResumen(
                request.getEstudiante(),
                cursosSeleccionados,
                total
        );
    }
}