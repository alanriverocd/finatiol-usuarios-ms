package com.finatiol.usuarios.dto;

public class PermisoResponseDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private ModuloResponseDTO modulo;

    public PermisoResponseDTO() {
    }

    public PermisoResponseDTO(
            Long id,
            String nombre,
            String descripcion,
            ModuloResponseDTO modulo) {

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.modulo = modulo;
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

    public ModuloResponseDTO getModulo() {
        return modulo;
    }

    public void setModulo(ModuloResponseDTO modulo) {
        this.modulo = modulo;
    }
}
