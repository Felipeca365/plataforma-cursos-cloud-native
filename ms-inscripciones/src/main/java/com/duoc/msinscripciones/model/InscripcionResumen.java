package com.duoc.msinscripciones.model;

import java.util.List;

// Esto es lo que el BFF envia luego de: 1) recibir la inscripcion del frontend,
// 2) consultar los cursos en ms-cursos, 3) publicar el evento en RabbitMQ,
// 4) consumirlo, y 5) reenviarlo aca para persistir.
public class InscripcionResumen {

    private String estudiante;
    private List<CursoDTO> cursosSeleccionados;
    private double totalPagar;

    public InscripcionResumen() {
    }

    public InscripcionResumen(String estudiante, List<CursoDTO> cursosSeleccionados, double totalPagar) {
        this.estudiante = estudiante;
        this.cursosSeleccionados = cursosSeleccionados;
        this.totalPagar = totalPagar;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public List<CursoDTO> getCursosSeleccionados() {
        return cursosSeleccionados;
    }

    public void setCursosSeleccionados(List<CursoDTO> cursosSeleccionados) {
        this.cursosSeleccionados = cursosSeleccionados;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }
}
