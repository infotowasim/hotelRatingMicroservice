package com.happiestminds.hotelservice.services;

import com.happiestminds.hotelservice.entities.Hotel;

import java.util.List;

public interface HotelService {

    Hotel saveHotel(Hotel hotel);

    List<Hotel> getAllHotel();

    Hotel getHotelById(String id);

    Hotel updateHotel(String hotelId, Hotel hotel);

    void deleteHotel(String hotelId);
}
