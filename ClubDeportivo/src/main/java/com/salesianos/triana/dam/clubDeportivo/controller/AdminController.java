package com.salesianos.triana.dam.clubDeportivo.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianos.triana.dam.clubDeportivo.model.Deporte;
import com.salesianos.triana.dam.clubDeportivo.model.Pista;
import com.salesianos.triana.dam.clubDeportivo.model.Reserva;
import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.service.DeporteService;
import com.salesianos.triana.dam.clubDeportivo.service.EmailSenderService;
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
	@Autowired
	private EmailSenderService emailService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/reservas")
	public String showReservas(Model model) {

		model.addAttribute("reservas", reservaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("socios", socioService.findAll());
		model.addAttribute("reserva", new Reserva());
		return "reservas";
	}

	@GetMapping("/reservas/add")
	public String addReserva(Model model) {
		model.addAttribute("reservas", reservaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("socios", socioService.findAll());
		Reserva reserva = new Reserva();
		model.addAttribute("reserva", reserva);
		return "formularioReservaAdmin";
	}

	@GetMapping("/reservas/update/{id}")
	public String updateReserva(@PathVariable("id") Long id, Model model) {
		model.addAttribute("reservas", reservaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("socios", socioService.findAll());
		Optional <Reserva> rEditar = reservaService.findById(id);
		if (rEditar.isPresent()) {
			model.addAttribute("reserva", rEditar.get());
			return "formularioReservaAdmin";
		} else {
			return "redirect:/admin/reservas";
		}
	}

	@GetMapping("/reservas/borrar/{id}")
	public String borrarReserva(@PathVariable("id") long id) {
		Optional <Reserva> aBorrar = reservaService.findById(id);
		if (aBorrar.isPresent()) {
			reservaService.delete(aBorrar.get());
		}
		return "redirect:/admin/reservas";
	}

	@PostMapping("/reservas/add/submit")
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

	@PostMapping("/reservas/edit/submit")
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

	@GetMapping("/reservas/ordenar")
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

	@GetMapping("/reservas/calendario")
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

	@GetMapping("/reservas/calendario/semana2")
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

	@GetMapping("/socios")
	public String listarSocios(Model model) {

		model.addAttribute("socios", socioService.findAll());
		model.addAttribute("socio", new Socio());

		return "socios";
	}

	@GetMapping("/socios/add")
	public String agregarSocio(Model model) {
		model.addAttribute("socios", socioService.findAll());
		model.addAttribute("socio", new Socio());

		return "formularioSocioAdmin";
	}

	@PostMapping("/socios/add/submit")
	public String addSocioSubmit(@ModelAttribute("socio") Socio socio, Model model) {
		String caracteresPermitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		int longitud = 8;
		Random random = new Random();
		String contraseña = "";
		for (int i = 0; i < longitud; i++) {
			int index = random.nextInt(caracteresPermitidos.length());
			contraseña += caracteresPermitidos.charAt(index);
		}
		for (Socio s : socioService.findAll()) {
			if (s.getUsername().equals(socio.getUsername())) {
				model.addAttribute("error", "El nombre de usuario ya existe, por favor elige otro.");
				return "formularioSocioAdmin";
			}
		}
		emailService.sendEmail(socio.getUsername(), "¡Bienvenido al club!",
				"Hola " + socio.getNombre() + " a continuación tienes tus credenciales para entrar en nuestra web"
						+ "\n - Usuario: " + socio.getUsername() + "\n" + " - Contraseña: " + contraseña);
		String contraseñaEncriptada = passwordEncoder.encode(contraseña);
		socio.setPassword(contraseñaEncriptada);
		socioService.add(socio);
		return "redirect:/admin/socios";
	}

	@GetMapping("/socios/busqueda")
	public String buscarSocioPorNombreYApellidos(@RequestParam("nombre") String busqueda, Model model) {
		List<Socio> socios = socioService.findByNombreYApellidos(busqueda, busqueda);
		model.addAttribute("socios", socios);
		return "socios";
	}

	@GetMapping("/socios/ordenar")
	public String ordenarSocios(@RequestParam("criterio") String criterio, Model model) {
		List<Socio> socios;

		switch (criterio) {
		case "nombre":
			socios = socioService.orderByNombreAsc();
			break;
		case "apellidos":
			socios = socioService.orderByApellidosAsc();
			break;
		case "fecha_alta":
			socios = socioService.orderByFechaAltaDesc();
			break;
		case "id":
			socios = socioService.findAll(); //No funciona
		default:
			socios = new ArrayList<>();
			break;
		}

		model.addAttribute("socios", socios);
		return "socios";
	}

	@GetMapping("/socios/update/{id}")
	public String editarSocio(@PathVariable("id") Long id, Model model) {
		model.addAttribute("socios", socioService.findAll());
		Optional <Socio> sEditar = socioService.findById(id);
		if (sEditar.isPresent()) {
			model.addAttribute("socio", sEditar.get());
			return "formularioSocioAdmin";
		} else {
			return "redirect:/admin/socios";
		}
	}

	@GetMapping("/socios/borrar/{id}")
	public String borrarSocio(@PathVariable("id") long id) {
		Optional <Socio> sBorrar = socioService.findById(id);
		if (sBorrar.isPresent()) {
			if (reservaService.numeroReservasPorSocio(sBorrar.get()) == 0) {
				socioService.delete(sBorrar.get());
			} else {
				return "redirect:/admin/socios/?error=true";
			}
		}
		return "redirect:/admin/socios";
	}

	@PostMapping("/socio/edit/submit")
	public String editSocioSubmit(@ModelAttribute("socio") Socio socio) {
		socioService.edit(socio);
		return "redirect:/admin/reservas";
	}

	@GetMapping("/pistas")
	public String listarPistas(Model model) {

		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("pista", new Pista());

		return "pistas";
	}

	@GetMapping("/pistas/add")
	public String agregarPista(Model model) {
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("pista", new Pista());
		return "formularioPistaAdmin";
	}

	@PostMapping("/pistas/add/submit")
	public String addPistaSubmit(@ModelAttribute("pista") Pista pista) {
		int numPista = pistaService.numeroDePistasPorDeporte(pista.getDeporte().getId());
		pista.setNumero(numPista + 1);
		pistaService.add(pista);
		return "redirect:/admin/pistas";
	}

	@GetMapping("/pistas/update/{id}")
	public String updatePista(@PathVariable("id") int id, Model model) {
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		Optional <Pista> pEditar = pistaService.findById(id);
		if (pEditar.isPresent()) {
			model.addAttribute("pista", pEditar.get());
			return "formularioPistaAdmin";
		} else {
			return "redirect:/admin/pistas";
		}
	}

	@GetMapping("/pistas/borrar/{id}")
	public String borrarPista(@PathVariable("id") int id) {
		Optional <Pista> aBorrar = pistaService.findById(id);
		if (aBorrar.isPresent()) {
			pistaService.delete(aBorrar.get());
		}
		return "redirect:/admin/pistas";
	}

	@PostMapping("/pistas/edit/submit")
	public String editPistaSubmit(@ModelAttribute("pista") Pista pista) {
		pistaService.edit(pista);
		return "redirect:/admin/pistas";
	}

	@GetMapping("/deportes")
	public String listarDeportes(Model model) {

		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("deporte", new Deporte());

		return "deportes";
	}

	@GetMapping("/deportes/add")
	public String agregarDeporte(Model model) {
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("deporte", new Deporte());
		return "formularioDeporteAdmin";
	}

	@PostMapping("/deportes/add/submit")
	public String addDeporteSubmit(@ModelAttribute("deporte") Deporte deporte) {
		deporteService.add(deporte);
		return "redirect:/admin/deportes";
	}

	@GetMapping("/deportes/update/{id}")
	public String updateDeporte(@PathVariable("id") int id, Model model) {
		model.addAttribute("deportes", deporteService.findAll());
		Optional <Deporte> dEditar = deporteService.findById(id);
		if (dEditar.isPresent()) {
			model.addAttribute("deporte", dEditar.get());
			return "formularioDeporteAdmin";
		} else {
			return "redirect:/admin/deportes";
		}
	}

	@GetMapping("/deportes/borrar/{id}")
	public String borrarDepor(@PathVariable("id") int id) {
		Optional <Deporte> aBorrar = deporteService.findById(id);
		if (aBorrar.isPresent()) {
			if (pistaService.numeroDePistasPorDeporte(aBorrar.get().getId()) == 0) {
				deporteService.delete(aBorrar.get());
			} else {
				return "redirect:/admin/deportes/?error=true";
			}
		}
		return "redirect:/admin/deportes";
	}

	@PostMapping("/deportes/edit/submit")
	public String editDeporteSubmit(@ModelAttribute("deporte") Deporte deporte) {
		deporteService.edit(deporte);
		return "redirect:/admin/deportes";
	}
	
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
