package org.salon.dao;

import org.salon.models.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentDAO extends DAO<Appointment>  {
    List<Appointment> getByClientId(long id);
    List<Appointment> getByEmployeeId(long id);
    List<Appointment> getByStatus(Appointment.Status status);
    List<Appointment> getByTimeInterval(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
