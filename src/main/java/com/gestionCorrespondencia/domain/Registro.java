package com.gestionCorrespondencia.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;


@Data
@Entity
@Table(name="registro")
public class Registro implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idregistro;
    @NotEmpty
    private String numero;
    @NotEmpty
    private String numregistro;  
    @NotEmpty
    private String remitente;
    @NotEmpty
    private String cargoremitente;
    @NotEmpty
    private String instremitente;
    @NotEmpty
    private String destino;
    @NotEmpty
    private String referencia;
    
    private String contenido;
    @NotEmpty
    private String adjunto; //booleano
    @NotEmpty
    private String prioridad;
    //hora y fecha automatico?
    
}
