package com.gonet.api_validator.service;

import java.util.Map;

public interface UserService {
    // Retorna un mensaje o token si las credenciales son válidas
    boolean authenticate(String username, String password);
}