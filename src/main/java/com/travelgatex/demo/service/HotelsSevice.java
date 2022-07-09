package com.travelgatex.demo.service;

import com.travelgatex.demo.api.data.*;
import com.travelgatex.demo.mapper.AtalayaMapper;
import com.travelgatex.demo.service.data.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HotelsSevice {

    //constantes para itinerario
    private static final String CANCUN = "Cancun";
    private static final String MALAGA = "Malaga";
    private static final BigDecimal TOTAL = new BigDecimal(700);
    private static final Integer NMAL = 3;
    private static final Integer NCAN = 5;
    private static final Integer PERSONAS = 2;


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
                        roomsResponse.setName(habitacion.getCode());
                        roomsResponse.setRoom_type(habitacion.getName());
                        roomsResponse.setMeal_plan(mealPlans.getName());
                        roomsResponse.setPrice(value.getPrice());
                        response.setRooms(roomsResponse);
                        lista.add(response);
                    });
                    return lista;
                }

        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public ItineraryResponse obtenerItinerario() {
        ItineraryResponse response = new ItineraryResponse();
        List<HotelResponse> listaHoteles = obtenerInfoHoteles();
        //Obtenemos el menos precio de un hotel con alojamiento y desayuno en cancun
        HotelsItinerary hotelCancun = listaHoteles
                .stream()
                .filter(ht -> ht.getCity().equals(CANCUN) && ht.getRooms().getMeal_plan().equals(MealPlanEnum.AD.getDescripcion()))
                .map( ht -> {
                    HotelsItinerary hotelsItinerary = new HotelsItinerary();
                    hotelsItinerary.setCity(ht.getCity());
                    hotelsItinerary.setCode(ht.getCode());
                    hotelsItinerary.setName(ht.getName());
                    RoomItineary roomItineary = new RoomItineary();
                    roomItineary.setRoom_type(ht.getRooms().getName());
                    roomItineary.setMeal_plan(ht.getRooms().getMeal_plan());
                    roomItineary.setName(ht.getRooms().getName());
                    roomItineary.setNights(NCAN);
                    roomItineary.setPrice(ht.getRooms().getPrice().multiply(new BigDecimal(PERSONAS).multiply(new BigDecimal(NCAN))));
                    hotelsItinerary.setRoom(roomItineary);
                    return hotelsItinerary;
                }).min(Comparator.comparing(htI -> htI.getRoom().getPrice())).get();

        //Obtenemos los de malaga
        List<HotelsItinerary> listaMalaga = listaHoteles
                .stream()
                .filter(ht -> ht.getCity().equals(MALAGA) && ht.getRooms().getMeal_plan().equals(MealPlanEnum.PC.getDescripcion()))
                .map( ht -> {
                    HotelsItinerary hotelsItinerary = new HotelsItinerary();
                    hotelsItinerary.setCity(ht.getCity());
                    hotelsItinerary.setCode(ht.getCode());
                    hotelsItinerary.setName(ht.getName());
                    RoomItineary roomItineary = new RoomItineary();
                    roomItineary.setRoom_type(ht.getRooms().getRoom_type());
                    roomItineary.setMeal_plan(ht.getRooms().getMeal_plan());
                    roomItineary.setName(ht.getRooms().getName());
                    roomItineary.setNights(NMAL);
                    roomItineary.setPrice(ht.getRooms().getPrice().multiply(new BigDecimal(PERSONAS).multiply(new BigDecimal(NMAL))));
                    hotelsItinerary.setRoom(roomItineary);
                    return hotelsItinerary;
                }).sorted((p1, p2) -> p2.getRoom().getPrice().compareTo(p1.getRoom().getPrice())).toList();
        List<HotelsItinerary> itinerario = new ArrayList<>();
        itinerario.add(hotelCancun);
        for(HotelsItinerary hotel : listaMalaga){
            if(hotel.getRoom().getPrice().add(hotelCancun.getRoom().getPrice()).compareTo(TOTAL) < 0) {
                itinerario.add(hotel);
                break;
            }
        }
        response.setHotels(itinerario);
        return response;

    }
}
