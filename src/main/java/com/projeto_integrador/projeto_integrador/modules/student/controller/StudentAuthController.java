package com.projeto_integrador.projeto_integrador.modules.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto_integrador.projeto_integrador.modules.student.dto.LoginRequest;
import com.projeto_integrador.projeto_integrador.modules.student.usecases.AuthenticateStudentUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("student")
public class StudentAuthController {
    
    @Autowired
    AuthenticateStudentUseCase authenticateStudent;
    
    @PostMapping("/login")
        public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
            try {
                var student = authenticateStudent.execute(loginRequest.getInstitutionalEmail(), loginRequest.getStudentPassword());
                return ResponseEntity.ok().body(student);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
}
