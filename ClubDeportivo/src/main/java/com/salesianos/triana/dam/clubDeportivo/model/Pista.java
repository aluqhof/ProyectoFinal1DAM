package com.salesianos.triana.dam.clubDeportivo.model;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pista {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int numero;
	private double precio;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey (name="fk_deporte_pista"))
	private Deporte deporte;
	
	public void addToDeporte(Deporte deporte) {
		this.deporte=deporte;
		deporte.getPistas().add(this);
	}
	
	public void removeFromDeporte(Deporte deporte) {
		deporte.getPistas().remove(this);
		this.deporte=null;
	}
}
