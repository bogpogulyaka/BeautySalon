package org.salon.services;

import org.salon.models.ScheduleInterval;
import org.salon.repository.ScheduleIntervalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleIntervalService {
    private final ScheduleIntervalRepository scheduleIntervalRepository;
    private static final Logger logger = LoggerFactory.getLogger(ScheduleIntervalService.class);

    @Autowired
    public ScheduleIntervalService(ScheduleIntervalRepository scheduleIntervalRepository) {
        this.scheduleIntervalRepository = scheduleIntervalRepository;
    }
    public ScheduleInterval getScheduleInterval(long id) {
        return scheduleIntervalRepository.findById(id).orElse(null);
    }
    public List<ScheduleInterval> getAllScheduleIntervals() {
        return scheduleIntervalRepository.findAll();
    }
    public List<ScheduleInterval> getScheduleIntervalsByEmployeeId(long id) {
        return scheduleIntervalRepository.findByEmployeeId(id);
    }
    public ScheduleInterval createScheduleInterval(ScheduleInterval scheduleInterval){
        return scheduleIntervalRepository.save(scheduleInterval);
    }
    public void updateScheduleInterval(ScheduleInterval scheduleInterval){
        scheduleIntervalRepository.save(scheduleInterval);
    }
    public void deleteScheduleInterval(long id){
        scheduleIntervalRepository.deleteById(id);
    }
}
