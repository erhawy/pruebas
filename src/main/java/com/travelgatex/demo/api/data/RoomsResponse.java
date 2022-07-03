package com.travelgatex.demo.api.data;

import lombok.Data;

@Data
public class RoomsResponse {
    private String name;
    private String room_type;
    private String meal_plan;
    private String price;
}
