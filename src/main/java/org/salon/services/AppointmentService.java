package org.salon.services;

import org.salon.repository.AppointmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.salon.models.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment getAppointment(long id){
        return appointmentRepository.findById(id).orElse(null);
    }
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    public Page<Appointment> getAllAppointments(int page, int pageSize){
        Pageable pageable = PageRequest.of(page-1, pageSize);
        return appointmentRepository.findAll(pageable);
    }
    public Appointment createAppointment(Appointment appointment){
        return appointmentRepository.save(appointment);
    }
    public void updateAppointment(Appointment appointment){
        appointmentRepository.save(appointment);
    }
    public void deleteAppointment(long id){
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getAppointmentsByClientId(long clientId) { return appointmentRepository.findByClientId(clientId); }
    public List<Appointment> getAppointmentsByEmployeeId(long employeeId) { return appointmentRepository.findByEmployeeId(employeeId); }
    public List<Appointment> getAppointmentsByStatus(Appointment.Status status) { return appointmentRepository.findByStatus(status); }
}
