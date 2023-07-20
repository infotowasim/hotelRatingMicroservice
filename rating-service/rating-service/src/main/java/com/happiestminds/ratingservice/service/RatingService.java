package com.happiestminds.ratingservice.service;

import com.happiestminds.ratingservice.entities.Rating;

import java.util.List;

public interface RatingService {

    Rating saveRating(Rating rating);

    List<Rating> getAllRating();

    List<Rating> getAllRatingByUserId(String userId);

    List<Rating> getAllRatingByHotelId(String hotelId);

//    Rating updateRating(String ratingId, Rating rating);
//
//    void deleteRating(String ratingId);
}
