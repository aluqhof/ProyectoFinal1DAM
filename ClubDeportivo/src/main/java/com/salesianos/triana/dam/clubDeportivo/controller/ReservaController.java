package com.salesianos.triana.dam.clubDeportivo.controller;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianos.triana.dam.clubDeportivo.model.Deporte;
import com.salesianos.triana.dam.clubDeportivo.model.Pista;
import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.model.Usuario;
import com.salesianos.triana.dam.clubDeportivo.service.DeporteService;
import com.salesianos.triana.dam.clubDeportivo.service.EmailSenderService;
import com.salesianos.triana.dam.clubDeportivo.service.PistaService;
import com.salesianos.triana.dam.clubDeportivo.service.ReservaService;
import com.salesianos.triana.dam.clubDeportivo.service.SocioService;

@Controller
public class ReservaController {

	@Autowired
	private ReservaService reservaService;
	@Autowired
	private DeporteService deporteService;
	@Autowired
	private PistaService pistaService;
	@Autowired
	private EmailSenderService emailService;
	@Autowired
	private SocioService socioService;

	@GetMapping("/admin/reservas")
	public String showReservas(Model model) {

		model.addAttribute("reservas", reservaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("socios", socioService.findAll());
		model.addAttribute("reserva", new Reserva());
		return "reservas";
	}
	
	@GetMapping("/admin/reservas/add")
	public String addReserva(Model model) {
		model.addAttribute("reservas", reservaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("socios", socioService.findAll());
		Reserva reserva = new Reserva();
		model.addAttribute("reserva", reserva);
		return "formularioReservaAdmin";
	}

	@GetMapping("/admin/reservas/update/{id}")
	public String updateReserva(@PathVariable("id") Long id, Model model) {
		model.addAttribute("reservas", reservaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("socios", socioService.findAll());
		Optional <Reserva> rEditar = reservaService.findById(id);
		if (rEditar.isPresent() && LocalDateTime.of(rEditar.get().getFecha_reserva(), rEditar.get().getHora_reserva()).isAfter(LocalDateTime.now())) {
			model.addAttribute("reserva", rEditar.get());
			return "formularioReservaAdmin";
		} else {
			if(LocalDateTime.of(rEditar.get().getFecha_reserva(), rEditar.get().getHora_reserva()).isBefore(LocalDateTime.now())) {
				return "redirect:/admin/reservas/?error=true";
			}
			return "redirect:/admin/reservas";
		}
	}

	@GetMapping("/admin/reservas/borrar/{id}")
	public String borrarReserva(@PathVariable("id") long id) {
		Optional <Reserva> aBorrar = reservaService.findById(id);
		if (aBorrar.isPresent()) {
			reservaService.delete(aBorrar.get());
			System.out.println(aBorrar.get());
		}
		return "redirect:/admin/reservas";
	}

	@PostMapping("/admin/reservas/add/submit")
	public String addReservaSubmit(@ModelAttribute("reserva") Reserva reserva, Model model) {
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("socios", socioService.findAll());
		reservaService.calcularPrecio(reserva);
		if (!reservaService.isHoraDisponible(reserva.getHora_reserva(), reserva.getFecha_reserva(),
				reserva.getPista().getId())) {
			model.addAttribute("error", "La pista no esta disponible, por favor escoge otra opción.");
			return "formularioReservaAdmin";
		}
		reservaService.add(reserva);
		return "redirect:/admin/reservas";
	}

	@PostMapping("/admin/reservas/edit/submit")
	public String editReservaSubmit(@ModelAttribute("reserva") Reserva reserva, Model model) {
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("socios", socioService.findAll());
		reservaService.calcularPrecio(reserva);
		if (!reservaService.isHoraDisponible(reserva.getHora_reserva(), reserva.getFecha_reserva(),
				reserva.getPista().getId())) {
			model.addAttribute("error", "La pista no esta disponible, por favor escoge otra opción.");
			return "formularioReservaAdmin";
		}
		reservaService.edit(reserva);
		return "redirect:/admin/reservas";
	}

	@GetMapping("/admin/reservas/ordenar")
	public String ordenarReservasPorFecha(@RequestParam("criterio") String criterio, Model model) {
		List<Reserva> reservas;

		switch (criterio) {
		case "fecha_reserva":
			reservas = reservaService.orderByFechaDesc();
			break;
		case "id":
			reservas = reservaService.findAll();
			break;
		default:
			reservas = new ArrayList<>();
			break;
		}

		model.addAttribute("reservas", reservas);
		return "reservas";
	}

	@GetMapping("/admin/reservas/calendario")
	public String mostrarReservasCalendario(Model model, @RequestParam(defaultValue = "1") int idPista) {
		int numeroDias = 6;
		int numeroHoras = 15;
		List<Reserva> reservas = reservaService.findReservasEstaSemanaYPista(idPista);
		List<LocalTime> horas = new ArrayList<>();
		LocalTime horaInicial = LocalTime.of(7, 0);
		for (int i = 0; i < numeroHoras; i++) {
			horas.add(horaInicial.plusHours(i));
		}
		LocalDate fechaActual = LocalDate.now();
		List<LocalDate> dias = new ArrayList<>();
		for (int i = 0; i < numeroDias; i++) {
			LocalDate diaSemana = fechaActual.with(DayOfWeek.of(i + 1));
			dias.add(diaSemana);
		}
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("reservas", reservas);
		model.addAttribute("horas", horas);
		model.addAttribute("dias", dias);
		model.addAttribute("idPista", idPista);

		return "calendarioEstaSemana";
	}

	@GetMapping("/admin/reservas/calendario/semana2")
	public String mostrarReservasCalendarioSemana2(Model model, @RequestParam(defaultValue = "1") int idPista) {
		int numeroDias = 6;
		int numeroHoras = 15;
		List<Reserva> reservas = reservaService.findReservasProximaSemanaYPista(idPista);
		List<LocalTime> horas = new ArrayList<>();
		LocalTime horaInicial = LocalTime.of(7, 0);
		for (int i = 0; i < numeroHoras; i++) {
			horas.add(horaInicial.plusHours(i));
		}
		LocalDate hoy = LocalDate.now();
		LocalDate inicioProximaSemana = hoy.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
		List<LocalDate> dias = new ArrayList<>();
		for (int i = 0; i < numeroDias; i++) {
			LocalDate diaSemana = inicioProximaSemana.with(DayOfWeek.of(i + 1));
			dias.add(diaSemana);
		}

		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("reservas", reservas);
		model.addAttribute("horas", horas);
		model.addAttribute("dias", dias);
		model.addAttribute("idPista", idPista);

		return "calendarioProximaSemana";
	}

	@GetMapping("/disponibilidad")
	public String mostrarReservasCalendario(Model model, @RequestParam(defaultValue = "1") int idDeporte,
			@RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dia) {
		
			Deporte deporte = deporteService.findById(idDeporte).get();
			int numeroHoras = 15;
			LocalDate diaSiguiente = dia.plusDays(1);
			LocalDate diaAnterior = dia.minusDays(1);
			List<Reserva> reservas = reservaService.findReservasHoyYDeporte(idDeporte, dia);
			List<LocalTime> horas = new ArrayList<>();
			LocalTime horaInicial = LocalTime.of(7, 0);
			List<Pista> pistas = deporte.getPistas();
			int numeroPistas = pistas != null ? pistas.size() : 0;

			// Crear matriz bidimensional de pistas y horas
			boolean[][] pistasHoras = new boolean[numeroHoras][numeroPistas];

			// Marcar las pistas ocupadas en cada hora
			for (Reserva reserva : reservas) {
				int horaIndex = reserva.getHora_reserva().getHour() - horaInicial.getHour();
				int pistaIndex = reserva.getPista().getId() - 1;
				pistasHoras[horaIndex][pistaIndex] = true;
			}

			for (int i = 0; i < numeroHoras; i++) {
				horas.add(horaInicial.plusHours(i));
			}

			if (LocalDate.now().isAfter(diaAnterior)) {
				model.addAttribute("mostrarBotonAnterior", false);
			} else { // tiene que salir excepcion si lo ponen en el navegador
				model.addAttribute("mostrarBotonAnterior", true);
			}

			if (LocalDate.now().plusDays(7).isEqual(diaSiguiente) || LocalDate.now().plusDays(7).isBefore(diaSiguiente)) {
				model.addAttribute("mostrarBotonSiguiente", false);
			} else {
				model.addAttribute("mostrarBotonSiguiente", true);
			}

			model.addAttribute("deporte", deporte);
			model.addAttribute("pistas", pistaService.findAll());
			model.addAttribute("deportes", deporteService.findAll());
			model.addAttribute("reservas", reservas);
			model.addAttribute("horas", horas);
			model.addAttribute("pistas", deporte.getPistas());
			model.addAttribute("idDeporte", idDeporte);
			model.addAttribute("hoy", LocalDate.now());
			model.addAttribute("horaActual", LocalTime.now());
			model.addAttribute("dia", dia);
			model.addAttribute("pistasHoras", pistasHoras);
			model.addAttribute("diaSiguiente", diaSiguiente);
			model.addAttribute("diaAnterior", diaAnterior);

			return "disponibilidad";

	}

	@GetMapping("/reserva-pista")
	public String verFormularioReserva(@RequestParam("idDeporte") int idDeporte,
			@RequestParam("dia") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dia,
			@RequestParam("hora") @DateTimeFormat(pattern = "HH:mm") LocalTime hora,
			@RequestParam("numPista") int numPista, Model model,
			@AuthenticationPrincipal Usuario u) {

		LocalDateTime ahora = LocalDateTime.now();

		if (u.puedeReservar() && (((dia.isEqual(LocalDate.now()) && hora.isAfter(ahora.toLocalTime())) || dia.isAfter(LocalDate.now()))
		        && dia.isBefore(LocalDate.now().plusDays(7)) && hora.isAfter(LocalTime.of(6, 0))
		        && hora.isBefore(LocalTime.of(22, 0)) && dia.getDayOfWeek().getValue() != 7)) {
			Reserva reserva = new Reserva();
			Pista pista = pistaService.findByNumeroAndDeporte(numPista, deporteService.findById(idDeporte).get());
			System.out.println(LocalDateTime.now());
			reserva.setPista(pista);
			reserva.setFecha_reserva(dia);
			reserva.setHora_reserva(hora);
			reserva.setPrecio_reserva(pista.getPrecio());
			reservaService.calcularPrecio(reserva);
			model.addAttribute("pistas", pistaService.findAll());
			model.addAttribute("deportes", deporteService.findAll());
			model.addAttribute("reserva", reserva);
			model.addAttribute("reservaExitosa", false);
			return "formularioReserva";
		}

		return "redirect:/disponibilidad";
	}

	@PostMapping("/reserva-pista/nuevo")
	public String agregarReservaUser(@ModelAttribute("reserva") Reserva reserva, Model model,
			@AuthenticationPrincipal Socio s) {
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		String precioFormateado;
		System.out.println(reserva);
		if (reservaService.isHoraDisponible(reserva.getHora_reserva(), reserva.getFecha_reserva(),
				reserva.getPista().getId())) {
			reserva.setSocio(s);
			reservaService.calcularPrecio(reserva);
			System.out.println(reserva);
			precioFormateado=decimalFormat.format(reserva.getPrecio_reserva()) + " €";
			reservaService.add(reserva);
			model.addAttribute("reservaExitosa", true);
			emailService.sendEmail(s.getUsername(), "Reserva confirmada",
					"Hola " + s.getNombre() + ",\nSu reserva para el día " + reserva.getFecha_reserva() + " " + "a las "
							+ reserva.getHora_reserva() + " ha sido confirmada.\n"
									+ "Precio "+precioFormateado);

		} else {
			model.addAttribute("error",
					"La pista no esta disponible a esa hora, intenta escoger otra pista o buscar otra hora");
			return "formularioReserva";
		}
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("nombre", s.getNombre());
		return "confirmacionReserva";
	}

}
