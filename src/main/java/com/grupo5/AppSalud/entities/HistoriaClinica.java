/*
 */
package com.grupo5.AppSalud.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author fitog
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity @Getter @Setter    //Agrego lombok
public class HistoriaClinica {

@Id
private String idhc;
//se borra profesional
//se borra la fecha
@OneToMany
private List<FichaPaciente> fichapaciente; /// agregar array de fichapaciente
@OneToOne
private Usuario usuario;
private String observaciones;
private Boolean alta;

    
}
