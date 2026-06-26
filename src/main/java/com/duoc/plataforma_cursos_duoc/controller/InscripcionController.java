package com.duoc.plataforma_cursos_duoc.controller;

import com.duoc.plataforma_cursos_duoc.model.Curso;
import com.duoc.plataforma_cursos_duoc.model.InscripcionRequest;
import com.duoc.plataforma_cursos_duoc.model.InscripcionResumen;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

        InscripcionResumen resumen = new InscripcionResumen(
                request.getEstudiante(),
                cursosSeleccionados,
                total
        );

        generarArchivoResumen(resumen);

        return resumen;
    }

    private void generarArchivoResumen(InscripcionResumen resumen) {
        try {
            File carpeta = new File("resumenes");

            if (!carpeta.exists()) {
                carpeta.mkdir();
            }

            String nombreArchivo = "resumen-" + System.currentTimeMillis() + ".txt";
            File archivo = new File(carpeta, nombreArchivo);

            FileWriter writer = new FileWriter(archivo);

            writer.write("RESUMEN DE INSCRIPCION\n");
            writer.write("======================\n");
            writer.write("Estudiante: " + resumen.getEstudiante() + "\n\n");
            writer.write("Cursos inscritos:\n");

            for (Curso curso : resumen.getCursosSeleccionados()) {
                writer.write("- " + curso.getNombre()
                        + " | Instructor: " + curso.getInstructor()
                        + " | Duracion: " + curso.getDuracion()
                        + " horas | Costo: $" + curso.getCosto() + "\n");
            }

            writer.write("\nTotal a pagar: $" + resumen.getTotalPagar());

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("Error al generar archivo de resumen", e);
        }
    }
}