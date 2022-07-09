package com.travelgatex.demo.api.data;

import lombok.Data;

@Data
public class HotelsItinerary {
    private String code;
    private String name;
    private String city;
    private RoomItineary room;
}
