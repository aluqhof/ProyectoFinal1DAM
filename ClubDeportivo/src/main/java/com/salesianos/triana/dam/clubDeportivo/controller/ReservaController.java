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
	public String listarReservas(Model model, @RequestParam(name = "reservaId", required = false) Long reservaId) {

		model.addAttribute("reservas", service.findAll());
		model.addAttribute("deportes", deporteService.findAll());
	    model.addAttribute("pistas", pistaService.findAll());
	    model.addAttribute("socios", socioService.findAll());
	    model.addAttribute("reserva", new Reserva());
	    model.addAttribute("mostrarForm", false);
	    return "reservas";
	}
	
	 
	@GetMapping("/update/{id}")
	public String actualizarReserva(@PathVariable("id") Long id, Model model) {
	    model.addAttribute("reservas", service.findAll());
	    model.addAttribute("deportes", deporteService.findAll());
	    model.addAttribute("pistas", pistaService.findAll());
	    model.addAttribute("socios", socioService.findAll());
	    model.addAttribute("reserva", service.findById(id));
	    model.addAttribute("mostrarForm", true);
	    return "reservas";
	}
	
	@PostMapping("/editReserva")
	public String editReservaAdmin(@ModelAttribute("reserva") Reserva reserva) {
		service.edit(reserva);
		return "redirect:/reservas/";
	}
	
	/*@PostMapping("/deleteReserva")
	public String deleteReserva(@RequestParam("reservaId") Long reservaId) {
		if (reservaId != null) {
	        service.deleteById(reservaId);
	    }
	    return "redirect:/panel-admin/reservas";
	}*/


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
