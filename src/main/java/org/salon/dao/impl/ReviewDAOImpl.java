package org.salon.dao.impl;

import org.salon.dao.ReviewDAO;
import org.salon.models.Appointment;
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

    private static final String SQL_SELECT_REVIEW_BY_ID = "SELECT * FROM review WHERE id = ?";
    private static final String SQL_SELECT_ALL_REVIEWS = "SELECT * FROM review";
    private static final String SQL_SELECT_REVIEW_BY_APPOINTMENT_ID = "SELECT * FROM review WHERE id = ?";
    private static final String SQL_ADD_REVIEW = "INSERT INTO review + " +
            "(appointment_id, content) VALUES(?, ?)";
    private static final String SQL_UPDATE_REVIEW = "UPDATE review SET " +
            "appointment_id = ?, content = ?";
    private static final String SQL_DELETE_REVIEW = "DELETE FROM review WHERE id = ?";

    private final Connection con;
    private static final Logger logger = LoggerFactory.getLogger(ReviewDAOImpl.class);
    public ReviewDAOImpl(Connection con){
        this.con = con;
    }


    private Review getReview(ResultSet rs){
        try {
            long id = rs.getLong("id");
            long appointmentId = rs.getLong("appointment_id");
            String content = rs.getString("content");

            Appointment appointment = new AppointmentDAOImpl(con).get(appointmentId);

            return new Review(id, appointment, content);
        }
        catch (SQLException ex) {
            logger.error("Error. Can't get review." + ex.getMessage());
        }
        return null;
    }

    private void setReview(PreparedStatement stmt, Review review) throws SQLException{
        stmt.setLong(1, review.getAppointment().getId());
        stmt.setString(2, review.getContent());
    }

    @Override
    public Review get(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_REVIEW_BY_ID)) {
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getReview(rs);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't find review by id " + id + ". " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Review> getAll() {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_REVIEWS)) {
            List<Review> reviews = new ArrayList<>();
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Review review = getReview(rs);
                    reviews.add(review);
                }

                return reviews;
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't get all reviews."+ ex.getMessage());
        }
        return null;
    }

    @Override
    public Review create(Review review) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_REVIEW, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setReview(stmt, review);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()){
                if (rs.next()) {
                    review.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't add new review " + review.getContent() + ". " + ex.getMessage());
        }
        return review;
    }

    @Override
    public void update(Review review) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_REVIEW)) {
            stmt.setLong(1, review.getId());
            setReview(stmt, review);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't update review " + review.getContent() + ". " + ex.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_REVIEW)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't delete review with id " + id + ". " + ex.getMessage());
        }
    }

    @Override
    public Review getByAppointmentId(int id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_REVIEW_BY_APPOINTMENT_ID)) {
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getReview(rs);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't find review by appointment id " + id + ". " + ex.getMessage());
        }
        return null;
    }
}
