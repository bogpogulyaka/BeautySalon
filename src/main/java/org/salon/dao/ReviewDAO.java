package org.salon.dao;

import org.salon.models.Review;

public interface ReviewDAO extends DAO<Review>  {
    Review getByAppointmentId(long id);
}
