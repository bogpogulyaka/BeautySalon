package org.salon.dao.impl;

import org.salon.dao.ScheduleIntervalDAO;
import org.salon.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleIntervalDAOImpl implements ScheduleIntervalDAO {
    private static final String SQL_SELECT_SCHEDULE_INTERVAL_BY_ID = "SELECT * FROM schedule_interval WHERE id = ?";
    private static final String SQL_SELECT_ALL_SCHEDULE_INTERVALS = "SELECT * FROM schedule_interval";
    private static final String SQL_SELECT_ALL_SCHEDULE_INTERVALS_BY_EMPLOYEE_ID = "SELECT * FROM schedule_interval WHERE employee_id = ?";
    private static final String SQL_ADD_SCHEDULE_INTERVAL = "INSERT INTO schedule_interval " +
            "(employee_id, start_datetime, finish_datetime) VALUES(?, ?, ?)";
    private static final String SQL_UPDATE_SCHEDULE_INTERVAL = "UPDATE schedule_interval SET " +
            "employee_id = ?, start_datetime = ?, finish_datetime = ?";
    private static final String SQL_DELETE_SCHEDULE_INTERVAL = "DELETE FROM schedule_interval WHERE id = ?";


    private final Connection con;
    private static final Logger logger = LoggerFactory.getLogger(ReviewDAOImpl.class);
    public ScheduleIntervalDAOImpl(Connection con){
        this.con = con;
    }


    private ScheduleInterval getScheduleInterval(ResultSet rs){
        try {
            long id = rs.getLong("id");
            long employeeId = rs.getLong("employee_id");
            LocalDateTime startDateTime = rs.getObject("start_datetime", LocalDateTime.class);
            LocalDateTime finishDateTime = rs.getObject("finish_datetime", LocalDateTime.class);

            User employee = new UserDAOImpl(con).get(employeeId);

            return new ScheduleInterval(id, employee, startDateTime, finishDateTime);
        }
        catch (SQLException ex) {
            logger.error("Error. Can't get schedule interval." + ex.getMessage());
        }
        return null;
    }

    private void setScheduleInterval(PreparedStatement stmt, ScheduleInterval interval) throws SQLException{
        stmt.setLong(1, interval.getEmployee().getId());
        stmt.setTimestamp(2, Timestamp.valueOf(interval.getStartDateTime()));
        stmt.setTimestamp(3, Timestamp.valueOf(interval.getFinishDateTime()));
    }
    
    @Override
    public ScheduleInterval get(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_SCHEDULE_INTERVAL_BY_ID)) {
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getScheduleInterval(rs);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't find interval by id " + id + ". " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<ScheduleInterval> getAll() {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_SCHEDULE_INTERVALS)) {
            List<ScheduleInterval> intervals = new ArrayList<>();
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ScheduleInterval interval = getScheduleInterval(rs);
                    intervals.add(interval);
                }

                return intervals;
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't get all intervals. "+ ex.getMessage());
        }
        return null;
    }

    @Override
    public ScheduleInterval create(ScheduleInterval scheduleInterval) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_SCHEDULE_INTERVAL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setScheduleInterval(stmt, scheduleInterval);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()){
                if (rs.next()) {
                    scheduleInterval.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't add new interval. " + ex.getMessage());
        }
        return scheduleInterval;
    }

    @Override
    public void update(ScheduleInterval scheduleInterval) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_SCHEDULE_INTERVAL)) {
            stmt.setLong(1, scheduleInterval.getId());
            setScheduleInterval(stmt, scheduleInterval);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't update interval. " + ex.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_SCHEDULE_INTERVAL)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't delete interval with id " + id + ". " + ex.getMessage());
        }
    }

    @Override
    public List<ScheduleInterval> getByEmployeeId(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_SCHEDULE_INTERVALS_BY_EMPLOYEE_ID)) {
            stmt.setLong(1, id);
            List<ScheduleInterval> intervals = new ArrayList<>();
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ScheduleInterval interval = getScheduleInterval(rs);
                    intervals.add(interval);
                }

                return intervals;
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't get all intervals. "+ ex.getMessage());
        }
        return null;
    }
}
