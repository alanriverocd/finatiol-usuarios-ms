package com.finatiol.usuarios.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.finatiol.usuarios.dto.ModuloResponseDTO;
import com.finatiol.usuarios.dto.PermisoRequestDTO;
import com.finatiol.usuarios.dto.PermisoResponseDTO;
import com.finatiol.usuarios.entity.ModuloEntity;
import com.finatiol.usuarios.entity.PermisoEntity;
import com.finatiol.usuarios.exception.UsuarioNoEncontradoException;
import com.finatiol.usuarios.repository.ModuloRepository;
import com.finatiol.usuarios.repository.PermisoRepository;

@Service
public class PermisoService {

    private final PermisoRepository permisoRepository;

    private final ModuloRepository moduloRepository;

    public PermisoService(
            PermisoRepository permisoRepository,
            ModuloRepository moduloRepository) {

        this.permisoRepository = permisoRepository;
        this.moduloRepository = moduloRepository;
    }

    public PermisoResponseDTO crearPermiso(
            PermisoRequestDTO request) {

        ModuloEntity modulo = moduloRepository
                .findById(request.getModuloId())
                .orElseThrow(() ->
                        new UsuarioNoEncontradoException(
                                "Modulo no encontrado"));

        PermisoEntity permiso = new PermisoEntity();
        permiso.setNombre(request.getNombre());
        permiso.setDescripcion(request.getDescripcion());
        permiso.setModulo(modulo);

        return mapToDTO(permisoRepository.save(permiso));
    }

    public List<PermisoResponseDTO> listarPermisos() {

        return permisoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public PermisoResponseDTO obtenerPermisoPorId(Long id) {

        PermisoEntity permiso = permisoRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNoEncontradoException(
                                "Permiso no encontrado"));

        return mapToDTO(permiso);
    }

    public void eliminarPermiso(Long id) {

        PermisoEntity permiso = permisoRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNoEncontradoException(
                                "Permiso no encontrado"));

        permisoRepository.delete(permiso);
    }

    public PermisoResponseDTO mapToDTO(PermisoEntity permiso) {

        return new PermisoResponseDTO(
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
        );
    }
}
