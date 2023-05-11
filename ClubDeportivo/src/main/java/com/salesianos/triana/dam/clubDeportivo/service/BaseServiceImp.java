package com.salesianos.triana.dam.clubDeportivo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseServiceImp<T, ID, R extends JpaRepository<T, ID>> 
		implements BaseService<T, ID>{

	@Autowired
	protected R repository;
	
	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<T> findById(ID id) {
		return repository.findById(id);
	}

	@Override
	public T add(T t) {
		return repository.save(t);
	}
	
	@Override
	public T save(T t) {
		return repository.save(t);
	}

	@Override
	public T edit(T t) {
		return repository.save(t);
	}

	@Override
	public void delete(T t) {
		repository.delete(t);
		
	}

	@Override
	public void deleteById(ID id) {
		repository.deleteById(id);
	}


	
}
