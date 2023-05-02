package com.salesianos.triana.dam.clubDeportivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.service.SocioService;

@Controller
public class SocioController {

	@Autowired
	private SocioService service;

	
	@GetMapping("/panel-admin")
	public String listarSocios (Model model) {
		
		model.addAttribute("socios", service.findAll());
		
		return "html/panelAdmin";
	}
	
	@PostMapping("/panel-admin/add-socio")
	public String addSocio(@ModelAttribute("formAddSocio") Socio socio, BindingResult result) {
		if (result.hasErrors()) {
		    return "html/panelAdmin";
		  }
		  // procesar datos del formulario y redirigir a otra página
		  return "html/panelAdmin";
	}
}
