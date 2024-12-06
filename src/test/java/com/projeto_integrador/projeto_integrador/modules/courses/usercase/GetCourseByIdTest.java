package com.projeto_integrador.projeto_integrador.modules.courses.usercase;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List; // Adicione esta linha
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.GetCourseById;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;

import jakarta.persistence.EntityNotFoundException;

class GetCourseByIdTest {

    @InjectMocks
    private GetCourseById getCourseById;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_CourseExists_ReturnsCourseDetails() {
        // Arrange
        Long courseId = 1L;
        CourseEntity course = new CourseEntity();
        course.setCourseId(courseId);
        course.setCourseName("Course 1");
        course.setCourseSemester("1");
        course.setCoursePeriod("Morning");
        course.setCourseSubjects(List.of(1L, 2L)); // Usa List.of para criar a lista

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        SubjectEntity subject1 = new SubjectEntity();
        subject1.setSubjectId(1L);
        subject1.setSubjectName("Subject 1");

        SubjectEntity subject2 = new SubjectEntity();
        subject2.setSubjectId(2L);
        subject2.setSubjectName("Subject 2");

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject1));
        when(subjectRepository.findById(2L)).thenReturn(Optional.of(subject2));

        // Act
        Map<String, Object> result = getCourseById.execute(courseId);

        // Assert
        assertEquals(courseId, result.get("courseId"));
        assertEquals("Course 1", result.get("courseName"));
        assertEquals("1", result.get("courseSemester"));
        assertEquals("Morning", result.get("coursePeriod"));
        assertEquals(List.of("Subject 1", "Subject 2"), result.get("subjects"));
    }

    @Test
    void testExecute_CourseDoesNotExist_ThrowsException() {
        // Arrange
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> getCourseById.execute(courseId));
    }
}
