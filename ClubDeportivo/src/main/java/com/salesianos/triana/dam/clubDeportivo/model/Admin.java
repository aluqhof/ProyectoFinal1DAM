package com.salesianos.triana.dam.clubDeportivo.model;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Admin extends Usuario{

	private static final long serialVersionUID = 1L;
	
	private String nombre, apellidos;

	public Admin(Long id, String username, String password, String nombre, String apellidos) {
		super(id, username, password);
		// TODO Auto-generated constructor stub
		this.nombre=nombre;
		this.apellidos=apellidos;
	}

	
}
