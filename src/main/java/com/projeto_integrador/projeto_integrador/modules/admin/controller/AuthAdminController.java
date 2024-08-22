package com.projeto_integrador.projeto_integrador.modules.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto_integrador.projeto_integrador.modules.admin.dto.AuthAdminDTO;
import com.projeto_integrador.projeto_integrador.modules.admin.usecases.AuthAdminUseCase;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthAdminController {
    
    @Autowired
    private AuthAdminUseCase authAdminUseCase;

    @PostMapping("/admin")
    public ResponseEntity<Object> create(@RequestBody AuthAdminDTO authAdminDTO) {
        try{
            var result = authAdminUseCase.execute(authAdminDTO);
            return ResponseEntity.ok(result);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
