/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo5.AppSalud.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Entity @Getter @Setter    //Agrego lombok
public class CalificacionProfesional {
    
    @Id
    @GeneratedValue(generator = "uuid") //Genero valor de forma automática.
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idcp;
    private Double puntuacionDelPaciente;
    private Double promedioDelProfesional;
    private Double vecesVotadoProfesional;
    private Boolean alta; //Agrego atributo alta


    
}
