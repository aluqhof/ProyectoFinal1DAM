package com.salesianos.triana.dam.clubDeportivo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianos.triana.dam.clubDeportivo.model.Deporte;
import com.salesianos.triana.dam.clubDeportivo.service.DeporteService;
import com.salesianos.triana.dam.clubDeportivo.service.PistaService;

@Controller
public class DeporteController {
	
	@Autowired
	private DeporteService deporteService;
	@Autowired
	private PistaService pistaService;
	
	@GetMapping("/admin/deportes")
	public String listarDeportes(Model model) {

		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("deporte", new Deporte());

		return "deportes";
	}

	@GetMapping("/admin/deportes/add")
	public String agregarDeporte(Model model) {
		model.addAttribute("deportes", deporteService.findAll());
		model.addAttribute("deporte", new Deporte());
		return "formularioDeporteAdmin";
	}

	@PostMapping("/admin/deportes/add/submit")
	public String addDeporteSubmit(@ModelAttribute("deporte") Deporte deporte) {
		deporteService.add(deporte);
		return "redirect:/admin/deportes";
	}

	@GetMapping("/admin/deportes/update/{id}")
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

	@GetMapping("/admin/deportes/borrar/{id}")
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

	@PostMapping("/admin/deportes/edit/submit")
	public String editDeporteSubmit(@ModelAttribute("deporte") Deporte deporte) {
		deporteService.edit(deporte);
		return "redirect:/admin/deportes";
	}
}
