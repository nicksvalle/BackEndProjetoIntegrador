package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthenticateStudentUseCase {

    @Autowired
    private StudentRepository studentRepository;

    public StudentEntity execute(String email, String password) throws Exception {
        Optional<StudentEntity> studentOptional = studentRepository.findByInstitutionalEmail(email);
        if (studentOptional.isPresent()) {
            StudentEntity student = studentOptional.get();
            if (student.getStudentPassword().equals(password)) {
                return student;
            } else {
                throw new Exception("Invalid password");
            }
        } else {
            throw new Exception("Student not found");
        }
    }
}