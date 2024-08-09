package com.projeto_integrador.projeto_integrador.modules.schedule.usecases;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.schedule.Validation;
import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;

import lombok.val;

@Service
public class CreateSchedule {
    
    @Autowired
    ScheduleRepository repository;

    @Autowired
    private Validation validation;

    public ScheduleEntity execute(ScheduleEntity scheduleEntity){
        Long subjectId = scheduleEntity.getSubject();
        validation.validateSubjectExist(subjectId);

        Long timeId = scheduleEntity.getTime();
        validation.validateTimeExist(timeId);

        Long teacherId = scheduleEntity.getTeacher();
        validation.validateTeacherExist(teacherId);

        Long roomId = scheduleEntity.getRoom();
        validation.validateRoomExist(roomId);
        
        return this.repository.save(scheduleEntity);
    }
}
