package com.grupo5.AppSalud.controladores;

import com.grupo5.AppSalud.entities.Profesional;
import com.grupo5.AppSalud.entities.Usuario;
import com.grupo5.AppSalud.exepciones.MiException;
import com.grupo5.AppSalud.servicios.ServicioProfesional;
import com.grupo5.AppSalud.servicios.ServicioUsuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/profesional")
public class ProfesionalControlador {

    @Autowired
    private ServicioProfesional serviProfesional;
    @Autowired
    private ServicioUsuario serviUsuario;

    @GetMapping("/dashboard")
    public String panelProfesional(ModelMap modelo) {

        List<Usuario> listaUsuario = serviUsuario.listaUsuario();
        List<Profesional> listaProfesional = serviProfesional.listaProfesionales();
        modelo.addAttribute("listaUsuario", listaUsuario);
        modelo.addAttribute("listaProfesional", listaProfesional);
        return "ProfesionalDashboard.html";
    }
    @GetMapping("/modificarPerfilProfesional/{matricula}")
    public String mostrarFormularioModificarProfesional(@PathVariable String matricula, ModelMap modelo) {
        Profesional profesional = serviProfesional.getOne(matricula);
        modelo.addAttribute("profesional", profesional);
        return "modificarPerfilProfesional.html";
    }

    @PostMapping("/modificadoPerfilProfesional/{matricula}")
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

            modelo.put("exito", "Profesional modificado correctamente!");

        } catch (MiException ex) {

            modelo.put("Error", ex.getMessage());
        }

        return "redirect:../dashboard";
    }

}
