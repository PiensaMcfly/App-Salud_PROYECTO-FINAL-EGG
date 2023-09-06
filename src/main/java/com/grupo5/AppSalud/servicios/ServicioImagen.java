
package com.grupo5.AppSalud.servicios;

import com.grupo5.AppSalud.entities.Imagen;
import com.grupo5.AppSalud.exepciones.MiException;
import com.grupo5.AppSalud.repository.ImagenRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ServicioImagen {
    @Autowired
    private ImagenRepository imagenRepositorio;
    
    public Imagen guardarImagen(MultipartFile archivo) throws MiException{ //cambio nombre del método: guardar -> guardarImagen - gise
        if (archivo != null){
            try{
                Imagen imagen = new Imagen();
                
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
             
                return imagenRepositorio.save(imagen);
            }catch (Exception e) {
                System.err.println(e.getMessage());
                throw new MiException("Error al guardar imagen"); //agrego mensaje de excepción - gise
            }
        }
        return null;
    }
    
    public Imagen actualizarImagen(MultipartFile archivo, String idImagen) throws MiException{ //cambio nombre del método: actualizar -> actualizarImagen - gise
        
        if(archivo != null){
            try {
                Imagen imagen = new Imagen();
                
                if(idImagen != null){
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    if(respuesta.isPresent()){
                        imagen = respuesta.get();
                    }

                }
                
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                
                return imagenRepositorio.save(imagen);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                throw new MiException("Error al actualizar imagen"); //agrego mensaje de excepción - gise
            }
        }      
        return null;
    }
    
    //Agrego método para eliminar imagen - gise
    public Imagen eliminarImagen(MultipartFile archivo, String idImagen) throws MiException{
        
        if(archivo != null){
            
            try {
                Imagen imagen;
                
                if(idImagen != null){
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    
                    if(respuesta.isPresent()){
                       imagen = respuesta.get();
                       imagenRepositorio.delete(imagen);
                    }
                }
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
                throw new MiException("Error al eliminar imagen");
            }
        }      
        return null;
    }


}
