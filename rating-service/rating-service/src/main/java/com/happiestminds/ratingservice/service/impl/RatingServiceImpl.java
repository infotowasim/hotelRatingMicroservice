package com.happiestminds.ratingservice.service.impl;

import com.happiestminds.ratingservice.entities.Rating;
import com.happiestminds.ratingservice.repositories.RatingRepository;
import com.happiestminds.ratingservice.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository){
        this.ratingRepository = ratingRepository;
    }


    @Override
    public Rating saveRating(Rating rating) {

        String randomRatingId = UUID.randomUUID().toString();
        rating.setRatingId(randomRatingId);

        Rating saveRating = ratingRepository.save(rating);
        return saveRating;
    }

    @Override
    public List<Rating> getAllRating() {
        List<Rating> ratingList = ratingRepository.findAll();
        return ratingList;
    }

    @Override
    public List<Rating> getAllRatingByUserId(String userId) {
        List<Rating> ratingListByUserId = ratingRepository.findByUserId(userId);
        return ratingListByUserId;
    }

    @Override
    public List<Rating> getAllRatingByHotelId(String hotelId) {
        List<Rating> ratingListByHotelId = ratingRepository.findByHotelId(hotelId);
        return ratingListByHotelId;
    }
}
