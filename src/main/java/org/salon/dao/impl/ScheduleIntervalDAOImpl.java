package org.salon.dao.impl;

import jakarta.persistence.EntityManager;
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
    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(ScheduleIntervalDAOImpl.class);
    public ScheduleIntervalDAOImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public ScheduleInterval get(long id) {
        return em.find(ScheduleInterval.class, id);
    }

    @Override
    public List<ScheduleInterval> getAll() {
        return em.createQuery("SELECT * FROM schedule_interval", ScheduleInterval.class).getResultList();
    }

    @Override
    public ScheduleInterval create(ScheduleInterval scheduleInterval) {
        em.persist(scheduleInterval);
        return scheduleInterval;
    }

    @Override
    public void update(ScheduleInterval scheduleInterval) {
        em.merge(scheduleInterval);
    }

    @Override
    public void delete(long id) {
        em.remove(em.find(ScheduleInterval.class, id));
    }
}
