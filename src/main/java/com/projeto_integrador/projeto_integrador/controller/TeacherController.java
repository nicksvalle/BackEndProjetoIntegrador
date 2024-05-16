package com.projeto_integrador.projeto_integrador.controller;

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

import com.projeto_integrador.projeto_integrador.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.usecases.CreateTeacher;
import com.projeto_integrador.projeto_integrador.usecases.DeleteTeacherById;
import com.projeto_integrador.projeto_integrador.usecases.GetTeacherById;
import com.projeto_integrador.projeto_integrador.usecases.PutTeacherById;

import jakarta.validation.Valid;

@RestController
@RequestMapping("teacher")
public class TeacherController {
    
    @Autowired
    TeacherRepository repository;

    @Autowired
    CreateTeacher createTeacher;

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
        var AllTeachers = repository.findAll();
        return ResponseEntity.ok(AllTeachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherEntity> getById(@Valid @PathVariable long id){
        var teacher = this.getTeacherById.execute(id);
        return ResponseEntity.ok().body(teacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherEntity> putTeacher(@Valid @RequestBody TeacherEntity teacherEntity, @PathVariable Long id) {
        var updatedTeacher = this.putTeacherById.execute(id, teacherEntity);
        return ResponseEntity.ok().body(updatedTeacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@Valid @PathVariable Long id) {
        this.deleteTeacherById.execute(id);
        return ResponseEntity.ok().build();
    }


}
