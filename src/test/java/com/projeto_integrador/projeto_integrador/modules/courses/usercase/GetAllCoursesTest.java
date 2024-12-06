package com.projeto_integrador.projeto_integrador.modules.courses.usercase;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.GetAllCourses;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;

import jakarta.persistence.EntityNotFoundException;

class GetAllCoursesTest {

    @InjectMocks
    private GetAllCourses getAllCourses;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_CoursesExist_ReturnsCourseList() {
        // Arrange
        CourseEntity course1 = new CourseEntity();
        course1.setCourseId(1L);
        course1.setCourseName("Course 1");
        course1.setCourseSemester("1"); // Alterado para String
        course1.setCoursePeriod("Morning");
        course1.setCourseSubjects(Arrays.asList(1L, 2L));

        CourseEntity course2 = new CourseEntity();
        course2.setCourseId(2L);
        course2.setCourseName("Course 2");
        course2.setCourseSemester("2"); // Alterado para String
        course2.setCoursePeriod("Afternoon");
        course2.setCourseSubjects(Arrays.asList(2L));

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        SubjectEntity subject1 = new SubjectEntity();
        subject1.setSubjectId(1L);
        subject1.setSubjectName("Subject 1");
        
        SubjectEntity subject2 = new SubjectEntity();
        subject2.setSubjectId(2L);
        subject2.setSubjectName("Subject 2");

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject1));
        when(subjectRepository.findById(2L)).thenReturn(Optional.of(subject2));

        // Act
        List<Map<String, Object>> result = getAllCourses.execute();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Course 1", result.get(0).get("courseName"));
        assertEquals("Course 2", result.get(1).get("courseName"));
        assertEquals(Arrays.asList("Subject 1", "Subject 2"), result.get(0).get("subjects"));
        assertEquals(Arrays.asList("Subject 2"), result.get(1).get("subjects"));
    }

    @Test
    void testExecute_NoCourses_ThrowsException() {
        // Arrange
        when(courseRepository.findAll()).thenReturn(List.of()); // Retorna uma lista vazia

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> getAllCourses.execute());
    }
}
