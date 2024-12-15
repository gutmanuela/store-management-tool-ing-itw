package com.ing.store_management.controller;

import com.ing.store_management.model.dto.RoleDto;
import com.ing.store_management.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto RoleDto) {
        roleService.addRole(RoleDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{roleName}")
    public ResponseEntity<Void> deleteRole(@PathVariable String roleName) {
        roleService.deleteRoleByName(roleName);
        return ResponseEntity.noContent().build();
    }
}
