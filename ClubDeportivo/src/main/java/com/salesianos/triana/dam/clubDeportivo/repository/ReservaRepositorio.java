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
	
	@Query("SELECT SUM(r.precio_reserva) FROM Reserva r WHERE MONTH(r.fecha_reserva) = MONTH(NOW()) AND YEAR(r.fecha_reserva) = YEAR(NOW())")
	public Double calcularFacturacionMensual();
	
	@Query("SELECT SUM(r.precio_reserva) FROM Reserva r WHERE YEAR(r.fecha_reserva) = YEAR(CURRENT_DATE())")
	public Double calcularFacturacionAnual();
	
	@Query("SELECT r FROM Reserva r WHERE r.fecha_reserva BETWEEN :inicio AND :fin AND r.pista.id = :idPista")
	public List<Reserva> findByFechaReservaEntreYPista(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin, @Param("idPista") int idPista);
	
	@Query("SELECT r FROM Reserva r WHERE r.fecha_reserva = :dia AND r.pista.deporte.id = :idDeporte")
	public List<Reserva> findByFechaReservaHoyYDeporte(@Param("dia") LocalDate dia, @Param("idDeporte") int idDeporte);
	
	@Query("SELECT r FROM Reserva r ORDER BY r.fecha_reserva DESC")
	public List<Reserva> findByFechaReservaDesc();
	
	@Query("SELECT r FROM Reserva r WHERE r.fecha_reserva = CURRENT_DATE")
    public List<Reserva> findAllReservasDelDiaActual();

}
