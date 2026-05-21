package com.finatiol.usuarios.dto;

import java.util.List;

public class RolResponseDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private List<PermisoResponseDTO> permisos;

    public RolResponseDTO() {
    }

    public RolResponseDTO(
            Long id,
            String nombre,
            String descripcion,
            List<PermisoResponseDTO> permisos) {

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.permisos = permisos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<PermisoResponseDTO> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<PermisoResponseDTO> permisos) {
        this.permisos = permisos;
    }
}
