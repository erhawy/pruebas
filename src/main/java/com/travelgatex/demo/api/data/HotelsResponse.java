package com.travelgatex.demo.api.data;

import lombok.Data;

import java.util.List;

@Data
public class HotelsResponse {
    List<HotelResponse> hotels;
}
