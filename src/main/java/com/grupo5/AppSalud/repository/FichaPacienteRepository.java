/*
 */
package com.grupo5.AppSalud.repository;

import com.grupo5.AppSalud.entities.FichaPaciente;
import com.grupo5.AppSalud.entities.Profesional;
import com.grupo5.AppSalud.entities.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fitog
 */
//Creo repositorio con query de busqueda por paciente
@Repository
public interface FichaPacienteRepository extends JpaRepository<FichaPaciente, String>{//revisar si esta ok usuario o corresponde string
    
@Query("SELECT fp FROM FichaPaciente fp WHERE fp.id = :id")
public FichaPaciente buscarPorId (@Param("id") String id);//revisar si corresponde usuario o string

@Query("SELECT fp FROM FichaPaciente fp WHERE fp.turno = :turno")
public FichaPaciente buscarPorTurno (@Param("turno") String turno);//revisar si corresponde usuario o string

@Query("SELECT fp FROM FichaPaciente fp WHERE fp.profesional = :profesional")
public List<FichaPaciente> buscarFichasPorProfesional(@Param("profesional") String profesional);
    
    public Optional<FichaPaciente> findById(String id); 


}
