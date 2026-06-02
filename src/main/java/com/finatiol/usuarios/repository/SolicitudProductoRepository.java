package com.finatiol.usuarios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finatiol.usuarios.entity.SolicitudProductoEntity;

@Repository
public interface SolicitudProductoRepository extends JpaRepository<SolicitudProductoEntity, Long> {

    List<SolicitudProductoEntity> findAllByUsernameOrderByFechaSolicitudDesc(String username);

    List<SolicitudProductoEntity> findAllByOrderByFechaSolicitudDesc();
}
