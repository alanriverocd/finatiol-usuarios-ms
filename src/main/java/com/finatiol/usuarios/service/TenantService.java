package com.finatiol.usuarios.service;

import com.finatiol.usuarios.entity.TenantEntity;
import com.finatiol.usuarios.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public List<TenantEntity> listarTenants() {
        return tenantRepository.findAll();
    }

    public TenantEntity crearTenant(TenantEntity tenant) {
        return tenantRepository.save(tenant);
    }

    public TenantEntity obtenerTenant(String id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant no encontrado: " + id));
    }

    public void eliminarTenant(String id) {
        tenantRepository.deleteById(id);
    }
}
