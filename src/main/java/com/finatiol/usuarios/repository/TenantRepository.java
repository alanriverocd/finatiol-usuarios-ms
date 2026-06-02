package com.finatiol.usuarios.repository;

import com.finatiol.usuarios.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<TenantEntity, String> {
}
