package com.projeto_integrador.projeto_integrador.modules.teacher.usercase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.exceptions.UserFoundException;
import com.projeto_integrador.projeto_integrador.modules.subjects.SubjectValidation;

import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;

@Service
public class CreateTeacher {
    
    @Autowired
    TeacherRepository teacherRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SubjectValidation subjectValidation;

    /**
     * Método responsável por criar um novo professor no sistema.
     * 
     * @param teacherEntity objeto que contém as informações do professor a ser criado
     * @return o professor criado
     * @throws UserFoundException caso o e-mail institucional já exista no sistema
     */
    public TeacherEntity execute(TeacherEntity teacherEntity){
        // Verificar se já existe um professor com o mesmo e-mail institucional
        this.teacherRepository.findByInstitutionalEmail(teacherEntity.getInstitutionalEmail())
            .ifPresent(user -> {
                throw new UserFoundException();
        });

        // Validar se todas as disciplinas associadas ao professor existem no sistema
        List<Long> subjectIds = teacherEntity.getTeacherSubjects();
        subjectValidation.validateSubjectsExist(subjectIds);

        // Codificar a senha do professor antes de salvar no banco
        var password = passwordEncoder.encode(teacherEntity.getTeacherPassword());
        teacherEntity.setTeacherPassword(password);

        // Salvar e retornar o professor criado
        return this.teacherRepository.save(teacherEntity);
    }
}
