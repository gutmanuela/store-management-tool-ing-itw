package com.ing.store_management.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleDto {
    private Long id;
    private String name;
    private String description;
}
