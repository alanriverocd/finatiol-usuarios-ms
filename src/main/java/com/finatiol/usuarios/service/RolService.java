package com.finatiol.usuarios.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.finatiol.usuarios.dto.ModuloResponseDTO;
import com.finatiol.usuarios.dto.PermisoResponseDTO;
import com.finatiol.usuarios.dto.RolRequestDTO;
import com.finatiol.usuarios.dto.RolResponseDTO;
import com.finatiol.usuarios.entity.PermisoEntity;
import com.finatiol.usuarios.entity.RolEntity;
import com.finatiol.usuarios.exception.UsuarioNoEncontradoException;
import com.finatiol.usuarios.repository.PermisoRepository;
import com.finatiol.usuarios.repository.RolRepository;

@Service
public class RolService {

    private final RolRepository rolRepository;

    private final PermisoRepository permisoRepository;

    public RolService(
            RolRepository rolRepository,
            PermisoRepository permisoRepository) {

        this.rolRepository = rolRepository;
        this.permisoRepository = permisoRepository;
    }

    public RolResponseDTO crearRol(RolRequestDTO request) {

        Set<PermisoEntity> permisos = Set.copyOf(
                permisoRepository.findAllById(
                        request.getPermisosIds()));

        RolEntity rol = new RolEntity();
        rol.setNombre(request.getNombre());
        rol.setDescripcion(request.getDescripcion());
        rol.setPermisos(permisos);

        return mapToDTO(rolRepository.save(rol));
    }

    public List<RolResponseDTO> listarRoles() {

        return rolRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public RolResponseDTO obtenerRolPorId(Long id) {

        RolEntity rol = rolRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNoEncontradoException(
                                "Rol no encontrado"));

        return mapToDTO(rol);
    }

    public void eliminarRol(Long id) {

        RolEntity rol = rolRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNoEncontradoException(
                                "Rol no encontrado"));

        rolRepository.delete(rol);
    }

    public RolResponseDTO mapToDTO(RolEntity rol) {

        return new RolResponseDTO(
                rol.getId(),
                rol.getNombre(),
                rol.getDescripcion(),
                rol.getPermisos()
                        .stream()
                        .map(permiso ->
                                new PermisoResponseDTO(
                                        permiso.getId(),
                                        permiso.getNombre(),
                                        permiso.getDescripcion(),
                                        new ModuloResponseDTO(
                                                permiso.getModulo().getId(),
                                                permiso.getModulo().getNombre(),
                                                permiso.getModulo().getDescripcion(),
                                                permiso.getModulo().getRuta(),
                                                permiso.getModulo().getIcono(),
                                                permiso.getModulo().getActivo()
                                        )
                                )
                        )
                        .toList()
        );
    }
}
