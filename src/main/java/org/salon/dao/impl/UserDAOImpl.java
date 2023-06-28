package org.salon.dao.impl;

import jakarta.persistence.EntityManager;
import org.salon.dao.UserDAO;
import org.salon.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    public UserDAOImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public User get(long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return em.createQuery("SELECT * FROM users", User.class).getResultList();
    }

    @Override
    public User getByLogin(String login) {
        return em.createQuery("SELECT * FROM users WHERE login = ?", User.class)
                .setParameter(1, login)
                .getSingleResult();
    }

    @Override
    public User create(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public void delete(long id) {
        em.remove(em.find(User.class, id));
    }
}
