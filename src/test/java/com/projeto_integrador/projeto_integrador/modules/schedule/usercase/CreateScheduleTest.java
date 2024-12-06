package com.projeto_integrador.projeto_integrador.modules.schedule.usercase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projeto_integrador.projeto_integrador.modules.schedule.ScheduleValidation;
import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.CreateSchedule;

@ExtendWith(MockitoExtension.class)
public class CreateScheduleTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ScheduleValidation scheduleValidation;

    @InjectMocks
    private CreateSchedule createSchedule;

    private ScheduleEntity scheduleEntity;

    @BeforeEach
    public void setUp() {
        // Inicializa o objeto ScheduleEntity
        scheduleEntity = new ScheduleEntity();
        // Defina os valores necessários para o ScheduleEntity, por exemplo:
        scheduleEntity.setSubject(1L);
        scheduleEntity.setTime(1L);
        scheduleEntity.setTeacher(1L);
        scheduleEntity.setRoom(1L);
        scheduleEntity.setCourse(1L);
    }

    @Test
    public void testExecute_CreatesSchedule() {
        // Arrange: mockando o comportamento dos métodos de validação
        doNothing().when(scheduleValidation).validateSubjectExist(anyLong());
        doNothing().when(scheduleValidation).validateTimeExist(anyLong());
        doNothing().when(scheduleValidation).validateTeacherExist(anyLong());
        doNothing().when(scheduleValidation).validateRoomExist(anyLong());
        doNothing().when(scheduleValidation).validateCourseExist(anyLong());

        // Arrange: mockando o comportamento do repository
        when(scheduleRepository.save(any(ScheduleEntity.class))).thenReturn(scheduleEntity);

        // Act: chamando o método a ser testado
        ScheduleEntity result = createSchedule.execute(scheduleEntity);

        // Assert: verificando que o repository.save foi chamado corretamente
        verify(scheduleRepository).save(scheduleEntity);

        // Assert: verificando o resultado
        assertNotNull(result);
        assertEquals(scheduleEntity, result);
    }
}

