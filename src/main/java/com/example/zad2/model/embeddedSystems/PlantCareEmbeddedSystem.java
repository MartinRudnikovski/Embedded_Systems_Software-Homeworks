package com.example.zad2.model.embeddedSystems;


import com.example.zad2.model.peripherals.AirHumidityAndTemperatureSensor;
import com.example.zad2.model.ServerSentEventsStream;
import com.example.zad2.model.peripherals.SoilMoistureSensor;
import com.example.zad2.model.abstractions.EmbeddedSystem;
import lombok.NonNull;

import java.util.ArrayList;

public class PlantCareEmbeddedSystem extends EmbeddedSystem {
    public PlantCareEmbeddedSystem(@NonNull String url) {
        eName = "Plant Care device";
        eDescription = "Used to measure the moisture in the air and soil and also the temperature in the room.";
        this.url = url;
        peripheralList = new ArrayList<>();
        peripheralList.add( new AirHumidityAndTemperatureSensor());
        peripheralList.add( new SoilMoistureSensor());
    }

    @Override
    public ServerSentEventsStream execute(Object... o) {
        if (o.length == 0)
            return (ServerSentEventsStream) this.getPeripheralList().stream()
                    .filter(i -> i instanceof SoilMoistureSensor)
                    .findFirst()
                    .get()
                    .execute(this);
        return (ServerSentEventsStream) this.getPeripheralList().stream()
                .filter(i -> i instanceof AirHumidityAndTemperatureSensor)
                .findFirst()
                .get()
                .execute(this, o[0]);
    }
}
