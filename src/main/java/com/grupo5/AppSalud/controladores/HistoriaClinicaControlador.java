/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo5.AppSalud.controladores;

import com.grupo5.AppSalud.entities.Profesional;
import com.grupo5.AppSalud.entities.Usuario;
import com.grupo5.AppSalud.repository.UsuarioRepository;
import com.grupo5.AppSalud.servicios.ServicioUsuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author chris
 */
@Controller
@RequestMapping("/historiaClinica")
public class HistoriaClinicaControlador {
    @Autowired
    UsuarioRepository usuarioRepositorio;
    @Autowired
    ServicioUsuario serviUsuario;
  
    @GetMapping("/lista/{id}")
    public String historiaClinica(@PathVariable String id ,ModelMap modelo ){
        Usuario usuario = usuarioRepositorio.buscarPorDni(id);
        modelo.addAttribute("usuario", usuario);
       
        return"HistoriaClinicaLista";
    }
    
    
}
