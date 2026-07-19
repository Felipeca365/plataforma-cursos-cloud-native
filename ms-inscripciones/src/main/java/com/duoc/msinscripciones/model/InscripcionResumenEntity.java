package com.duoc.msinscripciones.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "INSCRIPCIONES_RESUMEN")
public class InscripcionResumenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estudiante;

    private double totalPagar;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    public InscripcionResumenEntity() {
    }

    public InscripcionResumenEntity(String estudiante, double totalPagar) {
        this.estudiante = estudiante;
        this.totalPagar = totalPagar;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(double totalPagar) {
        this.totalPagar = totalPagar;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
