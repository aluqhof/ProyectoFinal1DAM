package com.salesianos.triana.dam.clubDeportivo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianos.triana.dam.clubDeportivo.model.Socio;

public interface SocioRepositorio extends JpaRepository <Socio, Integer>{

	Optional<Socio> findFirstByUsername(String username);
}
