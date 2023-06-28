package org.salon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.salon.dao.AppointmentDAO;
import org.salon.util.ConnectionPool;
import org.salon.dao.impl.AppointmentDAOImpl;
import org.salon.models.Appointment;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentService {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    public Appointment getAppointment(long id){
        try (Connection con = ConnectionPool.getConnection()){
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl(con);
            try {
                return appointmentDAO.get(id);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public List<Appointment> getAllAppointments() {
        try (Connection con = ConnectionPool.getConnection()){
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl(con);
            try {
                return appointmentDAO.getAll();
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public List<Appointment> getAppointmentsByClientId(long clientId) {
        try (Connection con = ConnectionPool.getConnection()){
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl(con);
            try {
                return appointmentDAO.getByClientId(clientId);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public List<Appointment> getAppointmentsByEmployeeId(long employeeId) {
        try (Connection con = ConnectionPool.getConnection()){
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl(con);
            try {
                return appointmentDAO.getByEmployeeId(employeeId);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public List<Appointment> getAppointmentsByStatus(Appointment.Status status) {
        try (Connection con = ConnectionPool.getConnection()){
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl(con);
            try {
                return appointmentDAO.getByStatus(status);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public List<Appointment> getAppointmentsTimeInterval(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        try (Connection con = ConnectionPool.getConnection()){
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl(con);
            try {
                return appointmentDAO.getByTimeInterval(startDateTime, endDateTime);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public Appointment createAppointment(Appointment appointment){
        try (Connection con = ConnectionPool.getConnection()){
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl(con);
            try {
                con.setAutoCommit(false);
                if (appointmentDAO.create(appointment) != null) {
                    con.commit();
                    return appointment;
                }
            } catch (Exception ex) {
                try {
                    con.rollback();
                } catch (Exception e){
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public void updateAppointment(Appointment appointment){
        try (Connection con = ConnectionPool.getConnection()){
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl(con);
            try {
                con.setAutoCommit(false);
                appointmentDAO.update(appointment);
                con.commit();
            } catch (Exception ex) {
                try {
                    con.rollback();
                } catch (Exception e){
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
    }

    public boolean deleteAppointment(long id){
        try (Connection con = ConnectionPool.getConnection()){
            AppointmentDAO appointmentDAO = new AppointmentDAOImpl(con);
            try {
                con.setAutoCommit(false);
                appointmentDAO.delete(id);
                con.commit();
                return true;
            } catch (Exception ex){
                try {
                    con.rollback();
                } catch (Exception e){
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }

        return false;
    }
}
