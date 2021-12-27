package com.example.zad2.model;

import java.net.URISyntaxException;

public class SoilMoistureSensor extends Peripheral{
    public SoilMoistureSensor() {
        this.pName = "Soil moisture sensor";
        this.pDescription = "Measures moisture in soil.";
    }

    @Override
    public ServerSentEventsStream execute(Object... o) {
        EmbeddedSystem embeddedSystem = (EmbeddedSystem) o[0];
        ServerSentEventsStream sse;
        try {
            sse = new ServerSentEventsStream(String.format("%s%s", embeddedSystem.getUrl(), "/soil"));
        } catch (URISyntaxException e) {
            return null;
        }
        return sse;
    }
}
