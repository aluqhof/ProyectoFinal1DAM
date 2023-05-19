package com.salesianos.triana.dam.clubDeportivo.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.service.DeporteService;
import com.salesianos.triana.dam.clubDeportivo.service.EmailSenderService;
import com.salesianos.triana.dam.clubDeportivo.service.PistaService;
import com.salesianos.triana.dam.clubDeportivo.service.ReservaService;

@Controller
public class ReservaController {

	@Autowired
	private ReservaService service;
	@Autowired
	private DeporteService deporteService;
	@Autowired
	private PistaService pistaService;
	@Autowired
	private EmailSenderService emailService;

	@GetMapping("/disponibilidad")
	public String mostrarReservasCalendario(Model model, @RequestParam(defaultValue = "1") int idDeporte) { //meter tambien dia
		int numeroPistas = deporteService.findById(idDeporte).get().getPistas().size();
		int numeroHoras = 15;
		List<Integer> pistas = new ArrayList<>();
		List<Reserva> reservas = service.findReservasHoyYDeporte(idDeporte);
		LocalDate hoy=LocalDate.now();
		List<LocalTime> horas = new ArrayList<>();
		LocalTime horaInicial = LocalTime.of(7, 0);
		
		for (int i = 0; i < numeroHoras; i++) {
			horas.add(horaInicial.plusHours(i));
		}
		
		for (int i = 0; i < numeroPistas; i++) {
			int pista = i + 1;
			pistas.add(pista);
		}
		
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("reservas", reservas);
		model.addAttribute("horas", horas);
		model.addAttribute("pistas", pistas);
		model.addAttribute("idDeporte", idDeporte);
		model.addAttribute("dia", hoy);
		return "disponibilidad";
	}
	
	@GetMapping("/reserva-pista")
	public String verFormularioReserva(Model model) {
		Reserva reserva = new Reserva();
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("reserva", reserva);
		//model.addAttribute("reservaExitosa", false);
		return "formularioReserva";
	}

	@PostMapping("/reserva-pista/nuevo")
	public String agregarReservaUser(@ModelAttribute("reserva") Reserva reserva, Model model,
			@AuthenticationPrincipal Socio s) {
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("nombre", s.getNombre());
		if (service.isHoraDisponible(reserva.getHora_reserva(), reserva.getFecha_reserva(),
				reserva.getPista().getId())) {
			reserva.setSocio(s);
			service.calcularPrecio(reserva);
			service.add(reserva);
			model.addAttribute("reservaExitosa", true);
			emailService.sendEmail(s.getUsername(), "Reserva confirmada",
					"Hola " + s.getNombre() + " su reserva para el día " + reserva.getFecha_reserva() + " " + "a las "
							+ reserva.getHora_reserva() + " ha sido confirmada.");

		} else {
			model.addAttribute("error",
					"La pista no esta disponible a esa hora, intenta escoger otra pista o buscar otra hora");
			return "formularioReserva";
		}
		return "confirmacionReserva";
	}

}
