package com.ing.store_management.service;

import com.ing.store_management.exception.ResourceNotFoundException;
import com.ing.store_management.model.dto.UserDto;
import com.ing.store_management.model.entity.Role;
import com.ing.store_management.model.entity.User;
import com.ing.store_management.repository.RoleRepository;
import com.ing.store_management.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto createUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String roleName = userDto.getRoleName();
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new ResourceNotFoundException("Role with name " + roleName + " was not found."));
        user.setRole(role);
        user = userRepository.save(user);
        return convertToDto(user);
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " was not found."));
    }

    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " was not found."));
    }

    private UserDto convertToDto(User user) {
        return new UserDto(user.getUsername(), user.getPassword(), user.getRole().getName());
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }
}

