package com.projeto_integrador.projeto_integrador.modules.courses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.CreateCourse;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.DeleteCourseById;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.GetAllCourses;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.GetCourseById;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.PutCourseById;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("courses")
public class CourseController {
    
    @Autowired
    CourseRepository repository;

    @Autowired
    CreateCourse createCourse;

    @Autowired
    GetAllCourses getAllCourses;

    @Autowired
    GetCourseById getCourseById;

    @Autowired
    PutCourseById putCourseById;

    @Autowired
    DeleteCourseById deleteCourseById;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CourseEntity courseEntity) {
        try {
            var result = this.createCourse.execute(courseEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<CourseEntity>> getAllCourses() {
       try {
            var result = this.getAllCourses.execute();
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
            throw new EntityNotFoundException("Course not Register");
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseEntity> getById(@Valid @PathVariable long id){
       try {
        var course = this.getCourseById.execute(id);
        return ResponseEntity.ok().body(course);
       } catch (Exception e) {
            throw new EntityNotFoundException("Course not found");
       }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseEntity> putCourse(@Valid @RequestBody CourseEntity courseEntity, @PathVariable Long id) {
        try {
            var updatedCourse = this.putCourseById.execute(id, courseEntity);
            return ResponseEntity.ok().body(updatedCourse);
        } catch (Exception e) {
            throw new EntityNotFoundException("Course not found");
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@Valid @PathVariable Long id) {
        this.deleteCourseById.execute(id);
        return ResponseEntity.ok().build();
    }


}
