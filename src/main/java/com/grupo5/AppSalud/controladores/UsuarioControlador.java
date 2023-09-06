/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupo5.AppSalud.controladores;

import com.grupo5.AppSalud.entities.Usuario;
import com.grupo5.AppSalud.enumeraciones.ObrasSociales;
import com.grupo5.AppSalud.exepciones.MiException;
import com.grupo5.AppSalud.repository.UsuarioRepository;
import com.grupo5.AppSalud.servicios.ServicioUsuario;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Usuario
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {
    
@Autowired   
private ServicioUsuario  servicioUsuario; 
 
@PreAuthorize("hasAnyRole('ROLE_PACIENTE','ROLE_ADMIN')")  
@GetMapping("/dashboard")
public String dashboard(HttpSession session,ModelMap modelo, String dni){
     
    Usuario logueado=(Usuario) session.getAttribute("usuariosession");
       modelo.addAttribute("logueado", logueado);
       System.out.println(logueado);
     List <Usuario> listaUsuarios = servicioUsuario.listaUsuario();
     modelo.put("listaUsuarios", listaUsuarios);

      return"UsuarioDashboard.html";
 }   
 
//    @GetMapping("/editarUsuario/{dni}")
//    public String mostrarFormularioModificar(@PathVariable String dni, ModelMap model) {
//        
//        List<Usuario> listauser = servicioUsuario.listaUsuario();
//        Usuario usuario = servicioUsuario.getOne(dni); // Implementa este método en tu servicio
//        model.addAttribute("usuario", usuario);
//        model.addAttribute("listauser", listauser);
//        return "UsuarioDashboard.html";
//    }

   @GetMapping("/modificarUsuario/{dni}")
    public String mostrarFormularioModificarUsuario(@PathVariable String dni, ModelMap modelo) {
        Usuario usuario = servicioUsuario.getOne(dni);
        modelo.addAttribute("usuario", usuario);
        return "modificarPerfilUsuario.html";
    }

    @PostMapping("/modificadoUsuario/{dni}")
    public String modificarUsuario(@PathVariable("dni") String dni,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("eMail") String eMail,
            @RequestParam("telefono") String telefono,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            @RequestParam("nombreObraSocial") ObrasSociales nombreObraSocial,
            ModelMap modelo,
            @RequestParam("archivo") MultipartFile archivo
    )throws ValidationException {
        try {
            servicioUsuario.modificarUsuario(archivo, dni, nombre, apellido, eMail, telefono, password, password2, nombreObraSocial);
            
            modelo.put("exito", "Profesional modificado correctamente!");
            
        } catch (MiException e) {

            modelo.put("error", e);
            
        }
        return "redirect:../dashboard";
    }
       
    @GetMapping("/modificar/{dni}")
    public String modificar(@PathVariable String dni, ModelMap modelo) {
        modelo.put("usuario", servicioUsuario.getOne(dni));
        return "usuarioDashboard.html";
    }
//
//    @PostMapping("/modificado/{dni}")
//    public String modificado(@PathVariable MultipartFile archivo,String dni,String nombre,String apellido, String eMail, String telefono, String password, String password2,ObrasSociales nombreObraSocial, ModelMap modelo) {
//        try {
//            servicioUsuario.modificarUsuario(archivo,dni, nombre,apellido,eMail,telefono,password,password2,nombreObraSocial);
//            modelo.put("usuario", "el usuario ha sido modificado");
//            return "usuarioDashboard.html";
//        } catch (Exception ex) {
//            modelo.put("Error", ex.getMessage());
//            return"/Dashboard";
//        }
//   }
       
     @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String dni, ModelMap modelo) throws MiException{
        servicioUsuario.eliminarUsuario(dni);
        
        return "usuarioDashboard.html";
}  
}

