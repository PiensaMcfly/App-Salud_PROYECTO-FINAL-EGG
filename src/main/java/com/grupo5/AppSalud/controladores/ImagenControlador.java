/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo5.AppSalud.controladores;

import com.grupo5.AppSalud.entities.Profesional;
import com.grupo5.AppSalud.entities.Usuario;
import com.grupo5.AppSalud.servicios.ServicioProfesional;
import com.grupo5.AppSalud.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//elimino línea de importación que no estaba en uso, porque generaba error!! - gise
/**
 *
 * @author chris
 */
@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
    ServicioUsuario servicioUsuario;
    
    @Autowired 
    ServicioProfesional servicioProfesional;
    
    @GetMapping("/perfil/{dni}")
    public ResponseEntity<byte[]> imagenUsuario (@PathVariable String dni){
       Usuario usuario = servicioUsuario.getOne(dni);
       
       byte[] imagen = usuario.getImagen().getContenido();
       
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
       
        
        
       return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    @GetMapping("/perfil1/{matricula}")
    public ResponseEntity<byte[]> imagenProfesional (@PathVariable String matricula){
       Profesional profesional = servicioProfesional.getOne(matricula);
       
       byte[] imagen = profesional.getImagen().getContenido();
       
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
       
        
        
       return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    
}
