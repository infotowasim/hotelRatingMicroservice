package com.happiestminds.ratingservice.controller;

import com.happiestminds.ratingservice.entities.Rating;
import com.happiestminds.ratingservice.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private RatingService ratingService;

    public RatingController(RatingService ratingService){
        this.ratingService = ratingService;
    }


    @PostMapping
    public ResponseEntity<Rating> saveRating(@RequestBody Rating rating){
        Rating saveRating = ratingService.saveRating(rating);
        return new ResponseEntity<>(saveRating, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRating(){
        List<Rating> ratingList = ratingService.getAllRating();
        return ResponseEntity.ok(ratingList);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<Rating>> getAllRatingByUserId(@PathVariable("id") String userId){
        List<Rating> ratingListByUserId = ratingService.getAllRatingByUserId(userId);
        return ResponseEntity.ok(ratingListByUserId);
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<List<Rating>> getAllRatingByHotelId(@PathVariable("id") String hotelId){
        List<Rating> ratingListByHotelId = ratingService.getAllRatingByHotelId(hotelId);
        return ResponseEntity.ok(ratingListByHotelId);
    }
}
