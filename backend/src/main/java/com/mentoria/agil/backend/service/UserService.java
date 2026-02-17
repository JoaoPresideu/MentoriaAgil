package com.mentoria.agil.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mentoria.agil.backend.dto.UserRequestDTO; // Importe o nome correto
import com.mentoria.agil.backend.interfaces.service.UserServiceInterface;
import com.mentoria.agil.backend.model.Role;
import com.mentoria.agil.backend.model.User;
import com.mentoria.agil.backend.repository.UserRepository;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User salvarUsuario(UserRequestDTO dto) {
        // Verifica se o e-mail já existe
        if (userRepository.existsByEmail(dto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado.");
        }
        
        User user = new User();
        user.setName(dto.name());    // Records usam .name() em vez de .getName()
        user.setEmail(dto.email());  // Records usam .email() em vez de .getEmail()
        
        // Define a role: usa a do DTO ou VISITANTE por padrão
        user.setRole(dto.role() != null ? dto.role() : Role.VISITANTE);
        
        // Criptografia da senha
        user.setPassword(passwordEncoder.encode(dto.password()));
        
        return userRepository.save(user);
    }

    @Override
    public User buscarPorEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}