/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo5.AppSalud.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author chris
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Turnero {

    @Id
    @GeneratedValue(generator = "uuid") //Genero valor de forma automática.
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fecha;
    private String horario; //Elegí Strig porque es mas facil su manipulación

    @ManyToOne
    private Profesional profesional;
    //    se modifica relacion con turnero muchos turnos a un usuario
    @ManyToOne
    private Usuario usuario;

    private Boolean Reserva; //Este se usa para que 2 usuarios no puedan usar el mismo turno.

    private String notasTurnero;

}
