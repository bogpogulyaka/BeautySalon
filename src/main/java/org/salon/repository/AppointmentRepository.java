package org.salon.repository;

import org.salon.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.salon.models.Appointment;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT * FROM appointment WHERE client_id = ?1")
    List<Appointment> findByClientId(long id);
    @Query("SELECT * FROM appointment WHERE employee_id = ?1")
    List<Appointment> findByEmployeeId(long id);
    @Query("SELECT * FROM appointment WHERE status = ?1")
    List<Appointment> findByStatus(Appointment.Status status);
}
