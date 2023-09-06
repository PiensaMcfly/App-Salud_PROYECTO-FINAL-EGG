package com.grupo5.AppSalud.servicios;

import com.grupo5.AppSalud.entities.Profesional;
import com.grupo5.AppSalud.entities.Turnos;
import com.grupo5.AppSalud.enumeraciones.DiasSemana;
import com.grupo5.AppSalud.exepciones.MiException;
import com.grupo5.AppSalud.repository.ProfesionalRepository;
import com.grupo5.AppSalud.repository.TurnosRepository;
import com.grupo5.AppSalud.repository.UsuarioRepository;
import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioTurnos {
////servicio turnos es el servicio mas complejo,provablemente tendriamos que colaborar para completarlo!! espero se entiendan los comentarios.
////las notas son para mi ,como para que todos puedan entender que hace el metodo.
    @Autowired
    private UsuarioRepository usuarioRepositorio;
    @Autowired
    private ProfesionalRepository profesionalRepositorio;
    @Autowired
    private TurnosRepository turnosRepositorio;
    
    
    //este primer metodo seria par guardar los datos ingresados por el profesional,para poder armar
    //su calendario de turnos asociados a la fecha(localdatetime)
    @Transactional
    public void cargarDisponibilidadDelProfesional(String idUsuario, String matricula, String idTurnos, String horaComienzoTurno, String horaFinalizacionTurno, DiasSemana diasSemana, Profesional profesional) throws MiException {
        // validar();
        

        Turnos turno = new Turnos();

        turno.setDiasSemana(diasSemana);
        turno.setHoraComienzoTurno(horaComienzoTurno);
        turno.setHoraFinalizacionTurno(horaFinalizacionTurno);
        turno.setProfesional(profesional);
        //asociado a un profesional
        // turno.getProfesional(matricula);
        

        turnosRepositorio.save(turno);

    }
    
    @Transactional
    public void crearTurnosDisponiblesDelProfesional(String idUsuario, String matricula, String idTurnos, String horaComienzoTurno, String horaFinalizacionTurno, DiasSemana diasSemana) throws MiException {
        // validar();
        
        /////primero resolver este metodo!!!!!!!
    //este metodo tendria que agarrar la disponibilidad del profesional y (a partir de la fecha de hoy)y generar los turnos disponibles por profesional, de aqui a una semana??
    //lista de turnosDisponibles en localDateTime (Dia semana??,año ,mes,dia,hora,minuto)
        
//       ano: An integer representing the year.
//       mes: An integer representing the month (1 to 12).
//       dia: An integer representing the day of the month.
//       hora: An integer representing the hour of the day (0 to 23).
//       min: An integer representing the minute of the hour (0 to 59).
//        
//       // arrancar con el dia de hoy
          LocalDateTime hoy = LocalDateTime.now();
         // DiasSemana diaSemanaActual = hoy.getDayOfWeek();este metodo tira un enum revisar bien
          int ano = hoy.getYear();
          int mes = hoy.getMonthValue();
          int dia = hoy.getDayOfMonth();
          

        //hacer un for que vaya seteando los dias de conmsulta para genera una fecha
        //recorriendo los dias de la semana
        //luego recorriendo las horas cad una hora hay un turno pra empezar
        //creo qeu se puede hacer varios if anidados y cuando coincide el dia la hora ,setea la fecha.

        //turnos.getDiasSemana(diasSemana);
        //turnos.getHoraComienzoTurno(horaComienzoTurno);
        //turnos.getHoraFinalizacionTurno(horaFinalizacionTurno);
        // turno.getProfesional(matricula);
       //en este metodo seteamos los disponibles de los profesionales
     
       //  Crear un objeto LocalDateTime
//        LocalDateTime turno = LocalDateTime.of(ano, mes, dia, hora, min);
//        
//        llenar una lista de turnos  
//        List<LocalDateTime> turnos=turno.add;   
//
     
  }

    @Transactional
    public List<LocalDateTime> modificarTurnosDisponibles(String idUsuario, String matricula, String idTurnos, String horaComienzoTurno, String horaFinalizacionTurno, DiasSemana diasSemana) throws MiException {
        // validar();
//este metodo trae la lista de turnos de un profesional y la retorna seteada con el nuevo turno(pedido por el usuario) ocupado!! 
 //osea sale ese truno de la list de turnosdisponibles y entra en la lista de turnosocupados       
// 

//  Optional<Profesional> respuestap = profesionalRepositorio.findById(matricula);
//        Optional<Turnos> respuestat = turnosRepositorio.findById(idTurnos);
//        if (respuestat.isPresent()) {
//            Turnos turnos = respuestat.get();
//
//            turnos.setDiasSemana(diasSemana);
//            turnos.setHoraComienzoTurno(horaComienzoTurno);
//            turnos.setHoraFinalizacionTurno(horaFinalizacionTurno);
//            // turno.getProfesional(matricula);
//            // turno.getUsuario(idUsuario);}

              
           
         
         return null;
    }

   
       public List<Turnos> listarTurnosDisponibles(String matricula) {
        //turnos disponibles por profesional
        List<Turnos> turnosDisponibles = turnosRepositorio.findAll();

        return turnosDisponibles;
    }
        public List<Turnos> listarTurnosOcupados(String matricula) {
        //turnos disponibles por profesional
        List<Turnos> turnosOcupados = turnosRepositorio.findAll();

        return turnosOcupados;
    }
    @Transactional
     public void pedidoTurnoUsuario(String turnoId, String usuarioId) {
     //asigna turno al usuario de x profesional y llama al metodo modificarturnodisponible(borra la disponibilidad)         

          List<LocalDateTime>turnoPedido; 
          
          
    }
    @Transactional
     public void cancelarTurnoUsuario(String turnoId, String usuarioId) {
//cancela turno al usuario de x profesional y llama al metodo modificarturnodisponible(setea disponible la disponibilidad)     
    }
     
    
 
     
//        public void validar() throws MiException {
//
//        if (nombre.isEmpty() || nombre == null) {
//            throw new MiException("El Nombre no puede ser nulo o estar vacio");       
//        }
     
     
     
     
     
     
//    local date time javatime
//        
//        // Imprimir la fecha y hora
//        System.out.println("Fecha y hora: " + dateTime);
//
//        // Formatear la fecha y hora usando un DateTimeFormatter
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//        String dateTimeFormatted = dateTime.format(formatter);
//        System.out.println("Fecha y hora formateada: " + dateTimeFormatted);
//
//        // Parsear una cadena en LocalDateTime
//        String fechaHoraString = "03/07/2016 15:45:30";
//        LocalDateTime parsedDateTime = LocalDateTime.parse(fechaHoraString, formatter);
//        System.out.println("Fecha y hora parseada: " + parsedDateTime);
//        
}
