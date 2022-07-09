package com.travelgatex.demo.service.data;


public enum MealPlanEnum {
    PC("Pension Completa", "pc"),
    AD("Alojamiento y desayuno", "ad");

    private String descripcion;
    private String abreviado;

    private MealPlanEnum(String descripcion, String abreviado){
        this.descripcion = descripcion;
        this.abreviado = abreviado;
    }
    public String getDescripcion(){
        return descripcion;
    }

}
