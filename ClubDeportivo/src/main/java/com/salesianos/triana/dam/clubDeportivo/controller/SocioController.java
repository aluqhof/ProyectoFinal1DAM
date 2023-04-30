package com.salesianos.triana.dam.clubDeportivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianos.triana.dam.clubDeportivo.service.SocioService;

@Controller
public class SocioController {

	@Autowired
	private SocioService service;

	
	@GetMapping("#socios")
	public String listarVarios (Model model) {
		
		model.addAttribute("socios", service.findAll());
		
		return "panelAdmin";
	}
}
