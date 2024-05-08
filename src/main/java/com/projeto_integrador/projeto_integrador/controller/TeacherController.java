package com.projeto_integrador.projeto_integrador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.projeto_integrador.projeto_integrador.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.repository.TeacherRepository;

@RestController
@RequestMapping("teacher")
public class TeacherController {
    
    @Autowired
    TeacherRepository repository;

    @PostMapping("/")
    public TeacherEntity create(@RequestBody TeacherEntity teacherEntity) {
        return this.repository.save(teacherEntity);
    }

    @GetMapping("/")
    public ResponseEntity<List<TeacherEntity>> getAllTeachers() {
        var AllTeachers = repository.findAll();
        return ResponseEntity.ok(AllTeachers);
    }


}
