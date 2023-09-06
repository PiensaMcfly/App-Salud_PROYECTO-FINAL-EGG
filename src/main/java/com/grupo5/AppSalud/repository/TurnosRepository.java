/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupo5.AppSalud.repository;

import com.grupo5.AppSalud.entities.Turnos;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author chris
 */
public interface TurnosRepository extends JpaRepository<Turnos, String>{ 
        @Query("SELECT tp FROM Turnos tp WHERE tp.turnoPedido = :turnoPedido")
    public Turnos buscarTurnoPedido(@Param("turnoPedido") String turnoPedido);
    
        @Query("SELECT tdp FROM Turnos tdp WHERE tdp.turnosDisponibleProfesional = :turnosDisponibleProfesional")
    public Turnos buscarTurnoDisponibleProfesional(@Param("turnosDisponibleProfesional") String turnosDisponibleProfesional);
    
    public Optional<Turnos> findById(String idTurnos);
    
}
