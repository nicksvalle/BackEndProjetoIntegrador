package com.projeto_integrador.projeto_integrador.modules.reservation.usecases;


import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.reservation.entity.ReservationEntity;
import com.projeto_integrador.projeto_integrador.modules.reservation.repository.ReservationRepository;
import com.projeto_integrador.projeto_integrador.modules.schedule.usecases.FKValidation;

@Service
public class CreateReservation {
    
    @Autowired
    ReservationRepository repository;

    @Autowired
    private FKValidation fkValidation;

    @Autowired
    private ReservationValidation reservationValidation;

    public ReservationEntity execute(ReservationEntity reservationEntity){

        Long subjectId = reservationEntity.getSubject();
        fkValidation.validateSubjectExist(subjectId);

        Long timeId = reservationEntity.getTime();
        fkValidation.validateTimeExist(timeId);

        Long teacherId = reservationEntity.getTeacher();
        fkValidation.validateTeacherExist(teacherId);

        Long roomId = reservationEntity.getRoom();
        fkValidation.validateRoomExist(roomId);

        Long courseId = reservationEntity.getCourse();
        fkValidation.validateCourseExist(courseId);

        Date date = reservationEntity.getDate();

        LocalDate localDate = date.toLocalDate();

        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        String dayName = dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, new Locale("pt", "BR"));

        reservationValidation.searchConflictReservations(dayName, roomId, timeId);
        
        return this.repository.save(reservationEntity);
    }
}
