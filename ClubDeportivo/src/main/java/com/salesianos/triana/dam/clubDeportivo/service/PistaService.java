package com.salesianos.triana.dam.clubDeportivo.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianos.triana.dam.clubDeportivo.model.Pista;
import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.repository.PistaRepositorio;
import com.salesianos.triana.dam.clubDeportivo.repository.ReservaRepositorio;

@Service
public class PistaService extends BaseServiceImp<Pista, Integer, PistaRepositorio>{

	@Autowired
    private ReservaRepositorio reservaRepositorio;
	
	public void crearReserva(Pista pista, Socio socio, LocalDate fechaReserva, LocalTime horaReserva) {
	    if (!pista.isHoraDisponible(horaReserva, fechaReserva)) {
	        throw new IllegalArgumentException("La hora ya está reservada");
	    }

	    Reserva reserva = new Reserva();
	    reserva.setPista(pista);
	    reserva.setSocio(socio);
	    reserva.setFecha_reserva(fechaReserva);
	    reserva.setHora_reserva(horaReserva);
	    reservaRepositorio.save(reserva);
	    
	    pista.getReservas().add(reserva);
	    reserva.setPista(pista);
	}

}
