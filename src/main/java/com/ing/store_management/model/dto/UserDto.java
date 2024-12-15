package com.ing.store_management.model.dto;


public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String roleName;

    public UserDto(Long id, String username, String password, String userRoleName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roleName = userRoleName;
    }

    public UserDto(String username, String password, String userRoleName) {
        this.username = username;
        this.password = password;
        this.roleName = userRoleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
