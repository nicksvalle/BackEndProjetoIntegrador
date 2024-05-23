package com.projeto_integrador.projeto_integrador.modules.courses.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetAllCourses {
    
    @Autowired
    CourseRepository courseRepository;

    public List<CourseEntity> execute() {
        var allCourses = courseRepository.findAll();
        if (allCourses.isEmpty()) {
            throw new EntityNotFoundException("Course not Register");
        }
        return allCourses;
    }
}

