package com.salesianos.triana.dam.clubDeportivo;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.salesianos.triana.dam.clubDeportivo.model.Admin;
import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.repository.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitData {
	
	private final UsuarioRepositorio repo;
	private final PasswordEncoder passwordEncoder;
	
	@PostConstruct
	public void init() {
		
		Socio socio = new Socio();
		socio.setUsername("user");
		socio.setPassword(passwordEncoder.encode("1234"));
		
		System.out.println(socio.getPassword());
		
		Admin admin = new Admin();
		admin.setUsername("admin");
		admin.setPassword(passwordEncoder.encode("admin"));
		
		repo.saveAll(List.of(socio, admin));
		
		System.out.println(admin.getPassword());
		
	}

}
