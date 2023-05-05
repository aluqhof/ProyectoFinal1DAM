package com.salesianos.triana.dam.clubDeportivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianos.triana.dam.clubDeportivo.model.Pista;
import com.salesianos.triana.dam.clubDeportivo.service.PistaService;

@Controller
public class PistaController {

	@Autowired
	private PistaService service;

	@GetMapping("/panel-admin/pistas")
	public String listarPistas(Model model) {

		model.addAttribute("pistas", service.findAll());
		model.addAttribute("pista", new Pista());

		return "panelAdmin";
	}
}
