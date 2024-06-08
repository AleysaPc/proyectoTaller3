package com.gestionCorrespondencia.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name="tasks")
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtasks;
    private String titulo;
    
     @Temporal(TemporalType.DATE)
    private LocalDate fechavencimiento; // Cambiado a LocalDate
    
    private LocalTime hora;
    private String asignado;
    private String estado;
}
