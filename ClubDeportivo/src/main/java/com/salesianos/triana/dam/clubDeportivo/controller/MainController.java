package com.salesianos.triana.dam.clubDeportivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianos.triana.dam.clubDeportivo.service.DeporteService;
import com.salesianos.triana.dam.clubDeportivo.service.PistaService;
import com.salesianos.triana.dam.clubDeportivo.service.ReservaService;
import com.salesianos.triana.dam.clubDeportivo.service.SocioService;

@Controller
public class MainController {

	@Autowired
	private ReservaService reservaService;
	@Autowired
	private DeporteService deporteService;
	@Autowired
	private PistaService pistaService;
	@Autowired
	private SocioService socioService;
	
	@GetMapping({"/", "/inicio"})
	public String showHomePage(Model model) {
		model.addAttribute("numReservas", reservaService.findAll().size());
		model.addAttribute("numDeportes", deporteService.findAll().size());
		model.addAttribute("numPistas", pistaService.findAll().size());
		model.addAttribute("numSocios", socioService.findAll().size());
		return "index";
	}
	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "paginaLogin";
	}
	
	@GetMapping("/errorAcceso")
	public String errorPermiso() {
		return "errorAcceso";
	}
}
