package com.projeto_integrador.projeto_integrador.modules.reservation.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto_integrador.projeto_integrador.modules.reservation.entity.ReservationEntity;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long>{
    List<ReservationEntity> findByDateAndRoom(LocalDate date, Long roomId);
}