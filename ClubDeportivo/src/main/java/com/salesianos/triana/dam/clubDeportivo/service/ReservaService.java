package com.salesianos.triana.dam.clubDeportivo.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.repository.ReservaRepositorio;

@Service
public class ReservaService extends BaseServiceImp<Reserva, Long, ReservaRepositorio> {
	
	@Autowired
	private ReservaRepositorio repositorio;
	
	public boolean isHoraDisponible(LocalTime horaReserva, LocalDate fechaReserva, int id) {
	    for (Reserva reserva : repositorio.findAll()) {
	        if (reserva.getPista().getId()==id && reserva.getFecha_reserva().equals(fechaReserva) && reserva.getHora_reserva().equals(horaReserva)) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public void calcularPrecio(Reserva reserva) {
		double div = 100;
		double precioAumentado = 0;


		if (reserva.getHora_reserva().isAfter(reserva.getPista().getHora_aumento_precio()) || 
				reserva.getHora_reserva().equals(reserva.getPista().getHora_aumento_precio())) {
			precioAumentado = reserva.getPista().getPrecio() + reserva.getPista().getAumento_precio()
					/ div * reserva.getPista().getAumento_precio();
			reserva.setPrecio_reserva(precioAumentado);
		};
	}

	public int numeroReservasPorSocio(Socio socio) {
		return repositorio.findNumReservasBySocio(socio);
	}
	
	/*public List<Reserva> findByNombre(String nombre){
		return repositorio.findByNombreSocioContainingIgnoreCase(nombre);
	}*/
	
	public Page<Reserva> findAllPageable(Pageable pageable) {
		return repositorio.findAll(pageable);
	}

}
