package org.salon.dao.impl;

import jakarta.persistence.EntityManager;
import org.salon.dao.ReviewDAO;
import org.salon.models.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements ReviewDAO {
    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(ReviewDAOImpl.class);
    public ReviewDAOImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public Review get(long id){
        return em.find(Review.class, id);
    }

    @Override
    public List<Review> getAll() {
        return em.createQuery("SELECT * FROM review", Review.class).getResultList();
    }

    @Override
    public Review create(Review review) {
        em.persist(review);
        return review;
    }

    @Override
    public void update(Review review) {
        em.merge(review);
    }

    @Override
    public void delete (long id) {
        em.remove(em.find(Review.class, id));
    }
}
