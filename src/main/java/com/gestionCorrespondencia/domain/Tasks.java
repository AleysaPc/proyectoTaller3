package com.gestionCorrespondencia.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
   @DateTimeFormat(pattern = "dd/MM/yyyy") // Especifica el formato de fecha
    private LocalDate fechavencimiento; // Cambiado a LocalDate
    private LocalTime hora;
    private String asignado;
    private String estado;
}
