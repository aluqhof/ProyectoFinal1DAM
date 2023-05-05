package com.salesianos.triana.dam.clubDeportivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.service.DeporteService;
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

	@GetMapping("panel-admin/reservas")
	public String listarReservas(Model model) {

		model.addAttribute("reservas", service.findAll());
		
		return "panelAdmin";
	}
	@PostMapping("/addReservas")
	public String agregarReservaAdmin(@ModelAttribute("reserva") Reserva reserva) {
		service.save(reserva);
		return "redirect:/panel-admin/reservas";
	}

	@GetMapping("/reserva-pista")
	public String verFormularioReserva(Model model) {
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("reserva", new Reserva());
		return "formularioReserva";
	}
	
	//Algo está mal
	@PostMapping("/reserva-pista/nuevo")
	public String agregarReservaUser(@ModelAttribute("reserva") Reserva reserva) {
		service.save(reserva);
		return "index";
	}


}
