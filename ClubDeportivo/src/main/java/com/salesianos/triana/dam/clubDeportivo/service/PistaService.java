package com.salesianos.triana.dam.clubDeportivo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianos.triana.dam.clubDeportivo.model.Deporte;
import com.salesianos.triana.dam.clubDeportivo.model.Pista;
import com.salesianos.triana.dam.clubDeportivo.repository.PistaRepositorio;

@Service
public class PistaService extends BaseServiceImp<Pista, Integer, PistaRepositorio> {

	@Autowired
	private PistaRepositorio repositorio;

	public int numeroDePistasPorDeporte(int id) {
		return repositorio.countPistasByDeporte(id);
	}
	
	public Pista findByNumeroAndDeporte(int numero, Deporte deporte) {
        return repositorio.findByNumeroAndDeporte(numero, deporte);
    }

	public List<Pista> findPistasByDeporteId(int IdDeporte) {
        return repositorio.findByDeporteId(IdDeporte);
    }
	
}
