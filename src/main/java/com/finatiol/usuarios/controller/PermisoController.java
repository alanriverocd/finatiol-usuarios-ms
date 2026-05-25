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

import com.finatiol.common.constants.usuarios.SuccessCodes;
import com.finatiol.common.constants.usuarios.SuccessMessages;
import com.finatiol.usuarios.dto.ApiResponse;
import com.finatiol.usuarios.dto.PermisoRequestDTO;
import com.finatiol.usuarios.dto.PermisoResponseDTO;
import com.finatiol.usuarios.service.PermisoService;

@RestController
@RequestMapping("/permisos")
public class PermisoController {

    private final PermisoService permisoService;

    public PermisoController(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USUARIO_EDITAR')")
    public ApiResponse<PermisoResponseDTO> crearPermiso(
            @RequestBody PermisoRequestDTO request) {

        return new ApiResponse<>(
                SuccessCodes.PERMISO_CREADO,
                SuccessMessages.PERMISO_CREADO,
                HttpStatus.CREATED.value(),
                permisoService.crearPermiso(request)
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USUARIO_VER')")
    public ApiResponse<List<PermisoResponseDTO>> listarPermisos() {

        return new ApiResponse<>(
                SuccessCodes.PERMISOS_OBTENIDOS,
                SuccessMessages.PERMISOS_OBTENIDOS,
                HttpStatus.OK.value(),
                permisoService.listarPermisos()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIO_VER')")
    public ApiResponse<PermisoResponseDTO> obtenerPermisoPorId(
            @PathVariable Long id) {

        return new ApiResponse<>(
                SuccessCodes.PERMISO_OBTENIDO,
                SuccessMessages.PERMISO_OBTENIDO,
                HttpStatus.OK.value(),
                permisoService.obtenerPermisoPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIO_ELIMINAR')")
    public ApiResponse<String> eliminarPermiso(
            @PathVariable Long id) {

        permisoService.eliminarPermiso(id);

        return new ApiResponse<>(
                SuccessCodes.PERMISO_ELIMINADO,
                SuccessMessages.PERMISO_ELIMINADO,
                HttpStatus.OK.value(),
                SuccessMessages.PERMISO_ELIMINADO
        );
    }
}
