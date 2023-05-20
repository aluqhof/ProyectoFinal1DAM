package com.salesianos.triana.dam.clubDeportivo.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.service.EmailSenderService;
import com.salesianos.triana.dam.clubDeportivo.service.ReservaService;
import com.salesianos.triana.dam.clubDeportivo.service.SocioService;

@Controller
public class SocioController {
	
	@Autowired
	private SocioService socioService;
	@Autowired
	private ReservaService reservaService;
	@Autowired
	private EmailSenderService emailService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/admin/socios")
	public String listarSocios(Model model) {

		model.addAttribute("socios", socioService.findAll());
		model.addAttribute("socio", new Socio());

		return "socios";
	}

	@GetMapping("/admin/socios/add")
	public String agregarSocio(Model model) {
		model.addAttribute("socios", socioService.findAll());
		model.addAttribute("socio", new Socio());

		return "formularioSocioAdmin";
	}

	@PostMapping("/admin/socios/add/submit")
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

	@GetMapping("/admin/socios/busqueda")
	public String buscarSocioPorNombreYApellidos(@RequestParam("nombre") String busqueda, Model model) {
		List<Socio> socios = socioService.findByNombreYApellidos(busqueda, busqueda);
		model.addAttribute("socios", socios);
		return "socios";
	}

	@GetMapping("/admin/socios/ordenar")
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

	@GetMapping("/admin/socios/update/{id}")
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

	@GetMapping("/admin/socios/borrar/{id}")
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

	@PostMapping("/admin/socio/edit/submit")
	public String editSocioSubmit(@ModelAttribute("socio") Socio socio) {
		socioService.edit(socio);
		return "redirect:/admin/reservas";
	}
}
