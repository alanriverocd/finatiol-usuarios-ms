package com.finatiol.usuarios.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private String codigo;

    private String mensaje;

    private int status;

    private LocalDateTime fecha;

    private T data;

    public ApiResponse(
            String codigo,
            String mensaje,
            int status,
            T data) {

        this.codigo = codigo;
        this.mensaje = mensaje;
        this.status = status;
        this.fecha = LocalDateTime.now();
        this.data = data;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(
            String codigo) {

        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(
            String mensaje) {

        this.mensaje = mensaje;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(
            LocalDateTime fecha) {

        this.fecha = fecha;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
