package com.travelgatex.demo.api.controller;

import com.travelgatex.demo.api.data.HotelResponse;
import com.travelgatex.demo.api.data.HotelsResponse;
import com.travelgatex.demo.api.data.ItineraryResponse;
import com.travelgatex.demo.service.HotelsSevice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Validated
@RequestMapping("/1.0")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class TravelController {
    private final HotelsSevice hotelsSevice;

    @GetMapping(value = "/hotelList")
    public ResponseEntity<HotelsResponse> listar() {
        HotelsResponse response = new HotelsResponse();
        response.setHotels(hotelsSevice.obtenerInfoHoteles());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/itineraryCancun")
    public ResponseEntity<ItineraryResponse> itinerario() {

        ItineraryResponse response = hotelsSevice.obtenerItinerario();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
