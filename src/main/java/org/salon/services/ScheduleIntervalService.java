package org.salon.services;

import org.salon.dao.ScheduleIntervalDAO;
import org.salon.dao.impl.ScheduleIntervalDAOImpl;
import org.salon.models.ScheduleInterval;
import org.salon.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleIntervalService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleIntervalService.class);

    public ScheduleInterval getScheduleInterval(long id){
        try (Connection con = ConnectionPool.getConnection()){
            ScheduleIntervalDAO scheduleIntervalDAO = new ScheduleIntervalDAOImpl(con);
            try {
                return scheduleIntervalDAO.get(id);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public List<ScheduleInterval> getAllScheduleIntervals() {
        try (Connection con = ConnectionPool.getConnection()){
            ScheduleIntervalDAO scheduleIntervalDAO = new ScheduleIntervalDAOImpl(con);
            try {
                return scheduleIntervalDAO.getAll();
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public List<ScheduleInterval> getScheduleIntervalsByEmployeeId(long employeeId) {
        try (Connection con = ConnectionPool.getConnection()){
            ScheduleIntervalDAO scheduleIntervalDAO = new ScheduleIntervalDAOImpl(con);
            try {
                return scheduleIntervalDAO.getByEmployeeId(employeeId);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public ScheduleInterval createScheduleInterval(ScheduleInterval scheduleInterval){
        try (Connection con = ConnectionPool.getConnection()){
            ScheduleIntervalDAO scheduleIntervalDAO = new ScheduleIntervalDAOImpl(con);
            try {
                con.setAutoCommit(false);
                if (scheduleIntervalDAO.create(scheduleInterval) != null) {
                    con.commit();
                    return scheduleInterval;
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

    public void updateScheduleInterval(ScheduleInterval scheduleInterval){
        try (Connection con = ConnectionPool.getConnection()){
            ScheduleIntervalDAO scheduleIntervalDAO = new ScheduleIntervalDAOImpl(con);
            try {
                con.setAutoCommit(false);
                scheduleIntervalDAO.update(scheduleInterval);
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

    public boolean deleteScheduleInterval(long id){
        try (Connection con = ConnectionPool.getConnection()){
            ScheduleIntervalDAO scheduleIntervalDAO = new ScheduleIntervalDAOImpl(con);
            try {
                con.setAutoCommit(false);
                scheduleIntervalDAO.delete(id);
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
