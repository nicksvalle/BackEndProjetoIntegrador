package com.projeto_integrador.projeto_integrador.modules.student.usecases;

import org.springframework.beans.factory.annotation.Autowired;

import com.projeto_integrador.projeto_integrador.exceptions.UserFoundException;
import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.student.repository.StudentRepository;


public class CreateStudentUseCase {
    @Autowired
    StudentRepository studentRepository;

    public StudentEntity execute(StudentEntity studentEntity){
        this.studentRepository.findByInstitutionalEmail(studentEntity.getInstitutionalEmail())
                                .ifPresent(user -> {
                                    throw new UserFoundException();
        });

        return this.studentRepository.save(studentEntity);
    }
}
