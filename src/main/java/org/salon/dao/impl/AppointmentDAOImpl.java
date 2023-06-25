package org.salon.dao.impl;

import org.salon.dao.AppointmentDAO;
import org.salon.models.Appointment;
import org.salon.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAOImpl implements AppointmentDAO {

    private static final String SQL_SELECT_APPOINTMENT_BY_ID = "SELECT * FROM appointment WHERE id = ?";
    private static final String SQL_SELECT_ALL_APPOINTMENTS = "SELECT * FROM appointment";
    private static final String SQL_SELECT_APPOINTMENT_BY_CLIENT_ID = "SELECT * FROM appointment WHERE client_id = ?";
    private static final String SQL_SELECT_APPOINTMENT_BY_EMPLOYEE_ID = "SELECT * FROM appointment WHERE employee_id = ?";
//    private static final String SQL_SELECT_APPOINTMENT_BY_TIME_INTERVAL = "SELECT * FROM appointment WHERE start_datetime >= ? AND ";
    private static final String SQL_ADD_APPOINTMENT = "INSERT INTO appointment + " +
            "(client_id, employee_id, start_datetime, finish_datetime, status) VALUES(?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_APPOINTMENT = "UPDATE appointment SET " +
            "client_id = ?, employee_id = ?, start_datetime = ?, finish_datetime = ?, status = ?, WHERE id = ?";
    private static final String SQL_DELETE_APPOINTMENT = "DELETE FROM appointment WHERE id = ?";

    private final Connection con;
    private static final Logger logger = LoggerFactory.getLogger(AppointmentDAOImpl.class);
    public AppointmentDAOImpl(Connection con){
        this.con = con;
    }


    private Appointment getAppointment(ResultSet rs){
        try {
            long id = rs.getLong("id");
            long clientId = rs.getLong("client_id");
            long employeeId = rs.getLong("employee_id");
            LocalDateTime startDateTime = rs.getObject("start_datetime", LocalDateTime.class);
            LocalDateTime finishDateTime = rs.getObject("end_datetime", LocalDateTime.class);
            var status = Appointment.Status.valueOf(rs.getString("status"));

            User client = new UserDAOImpl(con).get(clientId);
            User employee = new UserDAOImpl(con).get(employeeId);

            return new Appointment(id, client, employee, startDateTime, finishDateTime, status);
        } catch (SQLException ex) {
            logger.error("Error. Can't get Appointment." + ex.getMessage());
        }
        return null;
    }

    private void setAppointment(PreparedStatement stmt, Appointment appointment) throws SQLException {
        stmt.setLong(1, appointment.getClient().getId());
        stmt.setLong(2, appointment.getEmployee().getId());
        stmt.setTimestamp(3, Timestamp.valueOf(appointment.getStartDateTime()));
        stmt.setTimestamp(4, Timestamp.valueOf(appointment.getFinishDateTime()));
        stmt.setString(5, appointment.getStatus().toString());
    }

    @Override
    public Appointment get(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_APPOINTMENT_BY_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    return getAppointment(rs);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't find appointment by id " + id + ". " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Appointment> getAll() {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_APPOINTMENTS)) {
            List<Appointment> appointments = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    Appointment appointment = getAppointment(rs);
                    appointments.add(appointment);
                }
                return appointments;
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't get all appointments. " + ex.getMessage());
        }
        return null;
    }

    @Override
    public Appointment create(Appointment appointment) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_APPOINTMENT, PreparedStatement.RETURN_GENERATED_KEYS)){
            setAppointment(stmt, appointment);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    appointment.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't add new appointment. " + ex.getMessage());
        }
        return appointment;
    }

    @Override
    public void update(Appointment appointment) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_APPOINTMENT)){
            stmt.setLong(6, appointment.getId());
            setAppointment(stmt, appointment);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't update appointment. " + ex.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_APPOINTMENT)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't delete appointment. " + ex.getMessage());
        }
    }

    @Override
    public List<Appointment> getByClientId(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_APPOINTMENT_BY_CLIENT_ID)) {
            stmt.setLong(1, id);
            List<Appointment> appointments = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    Appointment appointment = getAppointment(rs);
                    appointments.add(appointment);
                }
                return appointments;
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't get appointments. " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Appointment> getByEmployeeId(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_APPOINTMENT_BY_EMPLOYEE_ID)) {
            stmt.setLong(1, id);
            List<Appointment> appointments = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    Appointment appointment = getAppointment(rs);
                    appointments.add(appointment);
                }
                return appointments;
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't get appointments. " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Appointment> getByStatus(Appointment.Status status) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_APPOINTMENT_BY_EMPLOYEE_ID)) {
            stmt.setString(1, status.toString());
            List<Appointment> appointments = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    Appointment appointment = getAppointment(rs);
                    appointments.add(appointment);
                }
                return appointments;
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't get appointments. " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Appointment> getByTimeInterval(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return null;
    }
}
