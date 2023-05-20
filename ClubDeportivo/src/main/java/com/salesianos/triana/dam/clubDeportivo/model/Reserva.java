package com.salesianos.triana.dam.clubDeportivo.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reserva {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_reserva;
    
    private LocalTime hora_reserva;
    
    private double precio_reserva;
    
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey (name="fk_socio_reserva"))
    private Socio socio;
    
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey (name="fk_pista_reserva"))
    private Pista pista;
    
    public Reserva (Long id) {
    	this.id=id;
    }
    
}
