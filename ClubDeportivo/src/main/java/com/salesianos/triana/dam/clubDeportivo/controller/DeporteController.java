package com.salesianos.triana.dam.clubDeportivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianos.triana.dam.clubDeportivo.model.Deporte;
import com.salesianos.triana.dam.clubDeportivo.service.DeporteService;

@Controller
public class DeporteController {

	@Autowired
	private DeporteService service;
	
	@GetMapping("/panel-admin/deportes")
	public String listarDeportes(Model model) {

		model.addAttribute("deportes", service.findAll());
		model.addAttribute("deporte", new Deporte());

		return "panelAdmin";
	}
}
