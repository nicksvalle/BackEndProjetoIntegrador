package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ForgotPasswordService {
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmailService emailService;

    public void generateResetToken(String email) {
        var student = studentRepository.findByInstitutionalEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        String token = UUID.randomUUID().toString();
        student.setResetToken(token);
        studentRepository.save(student);

        emailService.sendResetPasswordEmail(email, token);
    }
}
