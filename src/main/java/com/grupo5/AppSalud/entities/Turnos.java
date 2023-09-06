/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo5.AppSalud.entities;

import com.grupo5.AppSalud.enumeraciones.DiasSemana;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Turnos {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idTurnos;
    private String horaComienzoTurno;
    private String horaFinalizacionTurno;

    @Enumerated(EnumType.STRING)
    private DiasSemana diasSemana;

    @ElementCollection
    @CollectionTable(name = "turnos_disponibles_profesional")
    private List<LocalDateTime> turnosDisponibleProfesional;

    @ElementCollection
    @CollectionTable(name = "turno_pedido")
    private List<LocalDateTime> turnoPedido;

    @OneToOne
    private Profesional profesional; //Esto sería para que el profesional pueda cargar sus turnos.

    @OneToMany
    private List<Profesional> listaProfesionales; //Esto sería para que el administrador pueda seleccionar a que Profesional cargar turno.

    @OneToMany
    private List<Usuario> usuario;

}
