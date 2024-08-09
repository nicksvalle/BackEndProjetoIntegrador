package com.projeto_integrador.projeto_integrador.modules.schedule;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;

@Service
public class Validation {
    @Autowired
    private TimeRepository timeRepository;

    @Autowired 
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private RoomRepository roomRepository;

    public void validateTimeExist(Long timeId) {
        if (timeId != null) {
            Optional<TimeEntity> time = timeRepository.findById(timeId);
            if (time.isEmpty()) {
                throw new RuntimeException("Time not found with ID: " + timeId);
            }
        }
    }

    public void validateTeacherExist(Long teacherId) {
        if (teacherId != null) {
            Optional<TeacherEntity> teacher = teacherRepository.findById(teacherId);
            if (teacher.isEmpty()) {
                throw new RuntimeException("Teacher not found with ID: " + teacherId);
            }
        }
    }

    public void validateSubjectExist(Long subjectId) {
        if (subjectId != null) {
            Optional<SubjectEntity> subject = subjectRepository.findById(subjectId);
            if (subject.isEmpty()) {
                throw new RuntimeException("Subject not found with ID: " + subjectId);
            }
        }
    }

    public void validateRoomExist(Long roomId) {
        if (roomId != null) {
            Optional<RoomEntity> room = roomRepository.findById(roomId);
            if (room.isEmpty()) {
                throw new RuntimeException("Room not found with ID: " + roomId);
            }
        }
    }
}
