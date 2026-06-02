package com.finatiol.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.finatiol.usuarios.dto.AuditoriaRequestDTO;

@FeignClient(name = "${auditoria.client.name:finatiol-autenticacion-ms}")
public interface AuditoriaClient {

    @PostMapping("${auditoria.client.register-path:/auditoria}")
    void registrar(@RequestBody AuditoriaRequestDTO request);
}
