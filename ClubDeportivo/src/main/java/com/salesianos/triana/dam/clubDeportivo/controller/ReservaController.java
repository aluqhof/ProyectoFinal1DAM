package com.salesianos.triana.dam.clubDeportivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianos.triana.dam.clubDeportivo.service.ReservaService;

@Controller
public class ReservaController {

	@Autowired
	private ReservaService service;
	
	@GetMapping("/panel-admin/reservas")
	public String listarReservas (Model model) {
		
		model.addAttribute("reservas", service.findAll());
		
		return "html/panelAdmin";
	}
}
