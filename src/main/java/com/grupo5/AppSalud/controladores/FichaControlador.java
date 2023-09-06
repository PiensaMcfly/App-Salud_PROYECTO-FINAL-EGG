/*
 */
package com.grupo5.AppSalud.controladores;

import com.grupo5.AppSalud.entities.Profesional;
import com.grupo5.AppSalud.entities.Usuario;
import com.grupo5.AppSalud.enumeraciones.ObrasSociales;
import com.grupo5.AppSalud.exepciones.MiException;
import com.grupo5.AppSalud.repository.TurneroRepository;
import com.grupo5.AppSalud.servicios.ServicioFichaPaciente;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author fitog
 */
@Controller
@RequestMapping("/fichasPaciente")/// definr ruta html para ficha paciente desde front
public class FichaControlador {

    @Autowired
    private HttpSession session; // Agregar esta inyección para acceder a la sesión

    @Autowired
    private ServicioFichaPaciente servicioFichaPaciente;
    
    @Autowired
    private TurneroRepository turneroRepository;

     @PreAuthorize("hasAnyRole('ROLE_MEDICO')")
    @GetMapping("/registrar_fichaPaciente")/// definr ruta html para ficha paciente desde front
    public String verFichaPaciente(ModelMap model) {
       // List<String> listaTurnosProf = turneroRepository.listaTurnosAsignadosProf(obtenerMatriculaProfesionalDeSesion());
       // model.addAttribute("fechasAsign",listaTurnosProf);
        return "registroFicha.html"; ///llamar fichapaciente.html
    }
    
    @PreAuthorize("hasAnyRole('ROLE_MEDICO')")
    @PostMapping("/fichaPaciente_registrada")/// definr ruta html para ficha paciente desde front
    public String crearFichaPaciente( ModelMap model) {
        String matricula = obtenerMatriculaProfesionalDeSesion();
       // List<String> listaTurnosProf = turneroRepository.listaTurnosAsignadosProf(matricula);
        //model.addAttribute("fechasAsign",listaTurnosProf);
        
        return "index.html"; ///llamar fichapaciente.html
    }

    private String obtenerMatriculaProfesionalDeSesion() {
        Profesional profesionalEnSesion = (Profesional) session.getAttribute("usuariosession");
        if (profesionalEnSesion != null) {
            return profesionalEnSesion.getMatricula();
        }
        // Manejar el caso en que no se encuentre la matrícula en la sesión
        return null;
    }
    
    
}
