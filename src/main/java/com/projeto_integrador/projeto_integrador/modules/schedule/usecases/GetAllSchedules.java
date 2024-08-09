package com.projeto_integrador.projeto_integrador.modules.schedule.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetAllSchedules {
    
    @Autowired
    ScheduleRepository repository;

    public List<ScheduleEntity> execute() {
        var allSchedules = repository.findAll();
        if (allSchedules.isEmpty()) {
            throw new EntityNotFoundException("Schedule not Registered");
        }
        return allSchedules;
    }
}

