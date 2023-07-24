package com.happiestminds.userservice.controller;

import com.happiestminds.userservice.entities.User;
import com.happiestminds.userservice.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService= userService;
    }

    private Logger logger = LoggerFactory.getLogger(UserController.class);


    // create user service
    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User saveUser = userService.saveUser(user);
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }


    // get list user service for
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> userList = userService.getAllUser();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }


    // get single user service

    //@CircuitBreaker- Use of the Circuit Breaker pattern can allow a microservice to continue operating
    // when a related service fails, preventing the failure from cascading and giving the failing
    // service time to recover.

    // fallbackMethod should work when required service will down or fail or not working.
    //fallbackMethod ="ratingHotelFallback"- If I want to call RatingService or HotelService and
    //if RatingService or HotelService not working or fail or down then you should call fallbackMethod (ratingHotelFallback)


    int retryCount=1;
    @GetMapping("/{id}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod ="ratingHotelFallback" ) // Implementing CircuitBreaker- this url calling ratingService and hotelService
   //Retry
//    @Retry(name = "ratioHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")


    public ResponseEntity<User> getUserById(@PathVariable("id") String userId){
        logger.info("Get Single User Handler: UserControoler");
        // Retry count
        logger.info("Retry count: {}", retryCount);
        retryCount++;
        User userById = userService.getUserById(userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    // fallbackMethod should work when required service will down or fail or not working.
    // creating fallbackMethod(ratingHotelFallback) for CircuitBreaker
    // return type should be same as Implementing CircuitBreaker or main method of API return type same as parameter also
    // so return type will be ResponseEntity<User>
    // parameter should be (String userId, Exception exception)
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception exception){
//        logger.info("Fallback is executed because server is down : ",exception.getMessage());


        // creating user details
        User user = User.builder()
                .userId("12345")
                .name("dummy")
                .email("dummy@gmail.com")
                .about("This user is created dummy because some service is down")
                .build();
        return ResponseEntity.ok(user);

    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") String userId){
        User updateUser = userService.updateUser(userId, user);
        return new ResponseEntity<>(updateUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("Entity is Deleted Successfully",HttpStatus.OK);
    }
}
