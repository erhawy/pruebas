package com.travelgatex.demo.service.data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RegimenesItem {
    private String code;
    private String name;
    private String hotel;
    private String room_type;
    private BigDecimal price;
}
