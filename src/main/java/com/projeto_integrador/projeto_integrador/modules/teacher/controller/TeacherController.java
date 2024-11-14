package com.projeto_integrador.projeto_integrador.modules.teacher.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.CreateTeacher;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.DeleteTeacherById;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.GetAllTeachers;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.GetTeacherById;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.ProfileTeacherUseCase;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.PutTeacherById;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private CreateTeacher createTeacher;

    @Autowired
    private GetAllTeachers getAllTeachers;

    @Autowired
    private GetTeacherById getTeacherById;

    @Autowired
    private PutTeacherById putTeacherById;

    @Autowired
    private DeleteTeacherById deleteTeacherById;

    @Autowired
    private ProfileTeacherUseCase profileTeacherUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> create(@Valid @RequestBody TeacherEntity teacherEntity) {
        try {
            var result = this.createTeacher.execute(teacherEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<?> getAllTeachers(HttpServletRequest request) {
        var role = request.isUserInRole("ADMIN");

        try {
            if (role) {
                var allTeachers = this.getAllTeachers.execute();
                return ResponseEntity.ok().body(allTeachers);
            } else{
                var teacherId = request.getAttribute("teacher_id");
                var profile = this.profileTeacherUseCase
                    .execute(Long.parseLong(teacherId.toString()));
                return ResponseEntity.ok().body(profile);
            }
            
       } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
       }
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable long id) {
        try {
            var teacherMap = this.getTeacherById.execute(id);
            return ResponseEntity.ok().body(teacherMap);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<?> putTeacher(@Valid @RequestBody TeacherEntity teacherEntity, @PathVariable Long id) {
        try {
            var updatedTeacher = this.putTeacherById.execute(id, teacherEntity);
            return ResponseEntity.ok().body(updatedTeacher);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
        
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteTeacher(@Valid @PathVariable Long id) {
        try {   
            this.deleteTeacherById.execute(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
