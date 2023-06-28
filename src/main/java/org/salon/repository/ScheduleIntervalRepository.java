package org.salon.repository;

import org.salon.models.Appointment;
import org.salon.models.ScheduleInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleIntervalRepository extends JpaRepository<ScheduleInterval, Long> {
    @Query("SELECT * FROM schedule_interval WHERE employee_id = ?1")
    List<ScheduleInterval> findByEmployeeId(long id);
}
