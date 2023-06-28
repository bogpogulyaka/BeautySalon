package org.salon.dao.impl;

import jakarta.persistence.EntityManager;
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
    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(AppointmentDAOImpl.class);
    public AppointmentDAOImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public Appointment get(long id){
        return em.find(Appointment.class, id);
    }

    @Override
    public List<Appointment> getAll() {
        return em.createQuery("SELECT * FROM appointment", Appointment.class).getResultList();
    }

    @Override
    public Appointment create(Appointment appointment) {
        em.persist(appointment);
        return appointment;
    }

    @Override
    public void update(Appointment appointment) {
        em.merge(appointment);
    }

    @Override
    public void delete (long id) {
        em.remove(em.find(Appointment.class, id));
    }

    @Override
    public List<Appointment> getByStatus(Appointment.Status status) {
        return em.createQuery("SELECT * FROM appointment WHERE status = ?", Appointment.class)
                .setParameter(1, status)
                .getResultList();
    }

    @Override
    public List<Appointment> getByTimeInterval(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return null;
    }
}
