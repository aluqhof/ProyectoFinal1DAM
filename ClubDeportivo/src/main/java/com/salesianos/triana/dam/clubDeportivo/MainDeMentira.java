package com.salesianos.triana.dam.clubDeportivo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salesianos.triana.dam.clubDeportivo.model.Deporte;
import com.salesianos.triana.dam.clubDeportivo.model.Pista;
import com.salesianos.triana.dam.clubDeportivo.repository.DeporteRepositorio;
import com.salesianos.triana.dam.clubDeportivo.repository.PistaRepositorio;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MainDeMentira {

	@Autowired
	private final DeporteRepositorio deporteRepositorio;
	private final PistaRepositorio pistaRepositorio;

	@PostConstruct
	public void ejecutar() {
		Deporte d1 = new Deporte();
		d1.setNombre("Tenis");
		Deporte d2 = new Deporte();
		d2.setNombre("Pádel");

		deporteRepositorio.save(d1);
		deporteRepositorio.save(d2);

		Pista p1 = new Pista();
		p1.setNumero(1);
		p1.setPrecio(2.30);
		p1.setDeporte(d1);

		Pista p2 = new Pista();
		p2.setNumero(2);
		p2.setPrecio(2.30);
		p2.setDeporte(d1);

		Pista p3 = new Pista();
		p3.setNumero(1);
		p3.setPrecio(3.30);
		p3.setDeporte(d2);

		Pista p4 = new Pista();
		p4.setNumero(2);
		p4.setPrecio(4.30);
		p4.setDeporte(d2);

		pistaRepositorio.save(p1);

		pistaRepositorio.save(p2);

		pistaRepositorio.save(p3);

		pistaRepositorio.save(p4);

		p1.addToDeporte(d1);
		p2.addToDeporte(d1);
		p3.addToDeporte(d2);
		p4.addToDeporte(d2);

		System.out.println(d1);
		for (Pista pista : d1.getPistas()) {
			System.out.println(pista);
		}

		System.out.println(d2);
		for (Pista pista : d2.getPistas()) {
			System.out.println(pista);
		}
	}
}
