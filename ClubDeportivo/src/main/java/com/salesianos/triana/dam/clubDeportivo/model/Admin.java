package com.salesianos.triana.dam.clubDeportivo.model;

import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Admin extends Usuario{

	private static final long serialVersionUID = 1L;

	public Admin(Long id, String username, String password) {
		super(id, username, password);
		// TODO Auto-generated constructor stub
	}

	
}
