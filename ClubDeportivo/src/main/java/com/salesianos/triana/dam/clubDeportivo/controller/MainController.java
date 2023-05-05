package com.salesianos.triana.dam.clubDeportivo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianos.triana.dam.clubDeportivo.model.Deporte;
import com.salesianos.triana.dam.clubDeportivo.model.Pista;
import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.model.Socio;

@Controller
public class MainController {

	@GetMapping({"/", "/inicio"})
	public String showHomePage(Model model) {
		return "index";
	}
	
	@GetMapping("/panel-admin")
	public String showAdminPanel(Model model) {
		model.addAttribute("socio", new Socio());
		model.addAttribute("reserva", new Reserva());
		model.addAttribute("pista", new Pista());
		model.addAttribute("deporte", new Deporte());
		return "panelAdmin";
	}
}
