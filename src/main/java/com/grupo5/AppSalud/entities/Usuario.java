/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo5.AppSalud.entities;

import com.grupo5.AppSalud.enumeraciones.ObrasSociales;
import com.grupo5.AppSalud.enumeraciones.Rol;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author chris
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Usuario {
    @Id
    private String dni;
    private String nombre;
    private String apellido;
    private String eMail;
    private String telefono;
    private String password;

    @OneToOne
    private Imagen imagen;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    private Boolean alta;
    private Boolean obraSocial;
    @Enumerated(EnumType.STRING)
    private ObrasSociales nombreObraSocial;

    @OneToOne(cascade = CascadeType.ALL)
    private HistoriaClinica historiaC;

}
