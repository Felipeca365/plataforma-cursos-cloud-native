package com.duoc.plataforma_cursos_duoc.model;

public class Curso {

    private Long id;
    private String nombre;
    private String instructor;
    private int duracion;
    private double costo;

    public Curso() {
    }

    public Curso(Long id, String nombre, String instructor, int duracion, double costo) {
        this.id = id;
        this.nombre = nombre;
        this.instructor = instructor;
        this.duracion = duracion;
        this.costo = costo;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getInstructor() {
        return instructor;
    }

    public int getDuracion() {
        return duracion;
    }

    public double getCosto() {
        return costo;
    }
}