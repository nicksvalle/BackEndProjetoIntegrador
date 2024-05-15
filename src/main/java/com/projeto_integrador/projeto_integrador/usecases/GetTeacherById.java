package com.projeto_integrador.projeto_integrador.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.projeto_integrador.projeto_integrador.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.repository.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetTeacherById {
    
    @Autowired
    TeacherRepository repository;
    
    public TeacherEntity execute(@PathVariable Long id) {
        return this.repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Teacher not found")
        );
    }
}
