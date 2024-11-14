package com.projeto_integrador.projeto_integrador.modules.teacher.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.teacher.dto.ProfileTeacherResponseDTO;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;

@Service
public class ProfileTeacherUseCase {
    
    @Autowired
    private TeacherRepository teacherRepository;

    public ProfileTeacherResponseDTO execute(Long teacherId) {
        var teacher = this.teacherRepository.findById(teacherId)
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("User not found");
            });

        var teacherDTO = ProfileTeacherResponseDTO.builder()
            .teacherName(teacher.getTeacherName())
            .teacherArea(teacher.getTeacherArea())
            .teacherSubjects(teacher.getTeacherSubjects())
            .teacherId(teacher.getTeacherId())
            .institutionalEmail(teacher.getInstitutionalEmail())
            .businessPhone(teacher.getBusinessPhone())
            .personalEmail(teacher.getPersonalEmail())
            .personalPhone(teacher.getPersonalPhone())
            .researchLine(teacher.getResearchLine())
            .build();
        
        return teacherDTO;
    }
}
