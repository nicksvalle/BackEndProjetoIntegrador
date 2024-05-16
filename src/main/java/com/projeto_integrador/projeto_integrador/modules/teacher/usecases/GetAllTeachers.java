package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetAllTeachers {
    
    @Autowired
    TeacherRepository teacherRepository;

    public List<TeacherEntity> execute() {
        var allTeachers = teacherRepository.findAll();
        if (allTeachers.isEmpty()) {
            throw new EntityNotFoundException("Teacher not Register");
        }
        return allTeachers;
    }
}

