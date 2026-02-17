package com.mentoria.agil.backend.interfaces.service;

import com.mentoria.agil.backend.model.User;
import java.util.Date;

public interface TokenServiceInterface {
    String generateToken(User user);
    String getSubjectFromToken(String token);
    Date getExpirationFromToken(String token);
}