/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 /*
 */
package com.grupo5.AppSalud.controladores;

import com.grupo5.AppSalud.entities.Profesional;
import com.grupo5.AppSalud.entities.Turnero;
import com.grupo5.AppSalud.entities.Usuario;
//import com.grupo5.AppSalud.entities.Turnos;
import com.grupo5.AppSalud.enumeraciones.ObrasSociales;
import com.grupo5.AppSalud.enumeraciones.Rol;
import com.grupo5.AppSalud.repository.ProfesionalRepository;
import com.grupo5.AppSalud.exepciones.MiException;
import com.grupo5.AppSalud.repository.TurneroRepository;
import com.grupo5.AppSalud.repository.UsuarioRepository;
import com.grupo5.AppSalud.servicios.ServicioProfesional;
import com.grupo5.AppSalud.servicios.ServicioUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
 *
 * @author fitog
 */
@Controller
@RequestMapping("/")
public class HomeControlador {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private ServicioProfesional servicioProfesional;

    @Autowired
    private ProfesionalRepository repositorioProfesional;

    @Autowired
    private TurneroRepository repositorioTurnero;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/login")                //Agregado Tincho-

    public String login(@RequestParam(required = false) String error, ModelMap modelo) throws MiException {
        if (error != null) {
            modelo.put("Error", "Usuario o Contraseña invalidos!");
            System.out.println("Entre1");
            return "loginProfesional.html";
        }
        modelo.put("Exito", "Se ha logueado con exito!");
        System.out.println("Entre2");
        return "loginProfesional.html";

    }

