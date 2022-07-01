package com.travelgatex.demo.api.controller;

import com.travelgatex.demo.api.data.HotelResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Validated
@RequestMapping("/1.0")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class TravelController {

    @GetMapping(value = "/lista")
    public ResponseEntity<HotelResponse> listar() {
        HotelResponse hotelResponse = new HotelResponse();
        hotelResponse.setHotel("aaaa");
        return new ResponseEntity<HotelResponse>(hotelResponse, HttpStatus.OK);
    }
}
