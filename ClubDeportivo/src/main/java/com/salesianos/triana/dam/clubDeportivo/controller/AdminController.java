package com.salesianos.triana.dam.clubDeportivo.controller;

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
		Reserva rEditar = reservaService.findById(id).orElse(null);
		if (rEditar != null) {
			model.addAttribute("reserva", rEditar);
			return "formularioReservaAdmin";
		} else {
			return "redirect:/admin/reservas";
		}
	}

	@GetMapping("/reservas/borrar/{id}")
	public String borrarReserva(@PathVariable("id") long id) {
		Reserva aBorrar = reservaService.findById(id).orElse(null);
		if (aBorrar != null) {
			reservaService.delete(aBorrar);
		}
		return "redirect:/admin/reservas";
	}

	@PostMapping("/reservas/add/submit")

	public String addReservaSubmit(@ModelAttribute("reserva") Reserva reserva) {
		reservaService.add(reserva);
		return "redirect:/admin/reservas";
	}

	@PostMapping("/reservas/edit/submit")
	public String editReservaSubmit(@ModelAttribute("reserva") Reserva reserva) {
		reservaService.edit(reserva);
		return "redirect:/admin/reservas";
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
	public String addSocioSubmit(@ModelAttribute("socio") Socio socio) {
		String caracteresPermitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		int longitud = 8;
		Random random = new Random();
		String contraseña = "";
		for (int i = 0; i < longitud; i++) {
			int index = random.nextInt(caracteresPermitidos.length());
			contraseña += caracteresPermitidos.charAt(index);
		}
		emailService.sendEmail(socio.getUsername(), "¡Bienvenido al club!",
				"Hola " + socio.getNombre() + " a continuación tienes tus credenciales para entrar en nuestra web"
						+ "\n - Usuario: " + socio.getUsername() + "\n" + " - Contraseña: " + contraseña);
		String contraseñaEncriptada = passwordEncoder.encode(contraseña);
		socio.setPassword(contraseñaEncriptada);
		socioService.add(socio);
		return "redirect:/admin/socios";
	}
}
