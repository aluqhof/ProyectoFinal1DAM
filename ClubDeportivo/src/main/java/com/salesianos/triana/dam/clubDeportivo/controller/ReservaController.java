package com.salesianos.triana.dam.clubDeportivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.service.DeporteService;
import com.salesianos.triana.dam.clubDeportivo.service.PistaService;
import com.salesianos.triana.dam.clubDeportivo.service.ReservaService;
import com.salesianos.triana.dam.clubDeportivo.service.SocioService;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

	@Autowired
	private ReservaService service;
	@Autowired
	private DeporteService deporteService;
	@Autowired
	private PistaService pistaService;
	@Autowired
	private SocioService socioService;

	@GetMapping("/")
	public String showReservas(Model model, @RequestParam(name = "reservaId", required = false) Long reservaId) {

		model.addAttribute("reservas", service.findAll());
		model.addAttribute("deportes", deporteService.findAll());
	    model.addAttribute("pistas", pistaService.findAll());
	    model.addAttribute("socios", socioService.findAll());
	    model.addAttribute("reserva", new Reserva(0L)); // establecer el id en cero
	    return "reservas";
	}
	
	@GetMapping("/add")
	public String addReserva(Model model) {
		model.addAttribute("reservas", service.findAll());
		model.addAttribute("deportes", deporteService.findAll());
	    model.addAttribute("pistas", pistaService.findAll());
	    model.addAttribute("socios", socioService.findAll());
	    model.addAttribute("reserva", new Reserva());
	    return "formularioReservaAdmin";
	}
	 
	@GetMapping("/update/{id}")
	public String updateReserva(@PathVariable("id") Long id, Model model) {
		model.addAttribute("reservas", service.findAll());
		model.addAttribute("deportes", deporteService.findAll());
	    model.addAttribute("pistas", pistaService.findAll());
	    model.addAttribute("socios", socioService.findAll());
	    Reserva rEditar = service.findById(id).orElse(null);
	    if(rEditar !=null) {
	    	model.addAttribute("reserva", rEditar);
	    	return "formularioReservaAdmin";
	    }else {
	    	return "redirect:/reservas/";
	    }
	}
	
	@PostMapping("/add/submit")
	public String procesarFormulario(@ModelAttribute("reserva") Reserva r) {
		service.add(r);
		return "redirect:http:/reservas/";
	}
	
	@PostMapping("/edit/submit")
	public String editReservaAdmin(@ModelAttribute("reserva") Reserva reserva) {
		service.edit(reserva);
		return "redirect:/reservas/";
	}

	@GetMapping("/reserva-pista")
	public String verFormularioReserva(Model model) {
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("reserva", new Reserva());
		return "formularioReserva";
	}
	
	@PostMapping("/reserva-pista/nuevo")
	public String agregarReservaUser(@ModelAttribute("reserva") Reserva reserva, Model model) {
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		service.add(reserva);
		return "redirect:/reserva-pista";
	}


}
