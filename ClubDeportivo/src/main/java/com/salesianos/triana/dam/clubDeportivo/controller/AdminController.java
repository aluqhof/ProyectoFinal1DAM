package com.salesianos.triana.dam.clubDeportivo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.service.DeporteService;
import com.salesianos.triana.dam.clubDeportivo.service.PistaService;
import com.salesianos.triana.dam.clubDeportivo.service.ReservaService;
import com.salesianos.triana.dam.clubDeportivo.service.SocioService;

@RequestMapping("/admin")
@Controller
public class AdminController {

	@Autowired
	private ReservaService reservaService;
	@Autowired
	private DeporteService deporteService;
	@Autowired
	private PistaService pistaService;
	@Autowired
	private SocioService socioService;


	
	
	@GetMapping("/general")
	public String mostrarVistaGeneral(Model model) {
		List<Socio> top5Socios = socioService.findTop5SociosByReservas();
		List<Reserva> reservasHoy = reservaService.findReservasHoy();
		model.addAttribute("reservasHoy", reservasHoy);	    
		model.addAttribute("top5Socios", top5Socios);
		model.addAttribute("reservas", reservaService.findAll());
		model.addAttribute("socios", socioService.findAll());
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("socios", deporteService.findAll());
		model.addAttribute("facturacionMensual", reservaService.calcularFacturacionMensual());
		model.addAttribute("facturacionAnual", reservaService.calcularFacturacionAnual());
		return "general";
	}
}
