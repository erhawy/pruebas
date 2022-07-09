package com.travelgatex.demo.api.data;

import lombok.Data;

@Data
public class HotelResponse {
    private String code;
    private String name;
    private String city;
    private RoomsResponse rooms;
}
