package com.salesianos.triana.dam.clubDeportivo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping({"/", "/inicio"})
	public String showHomePage(Model model) {
		return "index";
	}
	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "paginaLogin";
	}
}
