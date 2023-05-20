package com.salesianos.triana.dam.clubDeportivo.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pista {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int numero;
	private double precio;
	
	private double aumento_precio;
	
	private LocalTime hora_aumento_precio;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey (name="fk_deporte_pista"))
	private Deporte deporte;

	
    @ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
    @OneToMany(mappedBy = "pista", cascade = CascadeType.ALL, orphanRemoval = true)//fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Reserva> reservas = new ArrayList<>();
    
}
