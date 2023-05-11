package com.salesianos.triana.dam.clubDeportivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.service.SocioService;

@Controller
@RequestMapping("/socios")
public class SocioController {

	@Autowired
	private SocioService service;

	@GetMapping("/")
	public String listarSocios(Model model) {

		model.addAttribute("socios", service.findAll());
		model.addAttribute("socio", new Socio());

		return "socios";
	}

	@PostMapping("/addSocio")
	public String agregarSocio(@ModelAttribute("socio") Socio socio) {
	    service.save(socio);
	    return "redirect:/";
	}
	
	@PostMapping("/editSocio")
	public String editarSocio(@ModelAttribute("socio") Socio socio) {
	    service.save(socio);
	    return "redirect:/";
	}
	
	@GetMapping("/me")
	public String me() {

		Socio u = (Socio) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		System.out.println(u.toString());

		return "perfil";
	}

	@GetMapping("/me2")
	public String me2(@AuthenticationPrincipal Socio u) {

		System.out.println(u.toString());

		return "perfil";
	}
}
