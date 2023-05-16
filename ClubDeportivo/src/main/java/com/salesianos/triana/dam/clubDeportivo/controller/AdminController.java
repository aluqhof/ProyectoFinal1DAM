package com.salesianos.triana.dam.clubDeportivo.controller;

import java.util.List;
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

import com.salesianos.triana.dam.clubDeportivo.formbeans.SearchBean;
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
	
	@PostMapping("/reservas/search")
	public String searchReserva(@ModelAttribute("searchForm") SearchBean searchBean, Model model){
	    List<Reserva> reservas = reservaService.findByNombre(searchBean.getSearch());
	    model.addAttribute("reservas", reservas);
	    model.addAttribute("deportes", deporteService.findAll());
	    model.addAttribute("pistas", pistaService.findAll());
	    model.addAttribute("socios", socioService.findAll());
	    return "reservas";
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
	public String addReservaSubmit(@ModelAttribute("reserva") Reserva reserva, Model model) {
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("socios", socioService.findAll());
		reservaService.calcularPrecio(reserva);
		if (!reservaService.isHoraDisponible(reserva.getHora_reserva(), reserva.getFecha_reserva(), reserva.getPista().getId())) {
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
		if (!reservaService.isHoraDisponible(reserva.getHora_reserva(), reserva.getFecha_reserva(), reserva.getPista().getId())) {
			model.addAttribute("error", "La pista no esta disponible, por favor escoge otra opción.");
			return "formularioReservaAdmin";
		}
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

	@GetMapping("/socios/update/{id}")
	public String editarSocio(@PathVariable("id") Long id, Model model) {
		model.addAttribute("socios", socioService.findAll());
		Socio sEditar = socioService.findById(id).orElse(null);
		if (sEditar != null) {
			model.addAttribute("socio", sEditar);
			return "formularioSocioAdmin";
		} else {
			return "redirect:/admin/socios";
		}
	}

	@GetMapping("/socios/borrar/{id}")
	public String borrarSocio(@PathVariable("id") long id) {
		Socio sBorrar = socioService.findById(id).orElse(null);
		if (sBorrar != null) {
			if (reservaService.numeroReservasPorSocio(sBorrar) == 0) {
				socioService.delete(sBorrar);
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
		Pista pEditar = pistaService.findById(id).orElse(null);
		if (pEditar != null) {
			model.addAttribute("pista", pEditar);
			return "formularioPistaAdmin";
		} else {
			return "redirect:/admin/pistas";
		}
	}

	@GetMapping("/pistas/borrar/{id}")
	public String borrarPista(@PathVariable("id") int id) {
		Pista aBorrar = pistaService.findById(id).orElse(null);
		if (aBorrar != null) {
			pistaService.delete(aBorrar);
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
		Deporte dEditar = deporteService.findById(id).orElse(null);
		if (dEditar != null) {
			model.addAttribute("deporte", dEditar);
			return "formularioDeporteAdmin";
		} else {
			return "redirect:/admin/deportes";
		}
	}

	@GetMapping("/deportes/borrar/{id}")
	public String borrarDepor(@PathVariable("id") int id) {
		Deporte aBorrar = deporteService.findById(id).orElse(null);
		if (aBorrar != null) {
			if (pistaService.numeroDePistasPorDeporte(aBorrar.getId()) == 0) {
				deporteService.delete(aBorrar);
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
}