    /// agrego inicio para probar login exitoso
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'PACIENTE', 'MEDICO')")
    @GetMapping("/inicio")
    public String inicio() {
        return "pruebaturno.html";
    }

    @PostMapping("/profesionales")
    public String tablaProfesionales(@RequestParam("especialidad") String especialidad, ModelMap model) {
        List<Profesional> profesionales = servicioProfesional.listaProfesionales();
        List<Profesional> profesionalesFiltrados = profesionales.stream()
                .filter(profesional -> profesional.getEspecialidad().equals(especialidad))
                .collect(Collectors.toList());
        model.addAttribute("especialidad", especialidad);
        model.addAttribute("profesionales", profesionalesFiltrados);
        return "tablaProfesionales";
    }

    @GetMapping("/1")
    public String profs(ModelMap modelo) {                                                         // CONTROLADOR DE PRUEBA NADA MAS 

        List<Profesional> profesionales = servicioProfesional.listaProfesionales();
        modelo.addAttribute("profesionales", profesionales);
        return "index1.html";
    }

    @PostMapping("/detalleprof/{matricula}")
    public String detalleProf(@PathVariable String matricula, @RequestParam("especialidad") String especialidad, ModelMap model) {
        List<Profesional> listaCompleta = servicioProfesional.listaProfesionales();
        List<Profesional> listaFiltrada = listaCompleta.stream()
                .filter(profesional -> profesional.getEspecialidad().equals(especialidad))
                .collect(Collectors.toList());

        // Modificación para poder enviar el profesional elegido a la vista de detalleProfesional
        Optional<Profesional> respuesta = repositorioProfesional.findById(matricula);
        Profesional profElegido = respuesta.get();

        model.addAttribute("profElegido", profElegido); // Aquí agrego como atributo el Prof Elegido
        model.addAttribute("profesionales2", listaFiltrada);
        model.addAttribute("matricula", matricula);
        model.addAttribute("especialidad", especialidad);

//        ---Listas para busqueda de fecha y hora del turnero----
        List<String> listafechaprof = repositorioTurnero.fechasDisponiblesProf(matricula);
        model.addAttribute("fechasdispo", listafechaprof);

        List<Object[]> turnosConId = repositorioTurnero.fechasHorariosConId(matricula);
        model.addAttribute("turnosConId", turnosConId);

        return "detalleProfesional";
    }

    @PostMapping("/selectturno/{matricula}")
    public String selectTurno(@PathVariable String matricula, @RequestParam("fecha") String fecha, ModelMap model) {

        List<String> listahoraprof = repositorioTurnero.horariosDispo(matricula, fecha);
        model.addAttribute("horasdispo", listahoraprof);

        return "horasdispo";
    }

    @GetMapping("/registrar")
    public String registrarUsuario(Usuario usuario, ObrasSociales obraSocial, ModelMap modelo) {
        modelo.put("usuario", usuario);
        modelo.put("obraSocial", obraSocial);
        return "RegistroUsuario.html";
    }

    @PostMapping("/registroUsuario")   // IMPORTANTE!!  EL  obrasocial trae la seleccionada y el obraSocial(S mayuscula)inyecta el enum para solucionar un error 
    public String registroUsuario(@RequestParam String dni, String nombre, String apellido, String eMail, boolean obraSocialBoleano, ObrasSociales obrasocial, ObrasSociales obraSocial, String telefono, String password, String password2, Usuario usuario, ModelMap modelo, MultipartFile archivo) throws MiException, ValidationException {

        try {

            servicioUsuario.registrar(archivo, dni, nombre, apellido, eMail, obraSocialBoleano, obrasocial, telefono, password, password2);
            modelo.put("usuario", usuario);
            modelo.put("obraSocial", obraSocial);
            modelo.put("exito", "Usuario registrado correctamente!");

        } catch (MiException | ValidationException ex) {

            modelo.put("Error", ex.getMessage());
            inyeccionDeDatos(dni, nombre, apellido, eMail, telefono, password, password2, modelo, ex);
        }

        return "RegistroUsuario.html";
    }

    @GetMapping("/registrarprofesional")
    public String registrarProfesional() {

        return "RegistroProfesional.html";
    }

    @PostMapping("/registroProfesional")
    public String registroProf(@RequestParam String matricula, String nombre, String apellido, String especialidad, String telefono, String eMail, String password, String password2, Boolean alta, Rol rol, ModelMap modelo, MultipartFile archivo) throws MiException {

//        servicioProfesional.registrar(nombre, apellido, matricula, especialidad,telefono,eMail,password,password2,alta, rol);
        // esta Deberia Ser la Linea  para poder enganchar los
        // mensajes de error y exito con el fragment de Error(Cartelito Verde y ROjO)    
        try {

            servicioProfesional.registrar(archivo, nombre, apellido, matricula, especialidad, telefono, eMail, password, password2, alta, rol); //  

            modelo.put("exito", "Profesional registrado correctamente!");

        } catch (MiException ex) {

            modelo.put("Error", ex.getMessage());
        }

        return "RegistroProfesional.html";
    }

    public void inyeccionDeDatos(String dni, String nombre, String apellido, String eMail, String telefono, String password, String password2, ModelMap modelo, Exception ex) {

        if (ex.getMessage().contains("Nombre")) {
            modelo.put("apellido", apellido);
            modelo.put("dni", dni);
            modelo.put("telefono", telefono);
            modelo.put("password", password);
            modelo.put("password2", password2);
            modelo.put("eMail", eMail);
        }

        //Agrego las que faltan
        if (ex.getMessage().contains("Apellido")) {
            modelo.put("nombre", nombre);
            modelo.put("dni", dni);
            modelo.put("telefono", telefono);
            modelo.put("password", password);
            modelo.put("password2", password2);
            modelo.put("eMail", eMail);
        }
        if (ex.getMessage().contains("dni")) {
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("telefono", telefono);
            modelo.put("password", password);
            modelo.put("password2", password2);
            modelo.put("eMail", eMail);
        }
        if (ex.getMessage().contains("Telefono")) {
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("dni", dni);
            modelo.put("password", password);
            modelo.put("password2", password2);
            modelo.put("eMail", eMail);
        }
        if (ex.getMessage().contains("Contraseña")) {
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("dni", dni);
            modelo.put("telefono", telefono);
            modelo.put("eMail", eMail);

        }
        if (ex.getMessage().contains("email")) {
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("dni", dni);
            modelo.put("telefono", telefono);
            modelo.put("password", password);
            modelo.put("password2", password2);
        }

    }

    
    
    @GetMapping("/aboutUs")
    public String SobreNosotros() {

        return "AboutUs.html";
    }
    
    
}
