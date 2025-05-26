package com.ejemplo.tareas.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Representa una entidad Tarea con información básica sobre una tarea.
 */
@Entity
public class Tarea {

    /** Identificador único de la tarea. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Título descriptivo de la tarea. */
    private String titulo;

    /** Descripción detallada de la tarea. */
    private String descripcion;

    /** Nivel de prioridad: BAJA, MEDIA o ALTA. */
    private String prioridad;

    /** Fecha límite para completar la tarea. */
    private LocalDate fechaLimite;

    /** Indica si la tarea ha sido completada. */
    private boolean completada;

    /** @return el ID de la tarea. */
    public Long getId() {
        return id;
    }

    /** @param id establece el ID de la tarea. */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return el título de la tarea. */
    public String getTitulo() {
        return titulo;
    }

    /** @param titulo establece el título de la tarea. */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /** @return la descripción de la tarea. */
    public String getDescripcion() {
        return descripcion;
    }

    /** @param descripcion establece la descripción de la tarea. */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /** @return la prioridad de la tarea. */
    public String getPrioridad() {
        return prioridad;
    }

    /** @param prioridad establece la prioridad de la tarea. */
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    /** @return la fecha límite de la tarea. */
    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    /** @param fechaLimite establece la fecha límite de la tarea. */
    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    /** @return true si la tarea está completada, false en caso contrario. */
    public boolean isCompletada() {
        return completada;
    }

    /** @param completada indica si la tarea ha sido completada. */
    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
}
