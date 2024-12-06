package com.projeto_integrador.projeto_integrador.modules.schedule.usercase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.projeto_integrador.projeto_integrador.modules.schedule.ScheduleValidation;
import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.PutScheduleById;

import jakarta.persistence.EntityNotFoundException;

class PutScheduleByIdTest {

    @InjectMocks
    private PutScheduleById putScheduleById;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ScheduleValidation scheduleValidation;

    @Mock
    private ScheduleEntity existingSchedule;

    @BeforeEach
    void setUp() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_Success() {
        // Dados de entrada
        Long id = 1L;
        ScheduleEntity updatedSchedule = new ScheduleEntity();
        updatedSchedule.setTime(2L);
        updatedSchedule.setTeacher(3L);
        updatedSchedule.setSubject(4L);
        updatedSchedule.setRoom(5L);
        updatedSchedule.setCourse(6L);

        // Simula o comportamento do repositório e a validação
        when(scheduleRepository.findById(id)).thenReturn(java.util.Optional.of(existingSchedule));
        when(scheduleRepository.save(any(ScheduleEntity.class))).thenReturn(updatedSchedule);

        // Simula as validações
        doNothing().when(scheduleValidation).validateSubjectExist(updatedSchedule.getSubject());
        doNothing().when(scheduleValidation).validateTimeExist(updatedSchedule.getTime());
        doNothing().when(scheduleValidation).validateTeacherExist(updatedSchedule.getTeacher());
        doNothing().when(scheduleValidation).validateRoomExist(updatedSchedule.getRoom());
        doNothing().when(scheduleValidation).validateCourseExist(updatedSchedule.getCourse());

        // Chama o método
        ScheduleEntity result = putScheduleById.execute(id, updatedSchedule);

        // Verifica se o método save foi chamado
        verify(scheduleRepository, times(1)).save(any(ScheduleEntity.class));

        // Verifica o resultado
        assertEquals(updatedSchedule.getTime(), result.getTime());
        assertEquals(updatedSchedule.getTeacher(), result.getTeacher());
        assertEquals(updatedSchedule.getSubject(), result.getSubject());
        assertEquals(updatedSchedule.getRoom(), result.getRoom());
        assertEquals(updatedSchedule.getCourse(), result.getCourse());
    }

    @Test
    void testExecute_ScheduleNotFound() {
        Long id = 1L;
        ScheduleEntity updatedSchedule = new ScheduleEntity();

        // Simula que o schedule não existe
        when(scheduleRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Verifica se a exceção é lançada
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            putScheduleById.execute(id, updatedSchedule);
        });

        // Verifica se a mensagem da exceção está correta
        assertEquals("Schedule not found with id: " + id, exception.getMessage());

        // Verifica se o save não foi chamado
        verify(scheduleRepository, never()).save(any(ScheduleEntity.class));
    }

    @Test
    void testExecute_ValidationFail() {
        Long id = 1L;
        ScheduleEntity updatedSchedule = new ScheduleEntity();
        updatedSchedule.setSubject(4L);
        updatedSchedule.setTime(2L);
        updatedSchedule.setTeacher(3L);
        updatedSchedule.setRoom(5L);
        updatedSchedule.setCourse(6L);

        // Simula que o schedule existe
        when(scheduleRepository.findById(id)).thenReturn(java.util.Optional.of(existingSchedule));

        // Simula falha na validação de Subject
        doThrow(new IllegalArgumentException("Invalid Subject")).when(scheduleValidation).validateSubjectExist(updatedSchedule.getSubject());

        // Verifica se a exceção de validação é lançada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            putScheduleById.execute(id, updatedSchedule);
        });

        // Verifica a mensagem da exceção
        assertEquals("Invalid Subject", exception.getMessage());

        // Verifica se o save não foi chamado
        verify(scheduleRepository, never()).save(any(ScheduleEntity.class));
    }
}
