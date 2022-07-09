package com.travelgatex.demo.api.data;

import lombok.Data;

import java.util.List;

@Data
public class ItineraryResponse {
    List<HotelsItinerary> hotels;
}
