

package com.grupo5.AppSalud.servicios;
//elimino importacones innecesarias
import com.grupo5.AppSalud.entities.HistoriaClinica;
import com.grupo5.AppSalud.entities.Usuario;
import com.grupo5.AppSalud.exepciones.MiException;
import com.grupo5.AppSalud.repository.FichaPacienteRepository;
import com.grupo5.AppSalud.repository.HistoriaClinicaRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Gisela Rantucho
 */
//creo servicio vacio
@Service
public class ServicioHistoriaClinica {
   @Autowired
   HistoriaClinicaRepository historiaClinicaRepositorio;
   @Autowired 
   ServicioUsuario servicioUsuario;
   
    @Transactional
    public HistoriaClinica registrar(String idhc, List fichapaciente, Usuario usuario, String observaciones) throws MiException{
        validar( usuario, observaciones);
        
    HistoriaClinica historiaClinica = new HistoriaClinica();  
    historiaClinica.setIdhc(idhc);
    historiaClinica.setFichapaciente(fichapaciente);
    historiaClinica.setUsuario(usuario);
    historiaClinica.setObservaciones(observaciones);
    historiaClinicaRepositorio.save(historiaClinica);
        return historiaClinica;
        
    }
        
    public void modificar(String idhc, List fichapaciente, Usuario usuario, String observaciones) throws MiException{
        validar( usuario, observaciones);
        Optional<HistoriaClinica> respuesta = historiaClinicaRepositorio.findById(idhc);
    
        if (respuesta.isPresent()) {
            HistoriaClinica historiaClinica = respuesta.get();
            
            historiaClinica.setFichapaciente(fichapaciente);
            historiaClinica.setUsuario(usuario);
            historiaClinica.setObservaciones(observaciones);
            
        historiaClinicaRepositorio.save(historiaClinica);
        }
    }
    
    public HistoriaClinica getOne(String idhc){
        return historiaClinicaRepositorio.getOne(idhc); 
    }
    
    @Transactional
    public void darDeBaja(String matricula) { 
        Optional<HistoriaClinica> respuesta = historiaClinicaRepositorio.findById(matricula); 
        if (respuesta.isPresent()) {
            HistoriaClinica historiaClinica = respuesta.get();
            historiaClinica.setAlta(false);

            historiaClinicaRepositorio.save(historiaClinica);
        }
    }
    
        @Transactional
    public void darDeAlta(String matricula) { 
        Optional<HistoriaClinica> respuesta = historiaClinicaRepositorio.findById(matricula); 
        if (respuesta.isPresent()) {
            HistoriaClinica historiaClinica = respuesta.get();
            historiaClinica.setAlta(true);

            historiaClinicaRepositorio.save(historiaClinica);
        }
    }
    
    public void eliminarHistoriaClinica(String idhc){
    
        historiaClinicaRepositorio.deleteById(idhc);
        
    }
    
    //valiciones de Historia Clinica
    public void validar( Usuario usuario, String observaciones) throws MiException{
        
        if ( usuario == null) {/// Revisar como evaluar "isEmpty" para un date
            throw new MiException("El usuario no puede ser nulo o estar vacío");
        }
         if ( observaciones == null) {
            throw new MiException("El campo de observaciones no pueden ser nulo o estar vacío");
        }
    }
    
    
}
