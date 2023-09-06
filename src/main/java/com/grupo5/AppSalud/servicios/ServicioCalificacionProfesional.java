/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo5.AppSalud.servicios;

import com.grupo5.AppSalud.entities.CalificacionProfesional;
import com.grupo5.AppSalud.exepciones.MiException;
import com.grupo5.AppSalud.repository.CalificacionProfesionalRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author chris
 */
@Service
public class ServicioCalificacionProfesional {
    
    @Autowired
    private CalificacionProfesionalRepository calificacionProfesionalRepository;
    
    @Transactional
    public void registrar(String idcp, Double puntuacionDelPaciente, Double promedioDelProfesional, Double vecesVotadoProfesional) throws MiException {
        validar(idcp, puntuacionDelPaciente, promedioDelProfesional, vecesVotadoProfesional);

        CalificacionProfesional calificacionProfesional = new CalificacionProfesional();
        calificacionProfesional.setIdcp(idcp);
        calificacionProfesional.setPromedioDelProfesional(promedioDelProfesional);
        calificacionProfesional.setVecesVotadoProfesional(vecesVotadoProfesional);
        
        calificacionProfesionalRepository.save(calificacionProfesional);
    }
    
        public List<CalificacionProfesional> listaCalificProf() {

        List<CalificacionProfesional> calificionesProf = calificacionProfesionalRepository.findAll();

        return calificionesProf;
    }
    
        
    @Transactional
    public void modificarCalificacionProfesional(String idcp, Double puntuacionDelPaciente, Double promedioDelProfesional, Double vecesVotadoProfesional) throws MiException {
        validar(idcp, puntuacionDelPaciente, promedioDelProfesional, vecesVotadoProfesional);

        CalificacionProfesional calificacionProfesional = new CalificacionProfesional();
        calificacionProfesional.setPuntuacionDelPaciente(puntuacionDelPaciente);
        calificacionProfesional.setPromedioDelProfesional(promedioDelProfesional);
        calificacionProfesional.setVecesVotadoProfesional(vecesVotadoProfesional);
        
        calificacionProfesionalRepository.save(calificacionProfesional);
    }    
        
    
    @Transactional 
    public void darDeBaja(String idcp) { 
        Optional<CalificacionProfesional> respuesta = calificacionProfesionalRepository.findById(idcp); //Cambio id por Matricula
    
        if (respuesta.isPresent()) {
            CalificacionProfesional calificacionProfesional = respuesta.get();
            calificacionProfesional.setAlta(false);

            calificacionProfesionalRepository.save(calificacionProfesional);
        }
    }   
    
        
    @Transactional 
    public void darDeAlta(String idcp) { 
        Optional<CalificacionProfesional> respuesta = calificacionProfesionalRepository.findById(idcp); //Cambio id por Matricula
    
        if (respuesta.isPresent()) {
            CalificacionProfesional calificacionProfesional = respuesta.get();
            calificacionProfesional.setAlta(true);

            calificacionProfesionalRepository.save(calificacionProfesional);
        }
    }   
    
    @Transactional
    public void eliminarCalificacionProfesional(String idcp){ 

        calificacionProfesionalRepository.deleteById(idcp); 
           
    }
        
    public void validar(String idcp, Double puntuacionDelPaciente, Double promedioDelProfesional, Double vecesVotadoProfesional) throws MiException {

        if (puntuacionDelPaciente == null) {
            throw new MiException("la opción no puede ser nula");

        }
        if (promedioDelProfesional == null) {
            throw new MiException("la opción no puede ser nula");

        }
        if (vecesVotadoProfesional == null) {
            throw new MiException("la opción no puede ser nula");
        }
    }    
}
