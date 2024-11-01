package com.projeto_integrador.projeto_integrador.modules.schedule.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto_integrador.projeto_integrador.modules.schedule.entity.ScheduleEntity;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    Optional<ScheduleEntity> findByWeekDayAndTimeAndRoom(String weekDay, Long time, Long roomId);

}
