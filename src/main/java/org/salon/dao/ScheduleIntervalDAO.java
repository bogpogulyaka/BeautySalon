package org.salon.dao;

import org.salon.models.ScheduleInterval;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleIntervalDAO extends DAO<ScheduleInterval> {
    List<ScheduleInterval> getByEmployeeId(long id);
}
