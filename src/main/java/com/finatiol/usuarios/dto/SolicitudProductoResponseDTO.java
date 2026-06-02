package com.finatiol.usuarios.dto;

import java.time.LocalDateTime;

public class SolicitudProductoResponseDTO {

    private Long id;

    private String username;

    private String producto;

    private String estado;

    private String comentario;

    private LocalDateTime fechaSolicitud;

    public SolicitudProductoResponseDTO(
            Long id,
            String username,
            String producto,
            String estado,
            String comentario,
            LocalDateTime fechaSolicitud) {

        this.id = id;
        this.username = username;
        this.producto = producto;
        this.estado = estado;
        this.comentario = comentario;
        this.fechaSolicitud = fechaSolicitud;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getProducto() {
        return producto;
    }

    public String getEstado() {
        return estado;
    }

    public String getComentario() {
        return comentario;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }
}
