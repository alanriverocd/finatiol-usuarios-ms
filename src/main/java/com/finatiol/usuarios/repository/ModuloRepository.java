package com.finatiol.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finatiol.usuarios.entity.ModuloEntity;

@Repository
public interface ModuloRepository
        extends JpaRepository<ModuloEntity, Long> {
}
