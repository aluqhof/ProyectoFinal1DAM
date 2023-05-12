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
import com.salesianos.triana.dam.clubDeportivo.service.PistaService;
import com.salesianos.triana.dam.clubDeportivo.service.ReservaService;
import com.salesianos.triana.dam.clubDeportivo.service.SocioService;

@Controller
public class ReservaController {

	@Autowired
	private ReservaService service;
	@Autowired
	private DeporteService deporteService;
	@Autowired
	private PistaService pistaService;
	@Autowired
	private SocioService socioService;

	@GetMapping("/reserva-pista")
	public String verFormularioReserva(Model model) {
	    Reserva reserva = new Reserva();
	    model.addAttribute("pistas", pistaService.findAll());
	    model.addAttribute("deportes", deporteService.findAll());
	    model.addAttribute("reserva", reserva);
	    return "formularioReserva";
	}
	
	@PostMapping("/reserva-pista/nuevo")
	public String agregarReservaUser(@ModelAttribute("reserva") Reserva reserva, Model model, @AuthenticationPrincipal Socio s) {
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		reserva.setSocio(s);
		service.add(reserva);
		return "redirect:/reserva-pista";
	}


}
