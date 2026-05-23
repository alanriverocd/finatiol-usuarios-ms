package com.finatiol.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.finatiol.usuarios.dto.EmailRequestDTO;

@FeignClient(name = "finatiol-notificaciones-ms")
public interface NotificacionClient {

    @PostMapping("/notificaciones/email")
    void enviarEmail(
            @RequestBody
            EmailRequestDTO request);
}
