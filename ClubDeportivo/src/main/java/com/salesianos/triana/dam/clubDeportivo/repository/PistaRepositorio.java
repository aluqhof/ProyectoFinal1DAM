package com.salesianos.triana.dam.clubDeportivo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesianos.triana.dam.clubDeportivo.model.Deporte;
import com.salesianos.triana.dam.clubDeportivo.model.Pista;

public interface PistaRepositorio extends JpaRepository<Pista, Integer>{

	@Query("SELECT COUNT(p) FROM Pista p WHERE p.deporte.id = ?1")
	public int countPistasByDeporte(int idDeporte);
	
	public Pista findByNumeroAndDeporte(int numero, Deporte deporte);
	
	public List<Pista> findByDeporteId(int IdDeporte);
}
