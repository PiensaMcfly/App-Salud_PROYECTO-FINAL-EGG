

package com.grupo5.AppSalud.repository;

import com.grupo5.AppSalud.entities.HistoriaClinica;
import com.grupo5.AppSalud.entities.Profesional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * Gisela Rantucho
 */
//creo repo con querys de busqueda por profesional y por usuario y metodo optional - gise 21:40
@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, String>{
       
    @Query("SELECT hc FROM HistoriaClinica hc WHERE hc.usuario = :usuario")
    public HistoriaClinica buscarPorUsuario(@Param("usuario") String usuario);
    
    public Optional<HistoriaClinica> findById(String idhc);

}
