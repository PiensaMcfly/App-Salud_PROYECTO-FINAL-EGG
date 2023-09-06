/*
 */
package com.grupo5.AppSalud.servicios;

import com.grupo5.AppSalud.entities.FichaPaciente;
import com.grupo5.AppSalud.entities.Profesional;
import com.grupo5.AppSalud.entities.Turnero;
import com.grupo5.AppSalud.entities.Usuario;
import com.grupo5.AppSalud.enumeraciones.ObrasSociales;
import com.grupo5.AppSalud.exepciones.MiException;
import com.grupo5.AppSalud.repository.FichaPacienteRepository;
import com.grupo5.AppSalud.repository.ProfesionalRepository;
import com.grupo5.AppSalud.repository.TurneroRepository;
import com.grupo5.AppSalud.repository.UsuarioRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author fitog
 */
@Service
public class ServicioFichaPaciente {

    @Autowired
    ServicioHistoriaClinica serviHistoriaClinica;
    @Autowired
    FichaPacienteRepository fichapacienteRepositorio;

    @Autowired
    private TurneroRepository turneroRepository;

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HttpSession httpSession;

    @Transactional
    public void registrar(String notasDeLaVisita, String fechaTurno, ObrasSociales os, Usuario paciente, String anotacionesFicha, String matricula, String dni, String idTurno) throws MiException, ParseException {
        //Elimino el if debido a que la ficha se crea al asignar turno.
        FichaPaciente fichapaciente = new FichaPaciente();
        Turnero turno = turneroRepository.buscarPorId(idTurno);
        fichapaciente.setTurno(turno);
// Conversión de String a Date para la fecha del turno  

        fichapaciente.setFecha(fechaTurno);
        fichapaciente.setNotasDeLaVisita("");
        fichapaciente.setOs(turno.getUsuario().getNombreObraSocial());
        fichapaciente.setAnotacionesFicha(anotacionesFicha);
        Profesional profesional = profesionalRepository.buscarPorMatricula(matricula);
        fichapaciente.setProfesional(profesional);
        fichapacienteRepositorio.save(fichapaciente);
        
        List<FichaPaciente> ficha = turno.getUsuario().getHistoriaC().getFichapaciente();
        ficha.add(fichapaciente);
        serviHistoriaClinica.modificar(turno.getUsuario().getDni(),ficha , turno.getUsuario(), notasDeLaVisita);
    }

    public void eliminarFichaPaciente(String id) {
        fichapacienteRepositorio.deleteById(id);
    }

    public FichaPaciente buscarFichaPacientePorTurno(String turno) {
        return fichapacienteRepositorio.buscarPorTurno(turno);
    }

    public List<FichaPaciente> listaFichas() {
        List<FichaPaciente> fichasDePacientes = fichapacienteRepositorio.buscarFichasPorProfesional(obtenerMatriculaProfesionalDeSesion());
        return fichasDePacientes;
    }

    public String obtenerMatriculaProfesionalDeSesion() { // Cambio de private a public
        Profesional profesionalEnSesion = (Profesional) httpSession.getAttribute("usuariosession");
        if (profesionalEnSesion != null) {
            return profesionalEnSesion.getMatricula();
        }
        return null;
    }

    //validaciones de ingreso de datos validar(notasDeLaVisita, fecha, os, paciente, profesional, anotacionesFicha);
//    public void validar(String notasDeLaVisita, Date fecha, ObrasSociales os, Usuario paciente, Profesional profesional, String anotacionesFicha) throws MiException {
//
//        if (notasDeLaVisita.isEmpty() || notasDeLaVisita == null) {
//            throw new MiException("Notas De La Visita (intencion de consulta) no puede ser nulo o estar vacio");
//        }
//        if (fecha == null) {/// Revisar como evaluar "isEmpty" para un date
//            throw new MiException("La fecha no puede ser nula o estar vacia");
//        }
//        if (os == null) {
//            throw new MiException("La obra social no puede ser nula o estar vacia");
//        }
//        if (paciente.getNombre().isEmpty() || paciente == null) {
//            throw new MiException("Paciente no puede ser nulo o estar vacio");
//        }
//        if (profesional.getNombre().isEmpty() || profesional == null) {
//            throw new MiException("Profesional no puede ser nulo o estar vacio");
//        }
//        if (anotacionesFicha.isEmpty() || anotacionesFicha == null) {
//            throw new MiException("Anotaciones ficha no puede ser nulo o estar vacio");
//        }
//
//    }
}
