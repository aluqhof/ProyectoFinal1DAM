package com.salesianos.triana.dam.clubDeportivo.service;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianos.triana.dam.clubDeportivo.model.Pista;
import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.repository.ReservaRepositorio;

@Service
public class ReservaService extends BaseServiceImp<Reserva, Long, ReservaRepositorio> {
	
	@Autowired
	private ReservaRepositorio repositorio;
	
	public boolean isHoraDisponible(LocalTime horaReserva, LocalDate fechaReserva) {
	    for (Reserva reserva : repositorio.findAll()) {
	        if (reserva.getFecha_reserva().equals(fechaReserva) && reserva.getHora_reserva().equals(horaReserva)) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public boolean crearReserva(Pista pista, Socio socio, LocalDate fechaReserva, LocalTime horaReserva) {
		double div = 100;
		double precioAumentado = 0;
		if (!isHoraDisponible(horaReserva, fechaReserva)) {
			return false;
		}

		Reserva reserva = new Reserva();
		reserva.setPista(pista);
		reserva.setSocio(socio);
		reserva.setFecha_reserva(fechaReserva);
		reserva.setHora_reserva(horaReserva);

		pista.getReservas().add(reserva);
		reserva.setPista(pista);

		if (horaReserva.isAfter(pista.getHora_aumento_precio()) || horaReserva.equals(pista.getHora_aumento_precio())) {
			precioAumentado = pista.getPrecio() + pista.getAumento_precio() / div * pista.getAumento_precio();
			reserva.getPista().setPrecio(precioAumentado);
			// Realizar la lógica adicional con el precio aumentado
		}
		
		add(reserva);
		return true;
	}

	public int numeroReservasPorSocio(Socio socio) {
		return repositorio.findNumReservasBySocio(socio);
	}
}
