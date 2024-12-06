package com.projeto_integrador.projeto_integrador.modules.courses.usercase;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.CreateCourse;
import com.projeto_integrador.projeto_integrador.modules.subjects.SubjectValidation;

class CreateCourseTest {

    @InjectMocks
    private CreateCourse createCourse;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private SubjectValidation subjectValidation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_ValidCourse_Success() {
        // Arrange
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCourseSubjects(Arrays.asList(1L, 2L)); // IDs de exemplo

        // Mock da validação de sujeitos
        doNothing().when(subjectValidation).validateSubjectsExist(courseEntity.getCourseSubjects());

        // Mock do repositório
        when(courseRepository.save(courseEntity)).thenReturn(courseEntity);

        // Act
        CourseEntity result = createCourse.execute(courseEntity);

        // Assert
        verify(subjectValidation).validateSubjectsExist(courseEntity.getCourseSubjects());
        verify(courseRepository).save(courseEntity);
        assertEquals(courseEntity, result); // Verifica se o resultado é igual ao curso enviado
    }

    @Test
    void testExecute_InvalidSubjects_ThrowsException() {
        // Arrange
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCourseSubjects(Arrays.asList(1L, 2L));

        // Mock da validação de sujeitos para lançar exceção
        doThrow(new IllegalArgumentException("Invalid subjects"))
            .when(subjectValidation).validateSubjectsExist(courseEntity.getCourseSubjects());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> createCourse.execute(courseEntity));
        verify(courseRepository, never()).save(courseEntity); // O repositório não deve ser chamado
    }
}
