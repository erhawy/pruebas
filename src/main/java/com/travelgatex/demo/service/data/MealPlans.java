package com.travelgatex.demo.service.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MealPlans {
    private String code;
    private String name;
    private Map<String, List<HotelMeal>> hotel;
}
