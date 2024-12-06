package com.projeto_integrador.projeto_integrador.modules.teacher.usercase;

import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.usecases.GetTeacherById;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetTeacherByIdTest {

    @InjectMocks
    private GetTeacherById getTeacherById;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnTeacherWhenFound() {
        TeacherEntity teacher = new TeacherEntity();
        teacher.setTeacherId(1L);
        teacher.setTeacherName("Teacher One");
        teacher.setInstitutionalEmail("teacher1@example.com");
        teacher.setTeacherSubjects(Arrays.asList(1L, 2L));

        SubjectEntity subject1 = new SubjectEntity();
        subject1.setSubjectId(1L);
        subject1.setSubjectName("Mathematics");

        SubjectEntity subject2 = new SubjectEntity();
        subject2.setSubjectId(2L);
        subject2.setSubjectName("Physics");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject1));
        when(subjectRepository.findById(2L)).thenReturn(Optional.of(subject2));

        Map<String, Object> result = getTeacherById.execute(1L);

        assertNotNull(result);
        assertEquals("Teacher One", result.get("teacherName"));
        assertEquals(Arrays.asList("Mathematics", "Physics"), result.get("subjects"));
    }

    @Test
    public void shouldThrowExceptionWhenTeacherNotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> getTeacherById.execute(1L));

        assertEquals("Teacher not found", exception.getMessage());
        verify(teacherRepository, times(1)).findById(1L);
    }
}
