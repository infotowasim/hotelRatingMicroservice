package com.happiestminds.hotelservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/staffs")
public class StaffController {

    @GetMapping
    public ResponseEntity<List<String>> getAllStaff(){
        List<String> stringList = Arrays.asList("Wasim", "Rashid", "Pervez", "Prakash", "Mausoof");
        return ResponseEntity.ok(stringList);
    }
}
