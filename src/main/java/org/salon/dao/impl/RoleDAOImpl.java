package org.salon.dao.impl;

import jakarta.persistence.EntityManager;
import org.salon.dao.RoleDAO;
import org.salon.models.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {
    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(RoleDAOImpl.class);
    public RoleDAOImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public Role get(long id) {
        return em.find(Role.class, id);
    }

    @Override
    public Role getByName(String name){
        return em.createQuery("SELECT * FROM roles WHERE name = ?", Role.class)
                .setParameter(1, name)
                .getSingleResult();
    }

    @Override
    public List<Role> getAll() {
        return em.createQuery("SELECT * FROM roles", Role.class).getResultList();
    }

    @Override
    public Role create(Role role) {
        em.persist(role);
        return role;
    }

    @Override
    public void update(Role role) {
        em.merge(role);
    }

    @Override
    public void delete(long id) {
        em.remove(em.find(Role.class, id));
    }
}
