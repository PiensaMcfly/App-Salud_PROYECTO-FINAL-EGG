/*
 */
package com.grupo5.AppSalud.servicios;

import com.grupo5.AppSalud.entities.FichaPaciente;
import com.grupo5.AppSalud.entities.HistoriaClinica;
import com.grupo5.AppSalud.entities.Imagen;
import com.grupo5.AppSalud.entities.Usuario;
import com.grupo5.AppSalud.enumeraciones.ObrasSociales;
import com.grupo5.AppSalud.enumeraciones.Rol;
import com.grupo5.AppSalud.exepciones.MiException;
import com.grupo5.AppSalud.repository.ImagenRepository;
import com.grupo5.AppSalud.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 *
 * @author fitog
 */
@Service
public class ServicioUsuario {

    @Autowired
    private UsuarioRepository usuarioRepositorio;
    @Autowired
    private ServicioImagen servicioImagen; //traigo servico de imagen - gise
    @Autowired
    @Lazy
    private ServicioHistoriaClinica servicioHistoriaClinica;

    //@CacheEvict(value = "usuarios", allEntries = true) // Invalidar caché después del registro
    @Transactional
    public void registrar(MultipartFile archivo, String dni, String nombre, String apellido, String eMail,boolean obraSocialBoleano,ObrasSociales obrasocial, String telefono, String password, String password2) throws MiException, ValidationException {//agrego el parametro para la imagen - gise

        validar(dni, nombre, apellido, eMail, telefono, password, password2);
        List<FichaPaciente> listaVacia = new ArrayList<>();
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEMail(eMail);
        usuario.setObraSocial(obraSocialBoleano);
        usuario.setNombreObraSocial(obrasocial);
        usuario.setTelefono(telefono);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setAlta(true);
        usuario.setRol(Rol.PACIENTE);
        Imagen imagen = servicioImagen.guardarImagen(archivo);
        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);
            
        HistoriaClinica historiaClinica = servicioHistoriaClinica.registrar(dni, listaVacia, usuario,telefono); 
        persistenciaHistoriaClinica(archivo, dni, nombre, apellido, eMail, telefono, password, password2, obrasocial, historiaClinica);
        
    }
    @Transactional
    public void persistenciaHistoriaClinica(MultipartFile archivo, String dni, String nombre, String apellido, String eMail, String telefono, String password, String password2, ObrasSociales nombreObraSocial,HistoriaClinica historiaClinica) throws MiException{
     Optional<Usuario> respuesta = usuarioRepositorio.findById(dni);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEMail(eMail);
            usuario.setTelefono(telefono);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setNombreObraSocial(nombreObraSocial);

            String idImagen = null;
            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getIdImagen();
            }
            Imagen imagen = servicioImagen.actualizarImagen(archivo, idImagen);
            usuario.setImagen(imagen);
            usuario.setHistoriaC(historiaClinica);
            usuarioRepositorio.save(usuario);
        }
    
    }
    //Agregado listar Usuario  .uriel
    public List<Usuario> listaUsuario() {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }

//    @Override
//    public UserDetails loadUserByUsername(String eMail) throws UsernameNotFoundException {
//        Usuario usuario = usuarioRepositorio.buscarPorEmail(eMail);
//
//        if (usuario != null) {
//            List<GrantedAuthority> permisos = new ArrayList();
//            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
//            permisos.add(p);
//
//            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//            HttpSession session = attr.getRequest().getSession(true);                                                    // Descomento y Agrego Http Session - Martin
//            session.setAttribute("usuariosession", usuario);
//            return new User(usuario.getEMail(), usuario.getPassword(), permisos);
//        } else {
//            return null;
//        }
//    }

    @Transactional
    public void darDeBaja(String dni) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(dni);
        if (respuesta.isPresent()) {
            Usuario profesional = respuesta.get();
            profesional.setAlta(false);
        }
    }

    public Usuario getOne(String dni) {
        return usuarioRepositorio.getOne(dni);
    }

    @Transactional
    public void darDeAlta(String dni) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(dni);
        if (respuesta.isPresent()) {
            Usuario profesional = respuesta.get();
            profesional.setAlta(true);

            usuarioRepositorio.save(profesional);
        }
    }

    @Transactional
    public void modificarUsuario(MultipartFile archivo, String dni, String nombre, String apellido, String eMail, String telefono, String password, String password2, ObrasSociales nombreObraSocial) throws MiException, ValidationException {//agrego el parametro para la imagen - gise

        //validar(dni, nombre, apellido, eMail, telefono, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(dni);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEMail(eMail);
            usuario.setTelefono(telefono);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setNombreObraSocial(nombreObraSocial);

            String idImagen = null;
            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getIdImagen();
            }
            Imagen imagen = servicioImagen.actualizarImagen(archivo, idImagen);
            usuario.setImagen(imagen);

            usuarioRepositorio.save(usuario);
        }
    }

    @Transactional
    public void eliminarUsuario(String dni) {
        usuarioRepositorio.deleteById(dni);
    }


    public void validar(String dni, String nombre, String apellido, String email, String telefono, String password, String password2) throws MiException, ValidationException{
        
        Usuario usuarioExistente = usuarioRepositorio.buscarPorEmail(email);
        if (usuarioExistente != null) {
            throw new ValidationException("Ya existe un usuario registrado con ese email");
        }

        Usuario usuarioExistente2 = usuarioRepositorio.buscarPorDni(dni);
        if (usuarioExistente2 != null) {
            throw new ValidationException("Ya existe un usuario con ese DNI");
        }
                
        if (dni.isEmpty() || dni == null || dni.length() < 8) {
            throw new MiException("El Numero de Documento no puede estar vacio, y debe tener al menos 8 digitos");
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El Nombre no puede ser nulo o estar vacio");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El Apellido no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo o estar vacio");
        }
        if (!email.contains(".com") || !email.contains("@")) {
            throw new MiException("El email debe contener @ y .com");
        }

        if (telefono.isEmpty() || telefono == null || telefono.length() < 10) {
            throw new MiException("El Telefono no puede estar vacio y debe tener 10 numeros incluyendo el codigo de area");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La Contraseña no puede estar vacia y debe tener mas de 5 digitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las Contraseñas ingresadas deben ser iguales");
        }

    }
}
