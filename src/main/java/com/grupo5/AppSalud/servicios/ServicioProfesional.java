/*
 */
package com.grupo5.AppSalud.servicios;

import com.grupo5.AppSalud.entities.Imagen;
import com.grupo5.AppSalud.entities.Profesional;
//import com.grupo5.AppSalud.entities.Turnos;
import com.grupo5.AppSalud.enumeraciones.Rol;
import com.grupo5.AppSalud.exepciones.MiException;
import com.grupo5.AppSalud.repository.ProfesionalRepository;
import com.grupo5.AppSalud.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author fitog
 */
@Service
public class ServicioProfesional {

    @Autowired
    private ProfesionalRepository profesionalRepositorio;//agrego private 
    @Autowired
    private ServicioImagen servicioImagen;//agrego servicio de imagen

    @CacheEvict(value = "profesionales", allEntries = true) // Invalidar caché después del registro
    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String apellido, String matricula, String especialidad, String telefono, String eMail, String password, String password2, Boolean alta, Rol rol) throws MiException {

        validar(nombre, apellido, matricula, especialidad, eMail, telefono, password, password2);  // comentando esta linea si me deja persistir 

        // Verificar si el email ya está registrado
        if (profesionalRepositorio.existsByEmail(eMail)) {
            throw new MiException("El correo electrónico ya está registrado.");
        }

        Profesional profesional = new Profesional();
        profesional.setNombre(nombre);
        profesional.setApellido(apellido);                                        //Agregamos mas parametros
        profesional.setMatricula(matricula);
        profesional.setEspecialidad(especialidad);
        profesional.setTelefono(telefono);
        profesional.setEMail(eMail);
        profesional.setPassword(new BCryptPasswordEncoder().encode(password));
        profesional.setAlta(Boolean.FALSE);
        profesional.setRol(Rol.MEDICO);

        Imagen imagen = servicioImagen.guardarImagen(archivo);
        profesional.setImagen(imagen);

//        profesional.setReputacion(reputacion);
        profesionalRepositorio.save(profesional);

    }

    public List<Profesional> listaProfesionales() {

        List<Profesional> profesionales = profesionalRepositorio.findAll();

        return profesionales;
    }

    @Transactional
    public void modificarProfesional(MultipartFile archivo, String nombre, String apellido, String matricula, String especialidad, String eMail, String telefono, String password, String password2) throws MiException {     
        validar(nombre, apellido, matricula, especialidad, eMail, telefono, password, password2);
        Optional<Profesional> respuesta = profesionalRepositorio.findById(matricula);

        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();

            profesional.setNombre(nombre);
            profesional.setApellido(apellido);
            profesional.setMatricula(matricula);                            //agregado de set a atributos que faltaban .uriel
            profesional.setEspecialidad(especialidad);
            profesional.setEMail(eMail);
            profesional.setTelefono(telefono);
            profesional.setPassword(new BCryptPasswordEncoder().encode(password));

            String idImagen = null;
            if (profesional.getImagen() != null) {
                idImagen = profesional.getImagen().getIdImagen();
            }
            Imagen imagen = servicioImagen.actualizarImagen(archivo, idImagen);
            profesional.setImagen(imagen);

            profesionalRepositorio.save(profesional);
        }

    }

    public Profesional getOne(String matricula) {
        return profesionalRepositorio.getOne(matricula);

    }

    @Transactional
    public void darDeBaja(String matricula) {
        Optional<Profesional> respuesta = profesionalRepositorio.findById(matricula);
        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();
            profesional.setAlta(false);

            profesionalRepositorio.save(profesional);
        }
    }

    @Transactional
    public void darDeAlta(String matricula) { //Cambio id por Matricula
        Optional<Profesional> respuesta = profesionalRepositorio.findById(matricula); //Cambio id por Matricula

        if (respuesta.isPresent()) {
            Profesional profesional = respuesta.get();
            profesional.setAlta(true);

            profesionalRepositorio.save(profesional);
        }

    }

    @Transactional
    public void eliminarProfesional(String matricula) {

        profesionalRepositorio.deleteById(matricula);

    }

    //validaciones de ingreso de datos
    public void validar(String nombre, String apellido, String matricula, String especialidad, String eMail, String telefono, String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El Nombre no puede ser nulo o estar vacio");

        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El Apellido no puede ser nulo o estar vacio");

        }
        if (matricula.isEmpty() || matricula == null || matricula.length() < 7) {
            throw new MiException("La Matricula no puede ser nula o estar vacio "
                    + "Debe contener almenos 7 caracteres");

        }
        if (especialidad.isEmpty() || especialidad == null) {
            throw new MiException("La Especialidad no puede ser nula o estar vacia");

        }
        if (eMail.isEmpty() || eMail == null) {
            throw new MiException("El Email no puede ser nulo o estar vacio");

        }
        if (telefono.isEmpty() || telefono == null || telefono.length() < 10) {
            throw new MiException("El Telefono no puede estar vacio y debe tener 10 numero incluyendo el codigo de area");

        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La Contraseña no puede estar vacia y debe tener mas de 5 digitos");

        }
        if (!password.equals(password2)) {
            throw new MiException("Las Contraseñas ingresadas deben ser iguales");

        }

    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//       
//        Profesional profesional = profesionalRepositorio.buscarPorEmail(email);
//        
//        if (profesional != null) {
//            
//            List<GrantedAuthority> permisos = new ArrayList();
//            System.out.println("Profesional encontrado: " + profesional.getNombre());
//            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + profesional.getRol().toString());
//            
//            permisos.add(p);
//            
//            User user = new User(profesional.getEMail(), profesional.getPassword(), permisos);
//            return new User(email, profesional.getPassword(), permisos);
//        } else {
//            throw new UsernameNotFoundException("Usuario no encontrado");
//        }
//    }
}
