package com.example.zad2.model;

import java.net.URISyntaxException;

public class AirHumidityAndTemperatureSensor extends Peripheral{

    public AirHumidityAndTemperatureSensor(){
        pName = "Air humidity and temperature sensor";
        pDescription = "Tells the humidity and temperature in a room.";
    }

    //First parameter is the embedded system that has called on the peripheral,
    //second one is whether the stream should be the humidity or not
    @Override
    public ServerSentEventsStream execute(Object... o) {
        EmbeddedSystem embeddedSystem = (EmbeddedSystem) o[0];
        String s = "/airH";
        if(!(Boolean) o[1])
            s = "/airT";
        ServerSentEventsStream sse;
        try {
            sse = new ServerSentEventsStream(String.format("%s%s", embeddedSystem.getUrl(), s));
        } catch (URISyntaxException e) {
            return null;
        }
        return sse;
    }
}