package com.duoc.msinscripciones.service;

import com.duoc.msinscripciones.model.CursoDTO;
import com.duoc.msinscripciones.model.InscripcionResumen;
import com.duoc.msinscripciones.model.InscripcionResumenEntity;
import com.duoc.msinscripciones.repository.InscripcionResumenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionResumenRepository inscripcionResumenRepository;

    // Antes esto vivia en ConsumidorService y se disparaba con @RabbitListener.
    // Ahora el BFF es quien escucha la cola, y llama a este metodo via HTTP
    // cuando recibe el mensaje. La logica de negocio (generar archivo + guardar
    // en Oracle) no cambia en nada, solo cambio quien la invoca.
    public InscripcionResumenEntity procesarResumen(InscripcionResumen resumen) {

        generarArchivoResumen(resumen);

        InscripcionResumenEntity entity = new InscripcionResumenEntity(
                resumen.getEstudiante(),
                resumen.getTotalPagar()
        );

        inscripcionResumenRepository.save(entity);

        System.out.println("Resumen guardado en Oracle Cloud con id: " + entity.getId());

        return entity;
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

            for (CursoDTO curso : resumen.getCursosSeleccionados()) {
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
