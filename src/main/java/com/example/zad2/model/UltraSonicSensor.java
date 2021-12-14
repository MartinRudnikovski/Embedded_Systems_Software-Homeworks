package com.example.zad2.model;


public class UltraSonicSensor extends Peripheral{
    public UltraSonicSensor() {
        this.setPName("UltraSonicSensor");
        this.setPDescription("Can be used to measure the distance in front of it.");
    }

    @Override
    public Object execute(Object... o){
        return null;
    }
}
