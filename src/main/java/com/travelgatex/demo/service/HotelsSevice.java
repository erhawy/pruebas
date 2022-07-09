package com.travelgatex.demo.service;

import com.travelgatex.demo.api.data.HotelResponse;
import com.travelgatex.demo.api.data.RoomsResponse;
import com.travelgatex.demo.mapper.AtalayaMapper;
import com.travelgatex.demo.service.data.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HotelsSevice {

    private final ClientAtalayaService clientAtalayaService;
    private final ClientResortService clientResortService;

    public List<HotelResponse> obtenerInfoHoteles() {
        AtalayaMealsResponse atalayaMealsResponse = clientAtalayaService.obtenerRegimenes();
        List<HotelResponse> hoteles = new ArrayList<>();
        hoteles.addAll(atalayaMealsResponse.getMeal_plans()
                .stream()
                .map(meal -> {return generarHotelResponse(meal);})
                .flatMap(Collection::stream).toList());

        RegimenesResort regimenesResort = clientResortService.obtenerRegimenes();
        ResortsHotels hotels = clientResortService.obtenerHoteles();
        hoteles.addAll(generarHotelResortResponse(regimenesResort, hotels));


        return hoteles;

    }

    private List<HotelResponse> generarHotelResortResponse(RegimenesResort meals, ResortsHotels hotels) {
        return meals.getRegimenes()
                .stream()
                .map(meal -> {
                    HotelResponse hotelResponse = new HotelResponse();
                    HotelesResort ht = hotels.getHotels()
                            .stream()
                            .filter(hotel -> hotel.getCode().equals(meal.getHotel()))
                            .findFirst().get();
                    hotelResponse.setCity(ht.getLocation());
                    hotelResponse.setName(ht.getName());
                    hotelResponse.setCode(ht.getCode());
                    RoomsResponse rooms = new RoomsResponse();
                    HabitacionesResort hb = ht.getRooms().stream().filter(rm -> rm.getCode().equals(meal.getRoom_type())).findFirst().get();
                    rooms.setName(hb.getName());
                    rooms.setRoom_type(meal.getRoom_type());
                    rooms.setMeal_plan(meal.getName());
                    rooms.setPrice(meal.getPrice());
                    hotelResponse.setRooms(rooms);
                    return hotelResponse;
                }).toList();


    }

    private List<HotelResponse> generarHotelResponse(MealPlans mealPlans){
        AtalayaResponse atalayaResponse = clientAtalayaService.obtenerHoteles();
        AtalayaRoomsResponse atalayaRoomsResponse = clientAtalayaService.obtenerHabitaciones();

        return mealPlans.getHotel().entrySet()
                .stream()
                .map(entry -> {
                    List<HotelResponse> lista = new ArrayList<>();
                    //Recorremos los tipos de habitaciones
                    entry.getValue().forEach(value -> {
                        //Buscamos el hotel
                        HotelesAtalaya hotel = atalayaResponse.getHotels()
                                .stream()
                                .filter(hotelfiltro -> hotelfiltro.getCode().equals(entry.getKey())).findFirst().get();
                        HotelResponse response = new HotelResponse();
                        response.setCode(entry.getKey());
                        response.setName(hotel.getName());
                        response.setCity(hotel.getCity());
                        //Buscamos la habitacion
                        TiposHabitacion habitacion = atalayaRoomsResponse.getRooms_type()
                                .stream()
                                .filter(room -> room.getCode().equals(value.getRoom())).findFirst().get();
                        RoomsResponse roomsResponse = new RoomsResponse();
                        roomsResponse.setName(habitacion.getName());
                        roomsResponse.setRoom_type(mealPlans.getName());
                        roomsResponse.setMeal_plan(mealPlans.getCode());
                        roomsResponse.setPrice(value.getPrice());
                        response.setRooms(roomsResponse);
                        lista.add(response);
                    });
                    return lista;
                }

        ).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
