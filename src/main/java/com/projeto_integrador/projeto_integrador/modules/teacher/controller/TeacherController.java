package com.projeto_integrador.projeto_integrador.modules.teacher.controller;

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

import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.CreateTeacher;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.DeleteTeacherById;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.GetAllTeachers;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.GetTeacherById;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.PutTeacherById;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("teacher")
public class TeacherController {
    
    @Autowired
    TeacherRepository repository;

    @Autowired
    CreateTeacher createTeacher;

    @Autowired
    GetAllTeachers getAllTeachers;

    @Autowired
    GetTeacherById getTeacherById;

    @Autowired
    PutTeacherById putTeacherById;

    @Autowired
    DeleteTeacherById deleteTeacherById;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody TeacherEntity teacherEntity) {
        try {
            var result = this.createTeacher.execute(teacherEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<TeacherEntity>> getAllTeachers() {
       try {
            var result = this.getAllTeachers.execute();
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
            throw new EntityNotFoundException("Teacher not Register");
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherEntity> getById(@Valid @PathVariable long id){
       try {
        var teacher = this.getTeacherById.execute(id);
        return ResponseEntity.ok().body(teacher);
       } catch (Exception e) {
            throw new EntityNotFoundException("Teacher not found");
       }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putTeacher(@Valid @RequestBody TeacherEntity teacherEntity, @PathVariable Long id) {
        try {
            var updatedTeacher = this.putTeacherById.execute(id, teacherEntity);
            return ResponseEntity.ok().body(updatedTeacher);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@Valid @PathVariable Long id) {
        this.deleteTeacherById.execute(id);
        return ResponseEntity.ok().build();
    }


}
