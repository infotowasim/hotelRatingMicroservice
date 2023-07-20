package com.happiestminds.hotelservice.services;

import com.happiestminds.hotelservice.entities.Hotel;
import com.happiestminds.hotelservice.exception.ResourceNotFoundException;
import com.happiestminds.hotelservice.repositories.HotelRepository;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.RelationNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService{


    private HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository){
        this.hotelRepository = hotelRepository;
    }


    @Override
    public Hotel saveHotel(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        Hotel saveHotel = hotelRepository.save(hotel);
        return saveHotel;
    }

    @Override
    public List<Hotel> getAllHotel() {
        List<Hotel> hotelList = hotelRepository.findAll();
        return hotelList;
    }

    @Override
    public Hotel getHotelById(String id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("hotel with given id not found" + id));
        return hotel;
    }

    @Override
    public Hotel updateHotel(String hotelId, Hotel hotel) {
        Hotel hotel1 = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("hotel with given id not found" + hotelId));

        hotel1.setName(hotel.getName());
        hotel1.setLocation(hotel.getLocation());
        hotel1.setAbout(hotel.getAbout());
        Hotel updateHotel = hotelRepository.save(hotel1);
        return updateHotel;
    }

    @Override
    public void deleteHotel(String hotelId) {
        Hotel hotel1 = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("hotel with given id not found" + hotelId));
        hotelRepository.delete(hotel1);
    }


}
