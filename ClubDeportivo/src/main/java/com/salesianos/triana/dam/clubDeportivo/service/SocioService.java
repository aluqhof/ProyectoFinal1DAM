package com.salesianos.triana.dam.clubDeportivo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.repository.SocioRepositorio;

@Service
public class SocioService extends BaseServiceImp<Socio, Long, SocioRepositorio>{
	
	@Autowired
	private SocioRepositorio repositorio;

	public Page<Socio> findAllPageable(Pageable pageable) {
		return repositorio.findAll(pageable);
	}

	public  Page<Socio> findByNombreContainingIgnoreCasePageable(String nombre, Pageable pageable)
	{
		return repositorio.findByNombreContainingIgnoreCase(nombre, pageable);
	}
}
