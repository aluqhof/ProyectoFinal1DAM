package com.salesianos.triana.dam.clubDeportivo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesianos.triana.dam.clubDeportivo.model.Socio;

public interface SocioRepositorio extends JpaRepository<Socio, Long> {

	List<Socio> findByNombreContainingIgnoreCaseAndApellidosContainingIgnoreCase(String nombre, String apellidos);

	@Query("SELECT s FROM Socio s ORDER BY s.fecha_alta DESC")
	public List<Socio> findByFechaAltaDesc();

//public List<Socio> findByNombreOrderByNombreDesc();

//public List<Socio> findByApellidosOrderByApellidosDesc();

}
