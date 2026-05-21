package com.finatiol.usuarios.config;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.finatiol.usuarios.entity.ModuloEntity;
import com.finatiol.usuarios.entity.PermisoEntity;
import com.finatiol.usuarios.entity.RolEntity;
import com.finatiol.usuarios.entity.UsuarioEntity;
import com.finatiol.usuarios.repository.ModuloRepository;
import com.finatiol.usuarios.repository.PermisoRepository;
import com.finatiol.usuarios.repository.RolRepository;
import com.finatiol.usuarios.repository.UsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PermisoRepository permisoRepository,
            ModuloRepository moduloRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (usuarioRepository.existsByUsername("admin")) {
                System.out.println("ADMIN YA EXISTE");
                return;
            }

            ModuloEntity moduloUsuarios = new ModuloEntity();
            moduloUsuarios.setNombre("USUARIOS");
            moduloUsuarios.setDescripcion("Modulo usuarios");
            moduloUsuarios.setRuta("/usuarios");
            moduloUsuarios.setIcono("user-icon");
            moduloUsuarios.setActivo(true);
            moduloUsuarios = moduloRepository.save(moduloUsuarios);

            PermisoEntity verPerfil =
                    crearPermiso("VER_PERFIL", "Permite ver perfil", moduloUsuarios);
            PermisoEntity usuarioCrear =
                    crearPermiso("USUARIO_CREAR", "Crear usuarios", moduloUsuarios);
            PermisoEntity usuarioEditar =
                    crearPermiso("USUARIO_EDITAR", "Editar usuarios", moduloUsuarios);
            PermisoEntity usuarioEliminar =
                    crearPermiso("USUARIO_ELIMINAR", "Eliminar usuarios", moduloUsuarios);
            PermisoEntity usuarioVer =
                    crearPermiso("USUARIO_VER", "Ver usuarios", moduloUsuarios);

            permisoRepository.saveAll(List.of(
                    verPerfil,
                    usuarioCrear,
                    usuarioEditar,
                    usuarioEliminar,
                    usuarioVer
            ));

            RolEntity adminRole = new RolEntity();
            adminRole.setNombre("ADMIN");
            adminRole.setDescripcion("Administrador sistema");
            adminRole.setPermisos(Set.of(
                    verPerfil,
                    usuarioCrear,
                    usuarioEditar,
                    usuarioEliminar,
                    usuarioVer
            ));
            adminRole = rolRepository.save(adminRole);

            UsuarioEntity admin = new UsuarioEntity();
            admin.setNombre("Administrador");
            admin.setUsername("admin");
            admin.setEmail("admin@test.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setActivo(true);
            admin.setRoles(Set.of(adminRole));
            usuarioRepository.save(admin);

            System.out.println("ADMIN CREADO CORRECTAMENTE");
        };
    }

    private PermisoEntity crearPermiso(
            String nombre,
            String descripcion,
            ModuloEntity modulo) {

        PermisoEntity permiso = new PermisoEntity();
        permiso.setNombre(nombre);
        permiso.setDescripcion(descripcion);
        permiso.setModulo(modulo);
        return permiso;
    }
}
