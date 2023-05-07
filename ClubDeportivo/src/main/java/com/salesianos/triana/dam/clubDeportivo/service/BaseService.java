package com.salesianos.triana.dam.clubDeportivo.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface BaseService <T, ID>{

	List<T> findAll();
	T findById(ID id);
	T add (T t);
	T save (T t);
	T edit(T t);
	void delete(T t);
	void deleteById(ID id);
}
