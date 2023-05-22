package com.salesianos.triana.dam.clubDeportivo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianos.triana.dam.clubDeportivo.model.Deporte;

public interface DeporteRepositorio extends JpaRepository<Deporte, Integer> {

	public Optional<Deporte> findFirstByOrderById();

}
