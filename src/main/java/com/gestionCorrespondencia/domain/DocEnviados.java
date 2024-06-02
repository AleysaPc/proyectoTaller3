package com.gestionCorrespondencia.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="documentos")
public class DocEnviados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iddocumentos;

    private String nombre;
    private String tipo;

    @Lob
    private byte[] contenido;

    // Constructor, getters y setters

    public DocEnviados() {
    }

    public DocEnviados (String nombre, String tipo, byte[] contenido) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.contenido = contenido;
    }

}

