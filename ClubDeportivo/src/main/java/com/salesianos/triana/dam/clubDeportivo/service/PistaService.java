package com.salesianos.triana.dam.clubDeportivo.service;

import java.time.LocalDateTime;

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
	
	/* Esto lo tiene que tener bien la rama 1
	public void crearReserva(Pista pista, Socio socio, LocalDateTime fechaReservaCliente) {
	    if (!pista.isHoraDisponible(fechaReservaCliente)) {
	        throw new IllegalArgumentException("La hora ya está reservada");
	    }
	    Reserva reserva = new Reserva();
	    reserva.setPista(pista);
	    reserva.setSocio(socio);
	    reserva.setFechaReserva(fechaReservaCliente);
	    reservaRepositorio.save(reserva);
	    
	    pista.getReservas().add(reserva); // Agregar la reserva a la lista de reservas de la pista
	    reserva.setPista(pista); // Establecer la referencia a la pista en la reserva
	}*/
}
