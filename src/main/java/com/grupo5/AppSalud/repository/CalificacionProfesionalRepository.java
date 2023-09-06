/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupo5.AppSalud.repository;

import com.grupo5.AppSalud.entities.CalificacionProfesional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chris
 */
@Repository
public interface CalificacionProfesionalRepository extends JpaRepository<CalificacionProfesional, String> {
    
    @Query("SELECT cp FROM CalificacionProfesional cp WHERE cp.promedioDelProfesional = :promedioDelProfesional")
    public CalificacionProfesional buscarpromedioCalificacionProfesional(@Param("promedioDelProfesional") String promedioDelProfesional);
    
    @Query("SELECT cp FROM CalificacionProfesional cp WHERE cp.puntuacionDelPaciente = :puntuacionDelPaciente")
    public CalificacionProfesional buscarPuntuacionDelPaciente(@Param("puntuacionDelPaciente") String puntuacionDelPaciente);

    @Query("SELECT cp FROM CalificacionProfesional cp WHERE cp.vecesVotadoProfesional = :vecesVotadoProfesional")
    public CalificacionProfesional buscarVecesVotado(@Param("vecesVotadoProfesional") String vecesVotadoProfesional);
    
    
    public Optional<CalificacionProfesional> findById(String idcp);
    
    
}
