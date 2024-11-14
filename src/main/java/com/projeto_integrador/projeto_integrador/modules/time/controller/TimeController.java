package com.projeto_integrador.projeto_integrador.modules.time.controller;


import org.springframework.beans.factory.annotation.Autowired;
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

import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.CreateTime;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.DeleteTimeById;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.GetAllTimes;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.GetTimeById;
import com.projeto_integrador.projeto_integrador.modules.time.usecases.PutTimeById;

import jakarta.validation.Valid;

@RestController
@RequestMapping("time")
@CrossOrigin
public class TimeController {
    
    @Autowired
    TimeRepository repository;

    @Autowired
    CreateTime createTime;

    @Autowired
    GetAllTimes getAllTimes;

    @Autowired
    GetTimeById getTimeById;

    @Autowired
    PutTimeById putTimeById;

    @Autowired
    DeleteTimeById deleteTimeById;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> create(@Valid @RequestBody TimeEntity timeEntity) {
        try {
            var result = this.createTime.execute(timeEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllTimes() {
       try {
            var result = this.getAllTimes.execute();
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getById(@Valid @PathVariable long id){
       try {
            var time = this.getTimeById.execute(id);
            return ResponseEntity.ok().body(time);
       } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
       }
        
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> putTime(@Valid @RequestBody TimeEntity timeEntity, @PathVariable Long id) {
        try {
            var updatedTime = this.putTimeById.execute(id, timeEntity);
            return ResponseEntity.ok().body(updatedTime);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteTime(@Valid @PathVariable Long id) {    
        try {           
            this.deleteTimeById.execute(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
