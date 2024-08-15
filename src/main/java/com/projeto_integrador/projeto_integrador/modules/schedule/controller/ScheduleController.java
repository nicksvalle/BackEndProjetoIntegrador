package com.projeto_integrador.projeto_integrador.modules.schedule.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.CreateSchedule;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.DeleteScheduleById;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.GetAllSchedules;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.GetScheduleById;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.PutScheduleById;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("schedule")
@CrossOrigin
public class ScheduleController {
    
    @Autowired
    ScheduleRepository repository;

    @Autowired
    CreateSchedule createSchedule;

    @Autowired
    GetAllSchedules getAllSchedules;

    @Autowired
    GetScheduleById getScheduleById;

    @Autowired
    PutScheduleById putScheduleById;

    @Autowired
    DeleteScheduleById deleteScheduleById;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody ScheduleEntity scheduleEntity) {
        try {
            var result = this.createSchedule.execute(scheduleEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllSchedules() {
        try {
            List<Map<String, Object>> schedules = getAllSchedules.execute();
            return ResponseEntity.ok().body(schedules);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable long id) {
        try {
            var scheduleMap = this.getScheduleById.execute(id);
            return ResponseEntity.ok().body(scheduleMap);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putSchedule(@Valid @RequestBody ScheduleEntity ScheduleEntity, @PathVariable Long id) {
        try {
            var updatedSchedule = this.putScheduleById.execute(id, ScheduleEntity);
            return ResponseEntity.ok().body(updatedSchedule);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@Valid @PathVariable Long id) {
        this.deleteScheduleById.execute(id);
        return ResponseEntity.ok().build();
    }


}
