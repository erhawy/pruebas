package com.travelgatex.demo.service.data;

import lombok.Data;

import java.util.List;

@Data
public class TiposHabitacion {
    private List<String> hotels;
    private String code;
    private String name;
}
