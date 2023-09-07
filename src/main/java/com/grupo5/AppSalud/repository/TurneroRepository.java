/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupo5.AppSalud.repository;

import com.grupo5.AppSalud.entities.Turnero;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author chris
 */
public interface TurneroRepository extends JpaRepository<Turnero, String> {

    @Query("SELECT t FROM Turnero t WHERE t.profesional = :profesional")
    public Turnero buscarPorProfesional(@Param("profesional") String matricula);

    //Verificar Query Boolean.
    @Query("SELECT t FROM Turnero t WHERE t.Reserva = true")
    public Turnero verificarCondicionTurno(@Param("true") Boolean reserva);

    @Query("SELECT t FROM Turnero t WHERE t.id = :id")
    public Turnero buscarPorId(@Param("id") String id);

    @Query("SELECT t FROM Turnero t WHERE t.id = :id")
    public Turnero buscarTurnoEspecifico(@Param("id") String id);

//    @Query("SELECT DISTINCT t.fecha FROM Turnero t WHERE t.Reserva  = '1' AND t.profesional = 'matricula'") 
//    public Turnero fechasDisponiblesProf(@Param("matricula") String matricula);
    @Query("SELECT t.fecha, t.horario, t.id FROM Turnero t WHERE t.Reserva = '1' AND t.profesional.matricula = :matricula ORDER BY t.fecha ASC")
    List<String> fechasDisponiblesProf(@Param("matricula") String matricula);

    @Query("SELECT t.horario FROM Turnero t WHERE t.Reserva = '1' AND t.profesional.matricula = :matricula AND t.fecha = :fecha")
    List<String> horariosDispo(@Param("matricula") String matricula, @Param("fecha") String fecha);

    @Query("SELECT t.id, t.fecha, t.horario FROM Turnero t WHERE t.Reserva = '1' AND t.profesional.matricula = :matricula ORDER BY t.fecha ASC")
    List<Object[]> fechasHorariosConId(@Param("matricula") String matricula);
    
    @Query("SELECT t FROM Turnero t WHERE t.fecha = :fecha AND t.horario = :horario AND t.profesional.matricula = :matricula")
    public Turnero validarTurnoExiste(@Param("matricula") String matricula,@Param("fecha") String fecha,@Param("horario") String horario);

    // @Query("SELECT t.fecha, t.horario, t.usuario.nombre, t.usuario.dni FROM Turnero t WHERE t.reserva = false AND t.profesional.matricula = :matricula")
    //List<String> listaTurnosAsignadosProf(@Param("matricula") String matricula); //Query para creación de Ficha Paciente
}

//    @Override
//    public Optional<Turnero> findById(String idTurnos);

