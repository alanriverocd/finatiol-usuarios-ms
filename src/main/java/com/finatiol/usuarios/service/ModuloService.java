package com.finatiol.usuarios.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.finatiol.usuarios.dto.ModuloRequestDTO;
import com.finatiol.usuarios.dto.ModuloResponseDTO;
import com.finatiol.usuarios.entity.ModuloEntity;
import com.finatiol.usuarios.exception.UsuarioNoEncontradoException;
import com.finatiol.usuarios.repository.ModuloRepository;

@Service
public class ModuloService {

    private final ModuloRepository moduloRepository;

    public ModuloService(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    public ModuloResponseDTO crearModulo(
            ModuloRequestDTO request) {

        ModuloEntity modulo = new ModuloEntity();
        modulo.setNombre(request.getNombre());
        modulo.setDescripcion(request.getDescripcion());
        modulo.setRuta(request.getRuta());
        modulo.setIcono(request.getIcono());
        modulo.setActivo(request.getActivo());

        return mapToDTO(moduloRepository.save(modulo));
    }

    public List<ModuloResponseDTO> listarModulos() {

        return moduloRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public ModuloResponseDTO obtenerModuloPorId(Long id) {

        ModuloEntity modulo = moduloRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNoEncontradoException(
                                "Modulo no encontrado"));

        return mapToDTO(modulo);
    }

    public void eliminarModulo(Long id) {

        ModuloEntity modulo = moduloRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNoEncontradoException(
                                "Modulo no encontrado"));

        moduloRepository.delete(modulo);
    }

    public ModuloResponseDTO mapToDTO(ModuloEntity modulo) {

        return new ModuloResponseDTO(
                modulo.getId(),
                modulo.getNombre(),
                modulo.getDescripcion(),
                modulo.getRuta(),
                modulo.getIcono(),
                modulo.getActivo()
        );
    }
}
