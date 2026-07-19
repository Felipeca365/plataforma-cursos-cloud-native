package com.duoc.bff.model;

import java.util.List;

public class InscripcionResumen {

    private String estudiante;
    private List<Curso> cursosSeleccionados;
    private double totalPagar;

    public InscripcionResumen() {
    }

    public InscripcionResumen(String estudiante, List<Curso> cursosSeleccionados, double totalPagar) {
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

    public List<Curso> getCursosSeleccionados() {
        return cursosSeleccionados;
    }

    public void setCursosSeleccionados(List<Curso> cursosSeleccionados) {
        this.cursosSeleccionados = cursosSeleccionados;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }
}
