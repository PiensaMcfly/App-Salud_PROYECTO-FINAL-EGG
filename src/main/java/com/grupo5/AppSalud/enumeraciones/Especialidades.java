/*
 */
package com.grupo5.AppSalud.enumeraciones;

/**
 *
 * @author fitog
 */
public enum Especialidades {
    PEDIATRIA("Pediatría"), CARDIOLOGIA("Cardiología"), GINECOLOGIA("Ginecología"), CLINICA_MEDICA("Clínica");

    private String nombreEspecialidad;

    Especialidades(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    @Override
    public String toString() {
        return nombreEspecialidad;
    }

}
