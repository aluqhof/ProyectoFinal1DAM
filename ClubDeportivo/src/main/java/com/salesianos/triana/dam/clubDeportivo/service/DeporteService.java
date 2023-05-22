package com.salesianos.triana.dam.clubDeportivo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianos.triana.dam.clubDeportivo.model.Deporte;
import com.salesianos.triana.dam.clubDeportivo.repository.DeporteRepositorio;

@Service
public class DeporteService extends BaseServiceImp<Deporte, Integer, DeporteRepositorio>{
	
	@Autowired
	private DeporteRepositorio repositorio;

	public Optional<Deporte> findFirstDeporte(){
		return repositorio.findFirstByOrderById();
	}
}
