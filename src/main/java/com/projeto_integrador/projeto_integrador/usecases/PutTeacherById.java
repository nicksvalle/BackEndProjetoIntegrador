package com.projeto_integrador.projeto_integrador.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.repository.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PutTeacherById {

    @Autowired
    TeacherRepository teacherRepository;
    
    public TeacherEntity execute(Long id, TeacherEntity teacherEntity) {
        TeacherEntity updateTeacher = this.teacherRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Teacher not found")
        );
        updateTeacher.setTeacher_name(teacherEntity.getTeacher_name());
        updateTeacher.setInstitutionalEmail(teacherEntity.getInstitutionalEmail());
        updateTeacher.setPersonal_email(teacherEntity.getPersonal_email());
        updateTeacher.setTeacher_password(teacherEntity.getTeacher_password());
        updateTeacher.setPersonal_phone(teacherEntity.getPersonal_phone());
        updateTeacher.setTeacher_area(teacherEntity.getTeacher_area());
        updateTeacher.setTeacher_area(teacherEntity.getTeacher_area());


        return this.teacherRepository.save(updateTeacher);
    }
}
