/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo5.AppSalud.repository;

import com.grupo5.AppSalud.entities.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chris
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.eMail = :email")
    public Usuario buscarPorEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.eMail = :email")
    Long countByEMail(@Param("email") String email);

    default boolean existsByEmail(String email) {
        return countByEMail(email) > 0;
    }

    @Query("SELECT u FROM Usuario u WHERE u.dni = :dni")
    public Usuario buscarPorDni(@Param("dni") String dni);

    @Override
    public Optional<Usuario> findById(String dni); 

}
