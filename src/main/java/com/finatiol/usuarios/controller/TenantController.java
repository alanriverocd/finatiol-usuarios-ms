package com.finatiol.usuarios.controller;

import com.finatiol.usuarios.entity.TenantEntity;
import com.finatiol.usuarios.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public ResponseEntity<List<TenantEntity>> listar() {
        return ResponseEntity.ok(tenantService.listarTenants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantEntity> obtener(@PathVariable String id) {
        return ResponseEntity.ok(tenantService.obtenerTenant(id));
    }

    @PostMapping
    public ResponseEntity<TenantEntity> crear(@RequestBody TenantEntity tenant) {
        return ResponseEntity.ok(tenantService.crearTenant(tenant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        tenantService.eliminarTenant(id);
        return ResponseEntity.noContent().build();
    }
}
