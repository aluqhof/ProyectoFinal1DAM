package com.salesianos.triana.dam.clubDeportivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.service.ReservaService;
import com.salesianos.triana.dam.clubDeportivo.service.SocioService;

@Controller
public class ReservaController {

	@Autowired
	private ReservaService service;
	@Autowired
	private SocioService serviceSocio;

	@GetMapping("/reservas")
	public String listarReservas(Model model) {

		model.addAttribute("reservas", service.findAll());
		model.addAttribute("reserva", new Reserva());
		model.addAttribute("socios", serviceSocio.findAll());

		return "panelAdmin";
	}

	@PostMapping("/addReservas")
	public String agregarReserva(@ModelAttribute("reserva") Reserva reserva) {
		service.save(reserva);
		return "redirect:/reservas";
	}
}
