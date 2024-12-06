package com.projeto_integrador.projeto_integrador.modules.teacher.usercase;

import com.projeto_integrador.projeto_integrador.modules.subjects.SubjectValidation;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.PutTeacherById;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PutTeacherByIdTest {

    @InjectMocks
    private PutTeacherById putTeacherById;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SubjectValidation subjectValidation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldUpdateTeacherSuccessfully() {
        TeacherEntity existingTeacher = new TeacherEntity();
        existingTeacher.setTeacherId(1L);
        existingTeacher.setTeacherName("Old Name");

        TeacherEntity updatedTeacher = new TeacherEntity();
        updatedTeacher.setTeacherName("New Name");
        updatedTeacher.setTeacherSubjects(Arrays.asList(1L, 2L));

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(existingTeacher));
        when(teacherRepository.save(existingTeacher)).thenReturn(existingTeacher);

        putTeacherById.execute(1L, updatedTeacher);

        verify(subjectValidation, times(1)).validateSubjectsExist(Arrays.asList(1L, 2L));
        verify(teacherRepository, times(1)).save(existingTeacher);

        assertEquals("New Name", existingTeacher.getTeacherName());
        assertEquals(Arrays.asList(1L, 2L), existingTeacher.getTeacherSubjects());
    }

    @Test
    public void shouldThrowExceptionWhenTeacherNotFound() {
        TeacherEntity updatedTeacher = new TeacherEntity();

        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, 
                                           () -> putTeacherById.execute(1L, updatedTeacher));

        assertEquals("Teacher not found", exception.getMessage());
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherRepository, never()).save(any());
    }

    @Test
    public void shouldThrowExceptionWhenSubjectValidationFails() {
        TeacherEntity existingTeacher = new TeacherEntity();
        existingTeacher.setTeacherId(1L);

        TeacherEntity updatedTeacher = new TeacherEntity();
        updatedTeacher.setTeacherSubjects(Arrays.asList(1L, 2L));

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(existingTeacher));
        doThrow(new RuntimeException("Invalid subjects")).when(subjectValidation)
                                                         .validateSubjectsExist(anyList());

        Exception exception = assertThrows(RuntimeException.class, 
                                           () -> putTeacherById.execute(1L, updatedTeacher));

        assertEquals("Invalid subjects", exception.getMessage());
        verify(subjectValidation, times(1)).validateSubjectsExist(Arrays.asList(1L, 2L));
    }
}
