package org.salon.services;

import org.salon.dao.ReviewDAO;
import org.salon.dao.impl.ReviewDAOImpl;
import org.salon.models.Review;
import org.salon.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class ReviewService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    public Review getReview(long id) {
        try (Connection con = ConnectionPool.getConnection()){
            ReviewDAO reviewDAO = new ReviewDAOImpl(con);
            try {
                return reviewDAO.get(id);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public Review getReviewByAppointmentId(long appointmentId) {
        try (Connection con = ConnectionPool.getConnection()){
            ReviewDAO reviewDAO = new ReviewDAOImpl(con);
            try {
                return reviewDAO.getByAppointmentId(appointmentId);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public List<Review> getAllReviews() {
        try (Connection con = ConnectionPool.getConnection()){
            ReviewDAO reviewDAO = new ReviewDAOImpl(con);
            try {
                return reviewDAO.getAll();
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }

    public Review createReview(Review review){
        try (Connection con = ConnectionPool.getConnection()){
            ReviewDAO reviewDAO = new ReviewDAOImpl(con);
            try {
                con.setAutoCommit(false);
                if (reviewDAO.create(review) != null) {
                    con.commit();
                    return review;
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

    public void updateReview(Review review){
        try (Connection con = ConnectionPool.getConnection()){
            ReviewDAO reviewDAO = new ReviewDAOImpl(con);
            try {
                con.setAutoCommit(false);
                reviewDAO.update(review);
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

    public boolean deleteReview(long id){
        try (Connection con = ConnectionPool.getConnection()){
            ReviewDAO reviewDAO = new ReviewDAOImpl(con);
            try {
                con.setAutoCommit(false);
                reviewDAO.delete(id);
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
