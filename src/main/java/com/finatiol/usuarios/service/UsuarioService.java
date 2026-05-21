package com.finatiol.usuarios.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finatiol.usuarios.dto.UsuarioAuthDTO;
import com.finatiol.usuarios.dto.UsuarioRequestDTO;
import com.finatiol.usuarios.dto.UsuarioResponseDTO;
import com.finatiol.usuarios.entity.RolEntity;
import com.finatiol.usuarios.entity.UsuarioEntity;
import com.finatiol.usuarios.exception.UsuarioNoEncontradoException;
import com.finatiol.usuarios.repository.RolRepository;
import com.finatiol.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository
            usuarioRepository;

    private final PasswordEncoder
            passwordEncoder;

    private final RolRepository
            rolRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            RolRepository rolRepository) {

        this.usuarioRepository =
                usuarioRepository;

        this.passwordEncoder =
                passwordEncoder;

        this.rolRepository =
                rolRepository;
    }

    public UsuarioResponseDTO crearUsuario(
            UsuarioRequestDTO request) {

        UsuarioEntity usuario =
                new UsuarioEntity();

        usuario.setNombre(
                request.getNombre());

        usuario.setUsername(
                request.getUsername());

        usuario.setEmail(
                request.getEmail());

        usuario.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        usuario.setActivo(true);

        UsuarioEntity usuarioGuardado =
                usuarioRepository.save(usuario);

        return toResponseDTO(usuarioGuardado);
    }

    public List<UsuarioResponseDTO>
    listarUsuarios() {

        return usuarioRepository
                .findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public UsuarioResponseDTO
    obtenerUsuarioPorId(Long id) {

        UsuarioEntity usuario =
                usuarioRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new UsuarioNoEncontradoException(
                                        "Usuario no encontrado"));

        return toResponseDTO(usuario);
    }

    public UsuarioResponseDTO
    actualizarUsuario(
            Long id,
            UsuarioRequestDTO request) {

        UsuarioEntity usuario =
                usuarioRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new UsuarioNoEncontradoException(
                                        "Usuario no encontrado"));

        usuario.setNombre(
                request.getNombre());

        usuario.setUsername(
                request.getUsername());

        usuario.setEmail(
                request.getEmail());

        if (request.getPassword() != null
                && !request.getPassword().isBlank()) {

            usuario.setPassword(
                    passwordEncoder.encode(
                            request.getPassword()));
        }

        return toResponseDTO(
                usuarioRepository.save(usuario));
    }

    public void eliminarUsuario(Long id) {

        UsuarioEntity usuario =
                usuarioRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new UsuarioNoEncontradoException(
                                        "Usuario no encontrado"));

        usuarioRepository.delete(usuario);
    }

    public void asignarRol(
            Long usuarioId,
            String rolNombre) {

        UsuarioEntity usuario =
                usuarioRepository
                        .findById(usuarioId)
                        .orElseThrow(() ->
                                new UsuarioNoEncontradoException(
                                        "Usuario no encontrado"));

        RolEntity rol = rolRepository
                .findByNombre(rolNombre)
                .orElseThrow(() ->
                        new UsuarioNoEncontradoException(
                                "Rol no encontrado: " + rolNombre));

        usuario.getRoles().add(rol);

        usuarioRepository.save(usuario);
    }

    public void removerRol(
            Long usuarioId,
            String rolNombre) {

        UsuarioEntity usuario =
                usuarioRepository
                        .findById(usuarioId)
                        .orElseThrow(() ->
                                new UsuarioNoEncontradoException(
                                        "Usuario no encontrado"));

        usuario.getRoles().removeIf(
                rol -> rol.getNombre().equals(rolNombre));

        usuarioRepository.save(usuario);
    }

    private UsuarioResponseDTO toResponseDTO(
            UsuarioEntity usuario) {

        List<String> roles = usuario.getRoles()
                .stream()
                .map(RolEntity::getNombre)
                .toList();

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getActivo(),
                roles
        );
    }

    public UsuarioAuthDTO findByUsernameForAuth(
            String username) {

        UsuarioEntity usuario =
                usuarioRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new UsuarioNoEncontradoException(
                                        "Usuario no encontrado"));

        List<String> roles = usuario.getRoles()
                .stream()
                .map(RolEntity::getNombre)
                .toList();

        List<String> permisos = usuario.getRoles()
                .stream()
                .flatMap(rol -> rol.getPermisos().stream())
                .map(permiso -> permiso.getNombre())
                .distinct()
                .toList();

        UsuarioAuthDTO dto = new UsuarioAuthDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setPassword(usuario.getPassword());
        dto.setActivo(usuario.getActivo());
        dto.setRoles(roles);
        dto.setPermisos(permisos);
        return dto;
    }
}
