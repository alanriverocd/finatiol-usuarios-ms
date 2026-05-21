package com.finatiol.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finatiol.usuarios.entity.PermisoEntity;

@Repository
public interface PermisoRepository
        extends JpaRepository<PermisoEntity, Long> {
}
