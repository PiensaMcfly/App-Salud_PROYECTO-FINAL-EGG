/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author chris
 */
@Service
public class ServicioTurnero { //No le veo la necesidad de generar una validación si nos manejamos con un Option Form

    @Autowired
    private TurneroRepository turneroRepositorio;

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Autowired
    private ServicioFichaPaciente fichaServi;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FichaPacienteRepository fichaRepository;

    @Autowired
    private HttpSession httpSession;

    public void registrar(String hora, String fecha, String matricula) throws ValidationException {
        String matriculaProfesional = obtenerMatriculaProfesionalDeSesion();
        
        validar(matricula, fecha, hora);

        if (matriculaProfesional != null) {
            Turnero turnero = new Turnero();
            turnero.setFecha(fecha);
            turnero.setHorario(hora);
            turnero.setReserva(Boolean.TRUE);

            Profesional profesional = profesionalRepository.buscarPorMatricula(matricula);
            if (profesional != null) {
//                se agrega el turno a la lista de turnos del profesional------------------------------
                turnero.setProfesional(profesional);
                turneroRepositorio.save(turnero);
                List<Turnero> listaTurneros = profesional.getTurneros();
                listaTurneros.add(turnero);
                profesional.setTurneros(listaTurneros);
                profesionalRepository.save(profesional);
            } else {
                // Manejar el caso en que no se encuentre el profesional
                System.out.println("No se encuentra el profesional.");
            }
        } else {
            // Manejar el caso en que no se encuentre la matrícula en la sesión
            System.out.println("No se encuentra la matrícula en la sesión");
        }
    }

    private String obtenerMatriculaProfesionalDeSesion() {
        Profesional profesionalEnSesion = (Profesional) httpSession.getAttribute("usuariosession");
        if (profesionalEnSesion != null) {
            return profesionalEnSesion.getMatricula();
        }
        return null;
    }

    @Transactional
    public void eliminarTurnero(String id) {
        // Obtén la lista de turneros
        List<Turnero> listaTurneros = turneroRepositorio.buscarPorId(id).getProfesional().getTurneros();

        // Itera sobre la lista para encontrar el Turnero con el ID deseado
        Turnero turneroAEliminar = null;
        for (Turnero turnero : listaTurneros) {
            if (turnero.getId().equals(id)) {
                turneroAEliminar = turnero;
                break; // Sal del bucle una vez que se encuentre el Turnero
            }
        }

        // Si se encontró el Turnero, elimínalo de la lista y luego de la base de datos
        if (turneroAEliminar != null) {
            listaTurneros.remove(turneroAEliminar); // Elimina de la lista en memoria
            turneroRepositorio.deleteById(id); // Elimina de la base de datos
        }
    }

    public void validar(String matricula, String fecha, String hora) throws ValidationException {

     Turnero turneroExiste = turneroRepositorio.validarTurnoExiste(matricula, fecha, hora);
        if (turneroExiste != null) {
            throw new ValidationException("Ya existe un turno registrado con esta fecha y horario");
    }
    }
    
    

    public List<Turnero> listaTurnosPorMatricula(String matricula) {
        String matriculaProfesional = obtenerMatriculaProfesionalDeSesion();
        Profesional profesional = profesionalRepository.buscarPorMatricula(matriculaProfesional);

        if (profesional != null) {
            return profesional.getTurneros();
        } else {
            // Manejar el caso en que no se encuentre el profesional
            System.out.println("No se encuentra el profesional.");
            return new ArrayList<>(); // Devuelve una lista vacía en caso de no encontrar al profesional
        }
    }

    public void asignarTurno(String idTurno, String notasTurnero, String dni) throws MiException, ParseException {
        // Buscar el turno por su ID
        Turnero turnero = turneroRepositorio.buscarPorId(idTurno);

        if (turnero != null) {
            // Verificar si el turno aún está disponible (Reserva == true)
            if (turnero.getReserva()) {
                // Buscar al usuario por su DNI
                Usuario usuario = usuarioRepository.buscarPorDni(dni);

                if (usuario != null) {
                    // Asignar el turno al usuario y actualizar notas y estado
                    turnero.setUsuario(usuario);
                    turnero.setNotasTurnero(notasTurnero);
                    turnero.setReserva(false); // Marcar el turno como no disponible
                    // Guardar el turno actualizado en la base de datos
                    turneroRepositorio.save(turnero);
                    fichaServi.registrar(turnero.getNotasTurnero(), turnero.getFecha(), turnero.getUsuario().getNombreObraSocial(), turnero.getUsuario(), "", turnero.getProfesional().getMatricula(), turnero.getUsuario().getDni(), idTurno);
                } else {
                    throw new MiException("No se encuentra el usuario.");
                    // Puedes lanzar una excepción o manejar esto de otra manera según tu necesidad.
                }
            } else {
                throw new MiException("El turno ya no está disponible.");
                // Puedes lanzar una excepción o manejar esto de otra manera según tu necesidad.
            }
        } else {
           throw new MiException("No se encuentra el turno.");
            // Puedes lanzar una excepción o manejar esto de otra manera según tu necesidad.
        }
    }

    private Usuario obtenerDniUsuarioEnSesion() {
        Usuario usuarioEnSesion = (Usuario) httpSession.getAttribute("usuariosession");
        if (usuarioEnSesion != null) {
            return usuarioEnSesion;
        }
        return null;
    }

}
