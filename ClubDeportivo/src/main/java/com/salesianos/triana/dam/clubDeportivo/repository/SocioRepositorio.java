package com.salesianos.triana.dam.clubDeportivo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianos.triana.dam.clubDeportivo.model.Socio;

public interface SocioRepositorio extends JpaRepository <Socio, Long>{

public  List<Socio> findByNombreContainingIgnoreCase(String nombre);
	

	public  Page<Socio> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
