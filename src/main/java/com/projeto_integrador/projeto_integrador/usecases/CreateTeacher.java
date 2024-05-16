package com.projeto_integrador.projeto_integrador.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.exceptions.TeacherFoundException;
import com.projeto_integrador.projeto_integrador.repository.TeacherRepository;

@Service
public class CreateTeacher {
    
    @Autowired
    TeacherRepository teacherRepository;

    public TeacherEntity execute(TeacherEntity teacherEntity){
        this.teacherRepository.findByInstitutionalEmail(teacherEntity.getInstitutionalEmail())
                                .ifPresent(user -> {
                                    throw new TeacherFoundException();
        });

        return this.teacherRepository.save(teacherEntity);
    }
}
