package com.projeto_integrador.projeto_integrador.modules.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_integrador.projeto_integrador.modules.student.entity.StudentEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, Long>{
    Optional<TeacherEntity> findByInstitutionalEmail(String institutionalEmail);
}
