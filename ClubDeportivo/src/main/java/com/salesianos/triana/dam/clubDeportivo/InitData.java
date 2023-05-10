package com.salesianos.triana.dam.clubDeportivo;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.salesianos.triana.dam.clubDeportivo.model.Socio;
import com.salesianos.triana.dam.clubDeportivo.repository.SocioRepositorio;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitData {
	
	private final SocioRepositorio repo;
	private final PasswordEncoder passwordEncoder;
	
	@PostConstruct
	public void init() {
		
		Socio usuario = Socio.builder()
				.nombre("Pepe")
				.apellidos("Vázquez")
				.username("user")
				.password(passwordEncoder.encode("1234"))
				.fecha_alta(LocalDate.of(2020, 3, 10))
				.cuota(50)
				.telefono("668868")
				.admin(false)
				.build();
		
		Socio admin = Socio.builder()
				.nombre("Pepe")
				.apellidos("Flores")
				.username("admin")
				.password(passwordEncoder.encode("admin"))
				.fecha_alta(LocalDate.of(2020, 3, 10))
				.cuota(0)
				.telefono("668868")
				.admin(true)
				.build();
		
		repo.saveAll(List.of(usuario, admin));
		
	}

}
