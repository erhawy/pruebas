package com.travelgatex.demo.service;

import com.travelgatex.demo.api.data.HotelResponse;
import com.travelgatex.demo.mapper.AtalayaMapper;
import com.travelgatex.demo.service.data.AtalayaMealsResponse;
import com.travelgatex.demo.service.data.AtalayaResponse;
import com.travelgatex.demo.service.data.AtalayaRoomsResponse;
import com.travelgatex.demo.service.data.HotelesAtalaya;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HotelsSevice {

    private final ClientAtalayaService clientAtalayaService;

    public List<HotelResponse> obtenerInfoHoteles() {
        AtalayaResponse atalayaResponse = clientAtalayaService.obtenerHoteles();
        AtalayaRoomsResponse atalayaRoomsResponse = clientAtalayaService.obtenerHabitaciones();
        AtalayaMealsResponse atalayaMealsResponse = clientAtalayaService.obtenerRegimenes();
        return new ArrayList<>();

    }
}
