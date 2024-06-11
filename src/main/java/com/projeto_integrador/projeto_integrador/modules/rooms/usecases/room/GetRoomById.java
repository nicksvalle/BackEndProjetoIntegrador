package com.projeto_integrador.projeto_integrador.modules.rooms.usecases.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GetRoomById {
    
    @Autowired
    RoomRepository repository;
    
    public RoomEntity execute(@PathVariable Long id) {
        return this.repository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Room not found")
        );
    }
}
