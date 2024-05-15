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
import com.projeto_integrador.projeto_integrador.usecases.DeleteTeacherById;
import com.projeto_integrador.projeto_integrador.usecases.GetTeacherById;
import com.projeto_integrador.projeto_integrador.usecases.PutTeacherById;

@RestController
@RequestMapping("teacher")
public class TeacherController {
    
    @Autowired
    TeacherRepository repository;

    @Autowired
    GetTeacherById getTeacherById;

    @Autowired
    PutTeacherById putTeacherById;

    @Autowired
    DeleteTeacherById deleteTeacherById;

    @PostMapping("/")
    public TeacherEntity create(@RequestBody TeacherEntity teacherEntity) {
        return this.repository.save(teacherEntity);
    }

    @GetMapping("/")
    public ResponseEntity<List<TeacherEntity>> getAllTeachers() {
        var AllTeachers = repository.findAll();
        return ResponseEntity.ok(AllTeachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherEntity> getById(@PathVariable long id){
        var teacher = this.getTeacherById.execute(id);
        return ResponseEntity.ok().body(teacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherEntity> putTeacher(@RequestBody TeacherEntity teacherEntity, @PathVariable Long id) {
        var updatedTeacher = this.putTeacherById.execute(id, teacherEntity);
        return ResponseEntity.ok().body(updatedTeacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        this.deleteTeacherById.execute(id);
        return ResponseEntity.ok().build();
    }


}
