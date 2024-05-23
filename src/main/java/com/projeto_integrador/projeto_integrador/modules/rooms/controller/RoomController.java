package com.projeto_integrador.projeto_integrador.modules.rooms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomRepository;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.CreateRoom;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.DeleteRoomById;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.GetAllRooms;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.GetRoomById;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.PutRoomById;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("rooms")
public class RoomController {
    
    @Autowired
    RoomRepository repository;

    @Autowired
    CreateRoom createRoom;

    @Autowired
    GetAllRooms getAllRooms;

    @Autowired
    GetRoomById getRoomById;

    @Autowired
    PutRoomById putRoomById;

    @Autowired
    DeleteRoomById deleteRoomById;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody RoomEntity roomEntity) {
        try {
            var result = this.createRoom.execute(roomEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<RoomEntity>> getAllRooms() {
       try {
            var result = this.getAllRooms.execute();
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
            throw new EntityNotFoundException("Room not Register");
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomEntity> getById(@Valid @PathVariable long id){
       try {
        var room = this.getRoomById.execute(id);
        return ResponseEntity.ok().body(room);
       } catch (Exception e) {
            throw new EntityNotFoundException("Room not found");
       }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomEntity> putRoom(@Valid @RequestBody RoomEntity roomEntity, @PathVariable Long id) {
        try {
            var updatedRoom = this.putRoomById.execute(id, roomEntity);
            return ResponseEntity.ok().body(updatedRoom);
        } catch (Exception e) {
            throw new EntityNotFoundException("Room not found");
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@Valid @PathVariable Long id) {
        this.deleteRoomById.execute(id);
        return ResponseEntity.ok().build();
    }


}
