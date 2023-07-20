package com.happiestminds.userservice.services.impl;

import com.happiestminds.userservice.entities.Hotel;
import com.happiestminds.userservice.entities.Rating;
import com.happiestminds.userservice.entities.User;
import com.happiestminds.userservice.exception.ResourceNotFoundException;
import com.happiestminds.userservice.external.service.HotelService;
import com.happiestminds.userservice.repositories.UserRepository;
import com.happiestminds.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;  // for Feign Client

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);



    @Override
    public User saveUser(User user) {

        // Generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);

        User saveUser = userRepository.save(user);
        return saveUser;
    }

    @Override
    public List<User> getAllUser() {



        List<User> userList = userRepository.findAll();

        // Implementing Rating Service Call: Using Rest Template.
//        List<Rating> restTemplateForObject = restTemplate.getForObject("http://localhost:8083/ratings", List.class);
//        logger.info("{}",restTemplateForObject);
//        userList.


        return userList;
    }

    @Override
    public User getUserById(String userId) {

        // get user from database using userRepository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found" + userId));

        // fetching rating of the above user from rating service
        // http://localhost:8083/ratings/users/9b8519f6-0044-4115-a3ee-1cbd66bfdb71
        // user Single id
//        ArrayList<Rating> restTemplateForObject = restTemplate.getForObject("http://localhost:8083/ratings/users/9b8519f6-0044-4115-a3ee-1cbd66bfdb71", ArrayList.class);

        // user Dynamic Id :- u can put any UserId and u can see how many rating id is present in 1 userId
//        Rating[] ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), Rating[].class);
        // Dynamic or replace host & port name - u have to use @LoadBalanced inside @Bean RestTemplate.
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);

        logger.info(" {} ",ratingsOfUser);
        // converting Array to ArrayList
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            // API call to hotel service to get the hotel
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("localhost:8082/hotels/8d3426fe-c435-4ec1-a4d6-7ecfbe13db88", Hotel.class);
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8082/hotels/"+rating.getHotelId(), Hotel.class);
           // Dynamic or replace host & port name. - u have to use @LoadBalanced inside @Bean RestTemplate.
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);

            // Feign Client
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
//            Hotel hotel = forEntity.getBody();



//            logger.info("response status code: {}", forEntity.getStatusCode());

            // set the hotel to rating
            rating.setHotel(hotel);

            return rating;

        }).collect(Collectors.toList());

        user.setRatings(ratingList);

        return user;
    }

    @Override
    public User updateUser(String userId, User user) {
        User user1 = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found" + userId));

        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setAbout(user.getAbout());

        User updatedUser = userRepository.save(user1);
        return updatedUser;
    }

    @Override
    public void deleteUser(String userId) {
        User user1 = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found" + userId));
        userRepository.delete(user1);
    }
}
