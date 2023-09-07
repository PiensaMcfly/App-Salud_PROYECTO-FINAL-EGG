/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo5.AppSalud.controladores;

import com.grupo5.AppSalud.entities.Profesional;
import com.grupo5.AppSalud.entities.Turnero;
import com.grupo5.AppSalud.entities.Usuario;
import com.grupo5.AppSalud.exepciones.MiException;
import com.grupo5.AppSalud.servicios.ServicioTurnero;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author chris
 */
@Controller
@RequestMapping("/turnos")
public class TurneroControlador {

    @Autowired
    private ServicioTurnero serviTurnero;

    @Autowired
    private HttpSession session; // Agregar esta inyección para acceder a la sesión

    @PreAuthorize("hasAnyRole('ROLE_MEDICO')")
    @GetMapping("/profesional/registrar_turno")
    public String registrarTurnoProf() {
        return "registroTurno.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_MEDICO')")
    @PostMapping("/profesional/turno_registrado")
    public String registrarTurno(@RequestParam String fecha, @RequestParam String hora, ModelMap modelo)throws ValidationException {
        try {
            String matriculaProfesional = obtenerMatriculaProfesionalDeSesion();
            serviTurnero.registrar(hora, fecha, matriculaProfesional);

        } catch (ValidationException ex) {
           modelo.put("Error", ex.getMessage());
           return "registroTurno.html";
        }

        return "turnoRegistrado.html";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) throws MiException {
        serviTurnero.eliminarTurnero(id);
        return "usuarioDashboard.html";
    }

    private String obtenerMatriculaProfesionalDeSesion() {
        Profesional profesionalEnSesion = (Profesional) session.getAttribute("usuariosession");
        if (profesionalEnSesion != null) {
            return profesionalEnSesion.getMatricula();
        }
        // Manejar el caso en que no se encuentre la matrícula en la sesión
        return null;
    }

//    ---------------------------------usuario-------------------------- perciste ok en base, revisar validacion de turno existente y segun profesional
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
    @GetMapping("/usuario/asignar_turno")
    public String registrarTurnoUsuario(@RequestParam("id") String id, ModelMap model) {
        return "registroTurno.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
    @PostMapping("/usuario/turno_asignado")
    public String asignarTurno(@RequestParam("idTurno") String idDelTurno, @RequestParam String notasTurnero) {
        try {
            String dniUsuario = obtenerDniUsuarioEnSesion();
            serviTurnero.asignarTurno(idDelTurno, notasTurnero, dniUsuario);
        } catch (Exception e) {
            // Manejar la excepción
        }

        return "turnoRegistrado.html";
    }
    
        @PreAuthorize("hasAnyRole('ROLE_MEDICO')")
    @GetMapping("/profesional/listar_turnos")
    public String listarTurnoProfesional(ModelMap model) {
        String matricula = obtenerMatriculaProfesionalDeSesion();
        List<Turnero> listaTurnos = serviTurnero.listaTurnosPorMatricula(matricula);
        model.addAttribute("listaTurnos", listaTurnos);
        return "listaTurnos.html";
    }

    private String obtenerDniUsuarioEnSesion() {
        Usuario usuarioEnSesion = (Usuario) session.getAttribute("usuariosession");
        if (usuarioEnSesion != null) {
            return usuarioEnSesion.getDni();
        }
        return null;
    }

//    @PreAuthorize("hasAnyRole('ROLE_PACIENTE', 'MEDICO')")
//    @GetMapping("/listaTurnos")
//    private String mostrarTurnos(ModelMap modelo){
//    List<Turnero> listaTurnos = 
//    }
}
