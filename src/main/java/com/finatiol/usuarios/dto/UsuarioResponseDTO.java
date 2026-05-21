package com.finatiol.usuarios.dto;

import java.util.List;

public class UsuarioResponseDTO {

    private Long id;

    private String nombre;

    private String username;

    private String email;

    private Boolean activo;

    private List<String> roles;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(
            Long id,
            String nombre,
            String username,
            String email,
            Boolean activo,
            List<String> roles) {

        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.activo = activo;
        this.roles = roles;
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

    public void setNombre(
            String nombre) {

        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(
            String username) {

        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(
            String email) {

        this.email = email;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(
            Boolean activo) {

        this.activo = activo;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(
            List<String> roles) {

        this.roles = roles;
    }
}
