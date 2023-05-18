package com.salesianos.triana.dam.clubDeportivo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.model.Socio;

public interface ReservaRepositorio extends JpaRepository <Reserva, Long>{

	@Query("select count(r) from Reserva r where r.socio = ?1")
	public int findNumReservasBySocio(Socio socio);
	
	@Query("SELECT r FROM Reserva r WHERE r.fecha_reserva BETWEEN :inicio AND :fin AND r.pista.id = :idPista")
	public List<Reserva> findByFechaReservaEntreYPista(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin, @Param("idPista") int idPista);
	
	@Query("SELECT r FROM Reserva r ORDER BY r.fecha_reserva DESC")
	public List<Reserva> findByFechaReservaDesc();
	
	//public List<Reserva> findByIdAsc();
}
