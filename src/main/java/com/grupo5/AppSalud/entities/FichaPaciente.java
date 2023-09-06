/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo5.AppSalud.entities;

import com.grupo5.AppSalud.enumeraciones.ObrasSociales;
import java.sql.Time;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Setter    //Agrego lombok
public class FichaPaciente {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String notasDeLaVisita; //intencion de la consulta - observaciones que completa profesional publicas
    private String fecha;
    @Enumerated(EnumType.STRING)
    private ObrasSociales os;
    @OneToOne
    private Turnero turno;
    @OneToOne
    private Profesional profesional;
    @OneToOne
    private Usuario usuario;
    private String anotacionesFicha;// observaciones exclusivas del medico//rol profesional
}
