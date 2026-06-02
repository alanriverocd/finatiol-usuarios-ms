package com.finatiol.usuarios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.finatiol.usuarios.client.AuditoriaClient;
import com.finatiol.usuarios.client.NotificacionClient;
import com.finatiol.usuarios.dto.UsuarioRequestDTO;
import com.finatiol.usuarios.dto.UsuarioResponseDTO;
import com.finatiol.usuarios.entity.RolEntity;
import com.finatiol.usuarios.entity.UsuarioEntity;
import com.finatiol.usuarios.repository.RolRepository;
import com.finatiol.usuarios.repository.SolicitudProductoRepository;
import com.finatiol.usuarios.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceRegistrationTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private NotificacionClient notificacionClient;

    @Mock
    private AuditoriaClient auditoriaClient;

    @Mock
    private SolicitudProductoRepository solicitudProductoRepository;

    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioService(
                usuarioRepository,
                passwordEncoder,
                rolRepository,
                notificacionClient,
                auditoriaClient,
                solicitudProductoRepository,
                "CLIENTE");
    }

    @Test
    void registrarUsuarioPublico_debeAsignarRolClienteSiExiste() {
        UsuarioRequestDTO request = buildRequest();

        RolEntity cliente = new RolEntity();
        cliente.setId(10L);
        cliente.setNombre("CLIENTE");

        when(usuarioRepository.existsByUsername("juana"))
                .thenReturn(false);
        when(usuarioRepository.existsByEmailIgnoreCase("juana@mail.com"))
                .thenReturn(false);
        when(passwordEncoder.encode("123456"))
                .thenReturn("ENCODED");
        when(rolRepository.findByNombre("CLIENTE"))
                .thenReturn(Optional.of(cliente));
        when(usuarioRepository.save(any(UsuarioEntity.class)))
                .thenAnswer(invocation -> {
                    UsuarioEntity entity = invocation.getArgument(0);
                    entity.setId(100L);
                    return entity;
                });

        UsuarioResponseDTO response = usuarioService.registrarUsuarioPublico(request);

        ArgumentCaptor<UsuarioEntity> captor = ArgumentCaptor.forClass(UsuarioEntity.class);
        verify(usuarioRepository).save(captor.capture());
        UsuarioEntity saved = captor.getValue();

        assertTrue(saved.getRoles().stream().anyMatch(r -> "CLIENTE".equals(r.getNombre())));
        assertEquals("juana", response.getUsername());
        assertTrue(response.getRoles().contains("CLIENTE"));

        verify(notificacionClient, times(1)).enviarEmail(any());
    }

    @Test
    void registrarUsuarioPublico_debeCrearRolClienteSiNoExiste() {
        UsuarioRequestDTO request = buildRequest();

        when(usuarioRepository.existsByUsername("juana"))
                .thenReturn(false);
        when(usuarioRepository.existsByEmailIgnoreCase("juana@mail.com"))
                .thenReturn(false);
        when(passwordEncoder.encode("123456"))
                .thenReturn("ENCODED");
        when(rolRepository.findByNombre("CLIENTE"))
                .thenReturn(Optional.empty());
        when(rolRepository.save(any(RolEntity.class)))
                .thenAnswer(invocation -> {
                    RolEntity role = invocation.getArgument(0);
                    role.setId(11L);
                    return role;
                });
        when(usuarioRepository.save(any(UsuarioEntity.class)))
                .thenAnswer(invocation -> {
                    UsuarioEntity entity = invocation.getArgument(0);
                    entity.setId(101L);
                    return entity;
                });

        UsuarioResponseDTO response = usuarioService.registrarUsuarioPublico(request);

        verify(rolRepository, times(1)).save(any(RolEntity.class));
        ArgumentCaptor<UsuarioEntity> captor = ArgumentCaptor.forClass(UsuarioEntity.class);
        verify(usuarioRepository).save(captor.capture());
        UsuarioEntity saved = captor.getValue();

        assertTrue(saved.getRoles().stream().anyMatch(r -> "CLIENTE".equals(r.getNombre())));
        assertTrue(response.getRoles().contains("CLIENTE"));
    }

    private UsuarioRequestDTO buildRequest() {
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setNombre("Juana Menchaca");
        request.setUsername("juana");
        request.setEmail("juana@mail.com");
        request.setPassword("123456");
        return request;
    }
}
