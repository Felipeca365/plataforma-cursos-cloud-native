package com.duoc.plataforma_cursos_duoc.model;

import java.util.List;

public class InscripcionRequest {

    private String estudiante;
    private List<Long> cursosIds;

    public InscripcionRequest() {
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public List<Long> getCursosIds() {
        return cursosIds;
    }

    public void setCursosIds(List<Long> cursosIds) {
        this.cursosIds = cursosIds;
    }
}