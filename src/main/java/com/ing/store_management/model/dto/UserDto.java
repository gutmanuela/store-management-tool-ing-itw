package com.ing.store_management.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto {

    private String username;
    @JsonIgnore
    private String password;
    private String roleName;

    public UserDto() {
    }

    public UserDto(String username, String password, String userRoleName) {

        this.username = username;
        this.password = password;
        this.roleName = userRoleName;
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
