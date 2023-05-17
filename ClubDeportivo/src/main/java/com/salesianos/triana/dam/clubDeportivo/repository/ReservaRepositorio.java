package com.salesianos.triana.dam.clubDeportivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.model.Socio;

public interface ReservaRepositorio extends JpaRepository <Reserva, Long>{

	@Query("select count(r) from Reserva r where r.socio = ?1")
	public int findNumReservasBySocio(Socio socio);
	
	@Query("SELECT SUM(r.pista.precio) FROM Reserva r WHERE MONTH(r.fecha_reserva) = MONTH(NOW()) AND YEAR(r.fecha_reserva) = YEAR(NOW())")
	public Double calcularFacturacionMensual();
	
	@Query("SELECT SUM(r.pista.precio) FROM Reserva r WHERE YEAR(r.fecha_reserva) = YEAR(CURRENT_DATE())")
	public Double calcularFacturacionAnual();
}
