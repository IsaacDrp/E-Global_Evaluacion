package com.gonet.api_validator.controller;

import com.gonet.api_validator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("usuario");
        String password = credentials.get("password");

        if (userService.authenticate(username, password)) {
            return ResponseEntity.ok("Login exitoso. Bienvenido a la siguiente ventana.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos.");
    }
}