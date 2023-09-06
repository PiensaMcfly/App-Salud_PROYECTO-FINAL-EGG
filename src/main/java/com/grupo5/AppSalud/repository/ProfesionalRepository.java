package com.grupo5.AppSalud.repository;

import com.grupo5.AppSalud.entities.Profesional;
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
public interface ProfesionalRepository extends JpaRepository<Profesional, String> {

    @Query("SELECT p FROM Profesional p WHERE p.especialidad = :especialidad")
    public Profesional buscarPorEspecialidad(@Param("especialidad") String especialidad);

    @Query("SELECT p FROM Profesional p WHERE p.matricula = :matricula")
    public Profesional buscarPorMatricula(@Param("matricula") String matricula);

    @Query("SELECT p FROM Profesional p WHERE p.eMail = :email")
    public Profesional buscarPorEmail(@Param("email") String email);

    @Query("SELECT COUNT(p) FROM Profesional p WHERE p.eMail = :email")
    Long countByEMail(@Param("email") String email);

    default boolean existsByEmail(String email) {
        return countByEMail(email) > 0;
    }

    @Override
    public Optional<Profesional> findById(String matricula);

//    @Query ("SELECT u FROM Usuario u WHERE u.email =:email") buscarPorMatricula
//    public Usuario buscaPorEmail(@Param("email")String email);
}
