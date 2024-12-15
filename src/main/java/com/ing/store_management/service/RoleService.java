package com.ing.store_management.service;

import com.ing.store_management.exception.ResourceNotFoundException;
import com.ing.store_management.model.dto.RoleDto;
import com.ing.store_management.model.entity.Role;
import com.ing.store_management.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

   public  RoleDto findRoleByName(String name) {
        Role role = roleRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Role with name " + name + " was not found."));
        return convertToDto(role);
    }

    public void addRole(RoleDto roleDto) {
        Role role = convertToEntity(roleDto);
        roleRepository.save(role);
    }

    @Transactional
    public void deleteRoleByName(String name) {
        roleRepository.deleteRoleByName(name);
    }

    private Role convertToEntity(RoleDto roledto) {
        Role role = new Role();
        role.setName(roledto.getName());
        role.setDescription(roledto.getDescription());
        return roleRepository.save(role);
    }

    private RoleDto convertToDto(Role role) {
        return new RoleDto(role.getName(), role.getDescription());
    }
}
