package org.salon.services;

import org.salon.models.Review;
import org.salon.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.util.List;

public class ReviewService {
    private final ReviewRepository reviewRepository;
    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review getReview(long id) {
        return reviewRepository.findById(id).orElse(null);
    }
    public Review getReviewByAppointmentId(long id) {
        return reviewRepository.findByAppointmentId(id);
    }
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    public Review createReview(Review review){
        return reviewRepository.save(review);
    }
    public void updateReview(Review review){
        reviewRepository.save(review);
    }
    public void deleteReview(long id){
        reviewRepository.deleteById(id);
    }
}
