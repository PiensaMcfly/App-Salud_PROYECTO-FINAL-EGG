

package com.grupo5.AppSalud.repository;

import com.grupo5.AppSalud.entities.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * Gisela Rantucho
 */
@Repository
public interface ImagenRepository extends JpaRepository<Imagen, String>{

}
