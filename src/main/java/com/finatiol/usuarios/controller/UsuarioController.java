package com.finatiol.usuarios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finatiol.common.constants.usuarios.SuccessCodes;
import com.finatiol.common.constants.usuarios.SuccessMessages;
import com.finatiol.usuarios.dto.ApiResponse;
import com.finatiol.usuarios.dto.UsuarioAuthDTO;
import com.finatiol.usuarios.dto.UsuarioRequestDTO;
import com.finatiol.usuarios.dto.UsuarioResponseDTO;
import com.finatiol.usuarios.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService
            usuarioService;

    public UsuarioController(
            UsuarioService usuarioService) {

        this.usuarioService = usuarioService;
    }

    @GetMapping("/perfil")
    @PreAuthorize("hasAuthority('VER_PERFIL')")
    public ApiResponse<String> perfil(
            Authentication authentication) {

        return new ApiResponse<>(
                SuccessCodes.PERFIL_OBTENIDO,
                SuccessMessages.PERFIL_OBTENIDO,
                HttpStatus.OK.value(),
                "Usuario autenticado: "
                        + authentication.getName()
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USUARIO_CREAR')")
    public ApiResponse<UsuarioResponseDTO>
    crearUsuario(
            @Valid
            @RequestBody
            UsuarioRequestDTO request) {

        UsuarioResponseDTO response =
                usuarioService.crearUsuario(request);

        return new ApiResponse<>(
                SuccessCodes.USUARIO_CREADO,
                SuccessMessages.USUARIO_CREADO,
                HttpStatus.CREATED.value(),
                response
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USUARIO_VER')")
    public ApiResponse<List<UsuarioResponseDTO>>
    listarUsuarios() {

        List<UsuarioResponseDTO> usuarios =
                usuarioService.listarUsuarios();

        return new ApiResponse<>(
                SuccessCodes.USUARIOS_OBTENIDOS,
                SuccessMessages.USUARIOS_OBTENIDOS,
                HttpStatus.OK.value(),
                usuarios
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIO_VER')")
    public ApiResponse<UsuarioResponseDTO>
    obtenerUsuarioPorId(
            @PathVariable Long id) {

        UsuarioResponseDTO response =
                usuarioService.obtenerUsuarioPorId(id);

        return new ApiResponse<>(
                SuccessCodes.USUARIO_OBTENIDO,
                SuccessMessages.USUARIO_OBTENIDO,
                HttpStatus.OK.value(),
                response
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIO_EDITAR')")
    public ApiResponse<UsuarioResponseDTO>
    actualizarUsuario(
            @PathVariable Long id,
            @Valid
            @RequestBody
            UsuarioRequestDTO request) {

        UsuarioResponseDTO response =
                usuarioService.actualizarUsuario(id, request);

        return new ApiResponse<>(
                SuccessCodes.USUARIO_ACTUALIZADO,
                SuccessMessages.USUARIO_ACTUALIZADO,
                HttpStatus.OK.value(),
                response
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIO_ELIMINAR')")
    public ApiResponse<String>
    eliminarUsuario(
            @PathVariable Long id) {

        usuarioService.eliminarUsuario(id);

        return new ApiResponse<>(
                SuccessCodes.USUARIO_ELIMINADO,
                SuccessMessages.USUARIO_ELIMINADO,
                HttpStatus.OK.value(),
                "Usuario eliminado correctamente"
        );
    }

    @PutMapping("/{id}/roles/{rolNombre}")
    @PreAuthorize("hasAuthority('USUARIO_EDITAR')")
    public ApiResponse<String>
    asignarRol(
            @PathVariable Long id,
            @PathVariable String rolNombre) {

        usuarioService.asignarRol(id, rolNombre);

        return new ApiResponse<>(
                SuccessCodes.ROL_ASIGNADO,
                SuccessMessages.ROL_ASIGNADO,
                HttpStatus.OK.value(),
                SuccessMessages.ROL_ASIGNADO
        );
    }

    @DeleteMapping("/{id}/roles/{rolNombre}")
    @PreAuthorize("hasAuthority('USUARIO_EDITAR')")
    public ApiResponse<String>
    removerRol(
            @PathVariable Long id,
            @PathVariable String rolNombre) {

        usuarioService.removerRol(id, rolNombre);

        return new ApiResponse<>(
                SuccessCodes.ROL_REMOVIDO,
                SuccessMessages.ROL_REMOVIDO,
                HttpStatus.OK.value(),
                SuccessMessages.ROL_REMOVIDO
        );
    }

    @GetMapping("/resumen")
    public ResponseEntity<Long> resumen() {
        return ResponseEntity.ok(
                (long) usuarioService.listarUsuarios().size());
    }

    @GetMapping("/debug")
    public Object debug(
            Authentication authentication) {

        return authentication.getAuthorities();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioResponseDTO>
    obtenerPorUsername(
            @PathVariable String username) {

        return ResponseEntity.ok(
                usuarioService.obtenerPorUsername(username));
    }

    @GetMapping("/auth/{username}")
    public UsuarioAuthDTO findByUsernameForAuth(
            @PathVariable String username) {

        return usuarioService
                .findByUsernameForAuth(username);
    }
}
