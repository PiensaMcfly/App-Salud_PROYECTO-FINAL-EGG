/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo5.AppSalud.controladores;

//import com.grupo5.AppSalud.entities.Turnos;
//import com.grupo5.AppSalud.servicios.ServicioTurnos;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author mauridev
 *//*
@Controller
public class TurnosControlador {

    @Autowired
    private ServicioTurnos servicioTurnos;

    @GetMapping("/turnos")
    public String mostrarTurnos(Model model) {
        List<Turnos> turnosDisponibles = servicioTurnos.obtenerTurnosDisponibles();
        model.addAttribute("turnos", turnosDisponibles);
        return "lista_turnos"; // Nombre de la vista Thymeleaf
    }

    @PostMapping("/reservar")
    public String reservarTurno(@RequestParam String turnoId, @RequestParam String usuarioId) {
        servicioTurnos.reservarTurno(turnoId, usuarioId);
        return "redirect:/turnos";
    }
}*/