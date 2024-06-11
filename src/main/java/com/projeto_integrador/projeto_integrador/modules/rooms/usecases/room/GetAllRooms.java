package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetAllRooms {
    
    @Autowired
    RoomRepository roomRepository;

    public List<RoomEntity> execute() {
        var allRooms = roomRepository.findAll();
        if (allRooms.isEmpty()) {
            throw new EntityNotFoundException("Room not Register");
        }
        return allRooms;
    }
}

