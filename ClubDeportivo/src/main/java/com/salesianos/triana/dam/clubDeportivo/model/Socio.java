package com.salesianos.triana.dam.clubDeportivo.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Socio extends Usuario{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String nombre;
	private String apellidos;
	private String telefono;
	private LocalDate fecha_alta;
	//private LocalDate fecha_baja;
	private double cuota;

	
	public Socio(Long id, String username, String password,String nombre, String apellidos, String telefono, LocalDate fecha_alta, double cuota,
			List<Reserva> reservas) {
		super(id, username, password);
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.fecha_alta = fecha_alta;
		this.cuota = cuota;
		this.reservas = reservas;
	}
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	//@Builder.Default
	@OneToMany(mappedBy="socio", cascade = CascadeType.ALL)
	private List<Reserva> reservas;

	
}
/*
// One-to-Many con la clase Pista
@ToString.Exclude
@EqualsAndHashCode.Exclude
@Builder.Default
@OneToMany(mappedBy = "socio")
private List<Pista> pistas = new ArrayList<>();*/
