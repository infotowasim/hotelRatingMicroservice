package com.happiestminds.ratingservice.repositories;

import com.happiestminds.ratingservice.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, String> {

    // Custom finder method
    List<Rating> findByUserId(String userId);

    List<Rating> findByHotelId(String hotelId);
}
