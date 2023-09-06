/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo5.AppSalud.entities;

import com.grupo5.AppSalud.enumeraciones.ObrasSociales;
import com.grupo5.AppSalud.enumeraciones.Rol;
import java.util.List;
import javax.persistence.Column;
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

/**
 *
 * @author chris
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity @Getter @Setter
public class Profesional  {
    
    @Id
    private String matricula;
    private String nombre;
    private String apellido;
    private String eMail;
    private String telefono;
    private String password;
    private String especialidad;
   // private String agenda;
    @OneToOne
    private CalificacionProfesional reputacion;
    @OneToOne
    private HistoriaClinica historiaC;
    
    @OneToMany
    private List<Turnero> turneros;
//    se declara los turnos que agrega el profesional
    
//    @OneToOne
//    private Turnos turno;// Agregado
//    

//    @OneToMany
//    private List<Usuario> usuarios;
//    
    @OneToOne
    private Imagen imagen;

    @Enumerated(EnumType.STRING)      //Agregada Etiqueta
    private Rol rol;
    @Enumerated(EnumType.STRING)     // idem
    private ObrasSociales obra;      //agregado

    private Boolean alta;
    private Double tarifa;           // Agregado
    private Boolean atencionremota;  //Agregado
    private String ubicacion;        //Agregado

       }

    
    
    
