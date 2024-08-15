package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import com.projeto_integrador.projeto_integrador.modules.courses.entity.CourseEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;
import com.projeto_integrador.projeto_integrador.modules.schedule.repository.ScheduleRepository;
import com.projeto_integrador.projeto_integrador.modules.subjects.entity.SubjectEntity;
import com.projeto_integrador.projeto_integrador.modules.subjects.repository.SubjectRepository;
import com.projeto_integrador.projeto_integrador.modules.teacher.entity.TeacherEntity;
import com.projeto_integrador.projeto_integrador.modules.teacher.repository.TeacherRepository;
import com.projeto_integrador.projeto_integrador.modules.time.entity.TimeEntity;
import com.projeto_integrador.projeto_integrador.modules.time.repository.TimeRepository;
import com.projeto_integrador.projeto_integrador.modules.courses.repository.CourseRepository;



@Service
public class GetAllRooms {
    
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomTypeRepository roomTypeRepository;

    public List<Map<String, Object>> execute() {
        var allRooms = roomRepository.findAll();
        if (allRooms.isEmpty()) {
            throw new EntityNotFoundException("Room not Registered");
        }

        return allRooms.stream()
                          .map(this::convertRoomToMap)
                          .collect(Collectors.toList());
    }

    private Map<String, Object> convertRoomToMap(RoomEntity room) {
        Map<String, Object> result = new HashMap<>();
        result.put("roomId", room.getRoomId());
        result.put("roomCapacity", room.getRoomCapacity());
        result.put("roomFloor", room.getRoomFloor());
        result.put("roomResources", room.getRoomResources());
        result.put("roomAvailability", room.getRoomAvailability());

        Long roomTypeId = room.getRoomType();

        Optional<RoomTypeEntity> roomType = roomTypeRepository.findById(roomTypeId);
        String roomTypeName = roomType.map(RoomTypeEntity::getRoomTypeDescription)
                                    .orElse("Unknown Subject");

        result.put("roomType", roomTypeName);
        return result;
    }

}

