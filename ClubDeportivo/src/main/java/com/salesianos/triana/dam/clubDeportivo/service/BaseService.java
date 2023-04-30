package com.salesianos.triana.dam.clubDeportivo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface BaseService <T, ID>{

	List<T> findAll();
	Optional<T> findById(ID id);
	T save (T t);
	T edit(T t);
	void delete(T t);
	void deleteById(ID id);
}
