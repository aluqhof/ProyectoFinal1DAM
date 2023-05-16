package com.salesianos.triana.dam.clubDeportivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.service.DeporteService;
import com.salesianos.triana.dam.clubDeportivo.service.EmailSenderService;
import com.salesianos.triana.dam.clubDeportivo.service.PistaService;
import com.salesianos.triana.dam.clubDeportivo.service.ReservaService;

@Controller
public class ReservaController {

	@Autowired
	private ReservaService service;
	@Autowired
	private DeporteService deporteService;
	@Autowired
	private PistaService pistaService;
	@Autowired
	private EmailSenderService emailService;

	@GetMapping("/reserva-pista")
	public String verFormularioReserva(Model model) {
		Reserva reserva = new Reserva();
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("reserva", reserva);
		model.addAttribute("reservaExitosa", false);
		return "formularioReserva";
	}

	@PostMapping("/reserva-pista/nuevo")
	public String agregarReservaUser(@ModelAttribute("reserva") Reserva reserva, Model model,
			@AuthenticationPrincipal Socio s) {
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		reserva.setSocio(s);
		service.add(reserva);
		model.addAttribute("reservaExitosa", true);
		try {
			emailService.sendEmail(s.getUsername(), "Reserva confirmada",
					"Hola " + s.getNombre() + " su reserva para el día " + reserva.getFecha_reserva() + " " + "a las "
							+ reserva.getHora_reserva() + " ha sido confirmada.");

		} catch (Exception e) {
			return "error";
		}
		return "confirmacionReserva";
	}

}
