package com.salesianos.triana.dam.clubDeportivo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.repository.SocioRepositorio;

@Service
public class SocioService extends BaseServiceImp<Socio, Long, SocioRepositorio>{
	
	@Autowired
	private SocioRepositorio repositorio;

	public List<Socio> findByNombre(String nombre) {
		return repositorio.findByNombreContainingIgnoreCase(nombre);
	}
	
	public List<Socio> orderByFechaDesc(){
		return repositorio.findByFechaAltaDesc();
	}
	
	public List<Socio> orderByNombreDesc(){
		return repositorio.findByApellidosOrderByApellidosDesc();
	}

	public List<Socio> orderByApellidosDesc(){
		return repositorio.findByApellidosOrderByApellidosDesc();
	}
}
