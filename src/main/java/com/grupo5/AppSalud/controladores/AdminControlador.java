/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo5.AppSalud.controladores;

import com.grupo5.AppSalud.entities.Profesional;
import com.grupo5.AppSalud.entities.Usuario;
import com.grupo5.AppSalud.enumeraciones.ObrasSociales;
import com.grupo5.AppSalud.enumeraciones.Rol;
import com.grupo5.AppSalud.exepciones.MiException;
import com.grupo5.AppSalud.repository.ProfesionalRepository;
import com.grupo5.AppSalud.repository.UsuarioRepository;
import com.grupo5.AppSalud.servicios.ServicioProfesional;
import com.grupo5.AppSalud.servicios.ServicioUsuario;
import java.util.List;
import java.util.Optional;
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
 * @author tincho
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    ServicioUsuario serviUsuario;
    @Autowired
    ServicioProfesional serviProfesional;
    @Autowired
    UsuarioRepository repoUsuario;
    @Autowired
    ProfesionalRepository repoProfesional;
    @Autowired
    HomeControlador homeControlador;

    //  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo) {

        List<Usuario> listaUsuario = serviUsuario.listaUsuario();
        List<Profesional> listaProfesional = serviProfesional.listaProfesionales();
        modelo.addAttribute("listaUsuario", listaUsuario);
        modelo.addAttribute("listaProfesional", listaProfesional);


        return "AdmDashboard.html";

    }
    
    
    @GetMapping("/listaUsuarios")
    public String mostrarUsuarios(ModelMap modelo) {

        List<Usuario> listaUsuario = serviUsuario.listaUsuario();
        List<Profesional> listaProfesional = serviProfesional.listaProfesionales();
        modelo.addAttribute("listaUsuario", listaUsuario);
        modelo.addAttribute("listaProfesional", listaProfesional);
        return "listaUsuarios.html";
    }
    
   
    
    @GetMapping("/listaProfesionales")
    public String mostrarProfesionales(ModelMap modelo) {

        List<Usuario> listaUsuario = serviUsuario.listaUsuario();
        List<Profesional> listaProfesional = serviProfesional.listaProfesionales();
        modelo.addAttribute("listaUsuario", listaUsuario);
        modelo.addAttribute("listaProfesional", listaProfesional);
        return "listaProfesionales.html";
    }
    
    

    @GetMapping("/eliminar/{dni}")
    public String eliminarUsuario(@PathVariable String dni) {
        try {
            serviUsuario.eliminarUsuario(dni);
            return "redirect:../listaUsuarios";
        } catch (Exception e) {
            System.out.println(e);
            return "AdmDashboard.html";
        }

    }

    @GetMapping("/eliminado/{matricula}")
    public String eliminarProfesional(@PathVariable String matricula) {

        serviProfesional.eliminarProfesional(matricula);
        return "redirect:../listaProfesionales";
    }

    @GetMapping("/modificarUsuario/{dni}")
    public String mostrarFormularioModificarUsuario(@PathVariable String dni, ModelMap modelo) {
        Usuario usuario = serviUsuario.getOne(dni);
        modelo.addAttribute("usuario", usuario);
        return "usuarioModificar.html";
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
            serviUsuario.modificarUsuario(archivo, dni, nombre, apellido, eMail, telefono, password, password2, nombreObraSocial);
            
            modelo.put("exito", "Profesional registrado correctamente!");
            
        } catch (MiException e) {

            modelo.put("error", e);
            
        }
        return "redirect:../listaUsuarios";
    }

    @GetMapping("/modificarProfesional/{matricula}")
    public String mostrarFormularioModificarProfesional(@PathVariable String matricula, ModelMap modelo) {
        Profesional profesional = serviProfesional.getOne(matricula);
        modelo.addAttribute("profesional", profesional);
        return "modificarProfesional.html";
    }

    @PostMapping("/modificadoProfesional/{matricula}")
    public String modificarProfesional(@PathVariable("matricula") String matricula,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("especialidad") String especialidad,
            @RequestParam("telefono") String telefono,
            @RequestParam("eMail") String eMail,
            @RequestParam("password") String password,
            @RequestParam("password2") String password2,
            ModelMap modelo,
            @RequestParam("archivo") MultipartFile archivo
    ) throws MiException {
        try {
          
            serviProfesional.modificarProfesional(archivo, nombre, apellido, matricula, especialidad, eMail, telefono, password, password2);

            modelo.put("exito", "Profesional registrado correctamente!");

        } catch (MiException ex) {

            modelo.put("Error", ex.getMessage());
        }

        return "redirect:../listaProfesionales";
    }

    @GetMapping("/registrarUsuario")
    public String registrarUsuario(Usuario usuario, ObrasSociales obraSocial, ModelMap modelo) {
        modelo.put("usuario", usuario);
        modelo.put("obraSocial", obraSocial);
        return "RegistroUsuario.html";
    }

    @PostMapping("/registradoUsuario")   // IMPORTANTE!!  EL  obrasocial trae la seleccionada y el obraSocial(S mayuscula)inyecta el enum para solucionar un error 
    public String registroUsuario(@RequestParam String dni, String nombre, String apellido, String eMail, boolean obraSocialBoleano, ObrasSociales obrasocial, ObrasSociales obraSocial, String telefono, String password, String password2, Usuario usuario, ModelMap modelo, MultipartFile archivo) throws MiException, ValidationException {

        try {

            serviUsuario.registrar(archivo, dni, nombre, apellido, eMail, obraSocialBoleano, obrasocial, telefono, password, password2);
            modelo.put("usuario", usuario);
            modelo.put("obraSocial", obraSocial);
            modelo.put("exito", "Usuario registrado correctamente!");

        } catch (MiException | ValidationException ex) {

            modelo.put("Error", ex.getMessage());
            homeControlador.inyeccionDeDatos(dni, nombre, apellido, eMail, telefono, password, password2, modelo, ex);
        }

        return "AdmDashboard.html";
    }
    @PostMapping("/darAlta/{dni}")
    public String darAlta(@PathVariable String dni) throws MiException{
            System.out.println("DNIIIIIIIII");
            System.out.println(dni);
            serviUsuario.darDeBajaAlta(dni);
      
        
        return"redirect:../listaUsuarios";
    }
}
