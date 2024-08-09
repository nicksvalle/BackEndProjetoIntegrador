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

import com.projeto_integrador.projeto_integrador.modules.rooms.entity.RoomTypeEntity;
import com.projeto_integrador.projeto_integrador.modules.rooms.repository.RoomTypeRepository;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.CreateRoomType;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.DeleteRoomTypeById;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.GetAllRoomTypes;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.GetRoomTypeById;
import com.projeto_integrador.projeto_integrador.modules.rooms.usecases.roomtype.PutRoomTypeById;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("roomsType")
public class RoomTypeController {
    @Autowired
    RoomTypeRepository repository;

    @Autowired
    CreateRoomType createRoomType;

    @Autowired
    GetAllRoomTypes getAllRoomTypes;

    @Autowired
    GetRoomTypeById getRoomTypeById;

    @Autowired
    PutRoomTypeById putRoomTypeById;

    @Autowired
    DeleteRoomTypeById deleteRoomTypeById;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody RoomTypeEntity roomTypeEntity) {
        try {
            var result = this.createRoomType.execute(roomTypeEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<RoomTypeEntity>> getAllRoomTypes() {
       try {
            var result = this.getAllRoomTypes.execute();
            return ResponseEntity.ok().body(result);
       } catch (Exception e) {
            throw new EntityNotFoundException("RoomType not Register");
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeEntity> getById(@Valid @PathVariable long id){
       try {
        var room = this.getRoomTypeById.execute(id);
        return ResponseEntity.ok().body(room);
       } catch (Exception e) {
            throw new EntityNotFoundException("RoomType not found");
       }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeEntity> putRoomType(@Valid @RequestBody RoomTypeEntity roomTypeEntity, @PathVariable Long id) {
        try {
            var updatedRoomType = this.putRoomTypeById.execute(id, roomTypeEntity);
            return ResponseEntity.ok().body(updatedRoomType);
        } catch (Exception e) {
            throw new EntityNotFoundException("RoomType not found");
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomType(@Valid @PathVariable Long id) {
        this.deleteRoomTypeById.execute(id);
        return ResponseEntity.ok().build();
    }


}