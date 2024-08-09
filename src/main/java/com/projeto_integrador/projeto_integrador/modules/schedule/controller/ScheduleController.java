package com.projeto_integrador.projeto_integrador.modules.schedule.controller;

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
    public ResponseEntity<List<ScheduleEntity>> getAllSchedules() {
       try {
            var result = this.getAllSchedules.execute();
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
            throw new EntityNotFoundException("Schedule not Registered");
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleEntity> getById(@Valid @PathVariable long id){
       try {
        var time = this.getScheduleById.execute(id);
        return ResponseEntity.ok().body(time);
       } catch (Exception e) {
            throw new EntityNotFoundException("Schedule not found");
       }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleEntity> putTime(@Valid @RequestBody ScheduleEntity ScheduleEntity, @PathVariable Long id) {
        try {
            var updatedSchedule = this.putScheduleById.execute(id, ScheduleEntity);
            return ResponseEntity.ok().body(updatedSchedule);
        } catch (Exception e) {
            throw new EntityNotFoundException("Schedule not found");
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@Valid @PathVariable Long id) {
        this.deleteScheduleById.execute(id);
        return ResponseEntity.ok().build();
    }


}
