package com.mentoria.agil.backend.interfaces.service;

import com.mentoria.agil.backend.dto.UserRequestDTO;
import com.mentoria.agil.backend.model.User;

public interface UserServiceInterface {
    User salvarUsuario(UserRequestDTO dto);
    User buscarPorEmail(String email);
}