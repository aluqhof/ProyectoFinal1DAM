package com.salesianos.triana.dam.clubDeportivo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.service.DeporteService;
import com.salesianos.triana.dam.clubDeportivo.service.PistaService;
import com.salesianos.triana.dam.clubDeportivo.service.ReservaService;
import com.salesianos.triana.dam.clubDeportivo.service.SocioService;

@RequestMapping("/admin")
@Controller
public class AdminController {

	@Autowired
	private ReservaService service;
	@Autowired
	private DeporteService deporteService;
	@Autowired
	private PistaService pistaService;
	@Autowired
	private SocioService socioService;
	
	@GetMapping("/reservas")
	public String showReservas(Model model) {

		model.addAttribute("reservas", service.findAll());
		model.addAttribute("deportes", deporteService.findAll());
	    model.addAttribute("pistas", pistaService.findAll());
	    model.addAttribute("socios", socioService.findAll());
	    model.addAttribute("reserva", new Reserva()); 
	    return "reservas";
	}
	
	@GetMapping("/reservas/add")
	public String addReserva(Model model) {
		model.addAttribute("reservas", service.findAll());
		model.addAttribute("deportes", deporteService.findAll());
	    model.addAttribute("pistas", pistaService.findAll());
	    model.addAttribute("socios", socioService.findAll());
	    model.addAttribute("reserva", new Reserva());
	    return "formularioReservaAdmin";
	}
	 
	@GetMapping("/reservas/update/{id}")
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
	    	return "redirect:/admin/reservas/";
	    }
	}
	
	@PostMapping("/reservas/add/submit")
	public String procesarFormulario(@ModelAttribute("reserva") Reserva reserva) {
		service.add(reserva);
		return "redirect:http:/admin/reservas/";
	}
	
	@PostMapping("/reservas/edit/submit")
	public String editReservaAdmin(@ModelAttribute("reserva") Reserva reserva) {
		service.edit(reserva);
		return "redirect:/admin/reservas/";
	}
}
