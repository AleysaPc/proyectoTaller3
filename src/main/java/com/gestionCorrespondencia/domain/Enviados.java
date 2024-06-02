package com.gestionCorrespondencia.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "documentos")
public class Enviados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iddocumento;
    private LocalDate fecha;
    private String numcite;
    private String remitente;
    private String destinatario;
    private String institucion;
    private String referencia;
    private String estado;
    private String nota;

    @Lob
    private byte[] contenido;

    // Constructor, getters y setters
    public Enviados() {
    }

    public Enviados(LocalDate fecha, String numcite, String remitente,
            String destinatario, String institucion, String referencia, String estado, String nota, byte[] contenido) {
        this.fecha = fecha;
        this.numcite = numcite;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.institucion = institucion;
        this.referencia = referencia;
        this.estado = estado;
        this.nota = nota;
        this.contenido = contenido;
    }

}
