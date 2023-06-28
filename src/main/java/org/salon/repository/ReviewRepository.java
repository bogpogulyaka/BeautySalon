package org.salon.repository;

import org.salon.models.Review;
import org.salon.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long>{
    @Query("SELECT * FROM review WHERE appointment_id = ?1")
    Review findByAppointmentId(long id);
}
