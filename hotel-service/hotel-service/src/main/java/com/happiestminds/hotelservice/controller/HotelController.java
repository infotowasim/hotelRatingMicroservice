package com.happiestminds.hotelservice.controller;

import com.happiestminds.hotelservice.entities.Hotel;
import com.happiestminds.hotelservice.services.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private HotelService hotelService;

    public HotelController(HotelService hotelService){
        this.hotelService = hotelService;
    }


    @PostMapping
    public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel){
        Hotel saveHotel = hotelService.saveHotel(hotel);
        return new ResponseEntity<>(saveHotel, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotel(){
        List<Hotel> hotelList = hotelService.getAllHotel();
        return ResponseEntity.ok(hotelList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable("id") String hotelId){
        Hotel hotelById = hotelService.getHotelById(hotelId);
        return new ResponseEntity<>(hotelById,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable("id") String hotelId, @RequestBody Hotel hotel){
        Hotel updateHotel = hotelService.updateHotel(hotelId, hotel);
        return new ResponseEntity<>(updateHotel,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable("id") String hotelId){
        hotelService.deleteHotel(hotelId);
        return new ResponseEntity<>("Hotel entity deleted successfully", HttpStatus.OK);
    }
}
