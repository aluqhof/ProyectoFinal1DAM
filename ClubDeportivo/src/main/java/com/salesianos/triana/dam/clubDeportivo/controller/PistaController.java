package com.salesianos.triana.dam.clubDeportivo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianos.triana.dam.clubDeportivo.model.Pista;
import com.salesianos.triana.dam.clubDeportivo.service.DeporteService;
import com.salesianos.triana.dam.clubDeportivo.service.PistaService;

@Controller
public class PistaController {
	
	@Autowired
	private DeporteService deporteService;
	@Autowired
	private PistaService pistaService;

	@GetMapping("/admin/pistas")
	public String listarPistas(Model model) {

		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("pista", new Pista());

		return "pistas";
	}

	@GetMapping("/admin/pistas/add")
	public String agregarPista(Model model) {
		model.addAttribute("pistas", pistaService.findAll());
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("pista", new Pista());
		return "formularioPistaAdmin";
	}

	@PostMapping("/admin/pistas/add/submit")
	public String addPistaSubmit(@ModelAttribute("pista") Pista pista) {
		int numPista = pistaService.numeroDePistasPorDeporte(pista.getDeporte().getId());
		pista.setNumero(numPista + 1);
		pistaService.add(pista);
		return "redirect:/admin/pistas";
	}

	@GetMapping("/admin/pistas/update/{id}")
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

	@GetMapping("/admin/pistas/borrar/{id}")
	public String borrarPista(@PathVariable("id") int id) {
		Optional <Pista> aBorrar = pistaService.findById(id);
		if (aBorrar.isPresent()) {
			pistaService.delete(aBorrar.get());
		}
		return "redirect:/admin/pistas";
	}

	@PostMapping("/admin/pistas/edit/submit")
	public String editPistaSubmit(@ModelAttribute("pista") Pista pista) {
		pistaService.edit(pista);
		return "redirect:/admin/pistas";
	}

}
