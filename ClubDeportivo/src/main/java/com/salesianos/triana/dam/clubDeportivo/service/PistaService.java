package com.salesianos.triana.dam.clubDeportivo.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianos.triana.dam.clubDeportivo.model.Pista;
import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.repository.PistaRepositorio;

@Service
public class PistaService extends BaseServiceImp<Pista, Integer, PistaRepositorio> {

	@Autowired
	private ReservaService reservaService;
	@Autowired
	private PistaRepositorio repositorio;

	public boolean crearReserva(Pista pista, Socio socio, LocalDate fechaReserva, LocalTime horaReserva) {
		double div = 100;
		double precioAumentado = 0;
		if (!reservaService.isHoraDisponible(horaReserva, fechaReserva)) {
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
		
		reservaService.add(reserva);
		return true;
	}

	public int numeroDePistasPorDeporte(int id) {
		return repositorio.countPistasByDeporte(id);
	}

}
