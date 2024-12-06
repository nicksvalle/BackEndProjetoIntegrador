package com.projeto_integrador.projeto_integrador.modules.schedule.usercase;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.GetScheduleById;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GetScheduleByIdTest {

    @InjectMocks
    private GetScheduleById getScheduleById;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TimeRepository timeRepository;

    @Mock
    private CourseRepository courseRepository;

    private ScheduleEntity scheduleEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Simula um ScheduleEntity com dados de exemplo
        scheduleEntity = new ScheduleEntity();
        scheduleEntity.setScheduleId(1L);
        scheduleEntity.setSubject(2L); // Id do Subject
        scheduleEntity.setTeacher(3L); // Id do Teacher
        scheduleEntity.setTime(4L);    // Id do Time
        scheduleEntity.setCourse(5L);  // Id do Course
    }

    @Test
    void testExecute_Success() {
        // Simula o comportamento do repository
        Mockito.when(scheduleRepository.findById(1L)).thenReturn(Optional.of(scheduleEntity));
        
        SubjectEntity subject = new SubjectEntity();
        subject.setSubjectName("Math");
        Mockito.when(subjectRepository.findById(2L)).thenReturn(Optional.of(subject));
        
        TeacherEntity teacher = new TeacherEntity();
        teacher.setTeacherName("John Doe");
        Mockito.when(teacherRepository.findById(3L)).thenReturn(Optional.of(teacher));
        
        TimeEntity time = new TimeEntity();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedStartTime = LocalDateTime.now().format(formatter);
        String formattedEndTime = LocalDateTime.now().plusHours(1).format(formatter);

        time.setStartTime(formattedStartTime);
        time.setEndTime(formattedEndTime);
        time.setWeekDay("Monday");
        Mockito.when(timeRepository.findById(4L)).thenReturn(Optional.of(time));
        
        CourseEntity course = new CourseEntity();
        course.setCourseName("Computer Science");
        course.setCourseSemester("Fall");
        Mockito.when(courseRepository.findById(5L)).thenReturn(Optional.of(course));

        // Executa o método
        Map<String, Object> result = getScheduleById.execute(1L);

        // Verifica se os dados foram retornados corretamente
        assertNotNull(result);
        assertEquals("Math", result.get("subject"));
        assertEquals("John Doe", result.get("teacher"));
        assertNotNull(result.get("time"));
        assertEquals("Computer Science - Fall", result.get("course"));
    }

    @Test
    void testExecute_ScheduleNotFound() {
        // Simula que o Schedule não foi encontrado
        Mockito.when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica se a exceção é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            getScheduleById.execute(1L);
        });

        assertEquals("Schedule not found", exception.getMessage());
    }
}
