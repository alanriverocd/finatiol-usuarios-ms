package com.finatiol.usuarios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finatiol.usuarios.constants.SuccessCodes;
import com.finatiol.usuarios.constants.SuccessMessages;
import com.finatiol.usuarios.dto.ApiResponse;
import com.finatiol.usuarios.dto.ModuloRequestDTO;
import com.finatiol.usuarios.dto.ModuloResponseDTO;
import com.finatiol.usuarios.service.ModuloService;

@RestController
@RequestMapping("/modulos")
public class ModuloController {

    private final ModuloService moduloService;

    public ModuloController(ModuloService moduloService) {
        this.moduloService = moduloService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USUARIO_EDITAR')")
    public ApiResponse<ModuloResponseDTO> crearModulo(
            @RequestBody ModuloRequestDTO request) {

        return new ApiResponse<>(
                SuccessCodes.MODULO_CREADO,
                SuccessMessages.MODULO_CREADO,
                HttpStatus.CREATED.value(),
                moduloService.crearModulo(request)
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USUARIO_VER')")
    public ApiResponse<List<ModuloResponseDTO>> listarModulos() {

        return new ApiResponse<>(
                SuccessCodes.MODULOS_OBTENIDOS,
                SuccessMessages.MODULOS_OBTENIDOS,
                HttpStatus.OK.value(),
                moduloService.listarModulos()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIO_VER')")
    public ApiResponse<ModuloResponseDTO> obtenerModuloPorId(
            @PathVariable Long id) {

        return new ApiResponse<>(
                SuccessCodes.MODULO_OBTENIDO,
                SuccessMessages.MODULO_OBTENIDO,
                HttpStatus.OK.value(),
                moduloService.obtenerModuloPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIO_ELIMINAR')")
    public ApiResponse<String> eliminarModulo(
            @PathVariable Long id) {

        moduloService.eliminarModulo(id);

        return new ApiResponse<>(
                SuccessCodes.MODULO_ELIMINADO,
                SuccessMessages.MODULO_ELIMINADO,
                HttpStatus.OK.value(),
                SuccessMessages.MODULO_ELIMINADO
        );
    }
}
