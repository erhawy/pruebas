package com.travelgatex.demo.api.data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomsResponse {
    private String name;
    private String room_type;
    private String meal_plan;
    private BigDecimal price;
}
