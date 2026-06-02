package com.finatiol.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SolicitudEstadoUpdateRequestDTO {

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
            regexp = "PENDIENTE|APROBADA|RECHAZADA",
            message = "Estado inválido. Use PENDIENTE, APROBADA o RECHAZADA"
    )
    private String estado;

    private String comentario;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
