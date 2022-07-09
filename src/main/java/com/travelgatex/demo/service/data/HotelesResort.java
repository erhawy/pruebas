package com.travelgatex.demo.service.data;

import lombok.Data;

import java.util.List;

@Data
public class HotelesResort {
    private String code;
    private String name;
    private String location;
    private List<HabitacionesResort> rooms;
}
