package com.finatiol.usuarios.dto;

import java.util.Set;

public class RolRequestDTO {

    private String nombre;

    private String descripcion;

    private Set<Long> permisosIds;

    public RolRequestDTO() {
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

    public Set<Long> getPermisosIds() {
        return permisosIds;
    }

    public void setPermisosIds(Set<Long> permisosIds) {
        this.permisosIds = permisosIds;
    }
}
