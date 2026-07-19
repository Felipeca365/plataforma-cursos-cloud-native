package com.duoc.msinscripciones.model;

// DTO liviano: ms-inscripciones no es dueno de los cursos (eso es de ms-cursos),
// solo necesita estos datos para generar el archivo de evidencia de la inscripcion.
public class CursoDTO {

    private Long id;
    private String nombre;
    private String instructor;
    private int duracion;
    private double costo;

    public CursoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
}
