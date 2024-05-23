package com.projeto_integrador.projeto_integrador.modules.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto_integrador.projeto_integrador.modules.admin.dto.LoginRequest;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.AuthenticateAdminUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("admin")
public class AuthController {
    
    @Autowired
    AuthenticateAdminUseCase authenticateAdmin;
    
    @PostMapping("/login")
        public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
            try {
                var admin = authenticateAdmin.execute(loginRequest.getEmail(), loginRequest.getAdminPassword());
                return ResponseEntity.ok().body(admin);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
}
