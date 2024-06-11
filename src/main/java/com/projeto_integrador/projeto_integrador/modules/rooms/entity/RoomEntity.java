package com.projeto_integrador.projeto_integrador.modules.rooms.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(of = "roomId")
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "rooms")
public class RoomEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @NotBlank
    @Length(max = 11, message = "o máximo de caracteres do campo [roomCapacity] são 11")
    @Column(name = "room_capacity")
    private String roomCapacity;

    @NotBlank
    @Length(max = 100, message = "o máximo de caracteres do campo [roomFloor] são 100")
    @Column(name = "room_floor")
    private String roomFloor;

    @NotBlank
    @Length(max = 200, message = "o máximo de caracteres do campo [roomResources] são 200")
    @Column(name = "room_resources")
    private String roomResources;

    @NotBlank
    @Length(max = 1, message = "o máximo de caracteres do campo [roomAvailability] são 1")
    @Column(name = "room_availability")
    private String roomAvailability;

    @CreationTimestamp
    private LocalDateTime create_at;
    
    @UpdateTimestamp
    private LocalDateTime update_at;
    

}
