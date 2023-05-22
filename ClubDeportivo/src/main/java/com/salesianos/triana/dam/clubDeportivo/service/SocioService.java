package com.salesianos.triana.dam.clubDeportivo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.repository.SocioRepositorio;

@Service
public class SocioService extends BaseServiceImp<Socio, Long, SocioRepositorio>{
	
	@Autowired
	private SocioRepositorio repositorio;

	public List<Socio> findByNombreYApellidos(String nombre, String apellidos) {
		return repositorio.findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(nombre, apellidos);
	}
	
	public List<Socio> orderByFechaAltaDesc(){
		return repositorio.findByFechaAltaDesc();
	}
	
	public List<Socio> orderByNombreAsc(){
		return repositorio.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
	}

	public List<Socio> orderByApellidosAsc(){
		return repositorio.findAll(Sort.by(Sort.Direction.ASC, "apellidos"));
	}
	
	public List<Socio> orderByIdAsc(){
		return repositorio.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}
	
	@Transactional
	public List<Socio> findTop5SociosByReservas() {
	    return repositorio.findTop5ByReservas();
	}
}
