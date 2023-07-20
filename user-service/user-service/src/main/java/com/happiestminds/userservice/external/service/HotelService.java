package com.happiestminds.userservice.external.service;

import com.happiestminds.userservice.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

    @GetMapping("/hotels/{id}")
    Hotel getHotel(@PathVariable("id") String hotelId);
}
