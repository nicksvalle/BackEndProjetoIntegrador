package com.projeto_integrador.projeto_integrador.modules.courses.usercase;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;
import com.projeto_integrador.projeto_integrador.modules.courses.usecases.PutCourseById;
import com.projeto_integrador.projeto_integrador.modules.subjects.SubjectValidation;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PutCourseByIdTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private SubjectValidation subjectValidation;

    @InjectMocks
    private PutCourseById putCourseById;

    @BeforeEach
    public void setUp() {
        // Mockito cuidará da injeção das dependências
    }

    @Test
    @DisplayName("Should update and return CourseEntity when ID exists")
    public void shouldUpdateCourseWhenIdExists() {
        // Arrange
        Long courseId = 1L;
        CourseEntity existingCourse = new CourseEntity();
        existingCourse.setCourseId(courseId);
        existingCourse.setCourseName("Old Course");
        existingCourse.setCourseSemester("1");
        existingCourse.setCoursePeriod("Morning");
        existingCourse.setCourseSubjects(List.of(1L, 2L));

        CourseEntity updatedCourseData = new CourseEntity();
        updatedCourseData.setCourseName("Updated Course");
        updatedCourseData.setCourseSemester("2");
        updatedCourseData.setCoursePeriod("Afternoon");
        updatedCourseData.setCourseSubjects(List.of(3L, 4L));

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        doNothing().when(subjectValidation).validateSubjectsExist(updatedCourseData.getCourseSubjects());
        when(courseRepository.save(any(CourseEntity.class))).thenReturn(existingCourse);

        // Act
        CourseEntity result = putCourseById.execute(courseId, updatedCourseData);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCourseName()).isEqualTo("Updated Course");
        assertThat(result.getCourseSemester()).isEqualTo("2");
        assertThat(result.getCoursePeriod()).isEqualTo("Afternoon");
        assertThat(result.getCourseSubjects()).isEqualTo(List.of(3L, 4L));

        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(existingCourse);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when ID does not exist")
    public void shouldThrowExceptionWhenIdDoesNotExist() {
        // Arrange
        Long courseId = 1L;
        CourseEntity updatedCourseData = new CourseEntity();
        updatedCourseData.setCourseName("Updated Course");

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> putCourseById.execute(courseId, updatedCourseData))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("Course not found");

        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, never()).save(any(CourseEntity.class)); // Verifica que o método save não foi chamado
    }
}
