package com.example.zad2.model.embeddedSystems;

import com.example.zad2.model.abstractions.EmbeddedSystem;
import com.example.zad2.model.peripherals.Camera;
import com.example.zad2.model.peripherals.Led;
import com.example.zad2.model.peripherals.UltraSonicSensor;

import java.util.ArrayList;

public class SecurityCameraEmbeddedSystem extends EmbeddedSystem {

    public SecurityCameraEmbeddedSystem(String url) {
        eName = "Security camera";
        eDescription = "Can stream live video with camera and uses ultrasonic sensor to detect movement.";

        peripheralList = new ArrayList<>();
        peripheralList.add(new Led());
        peripheralList.add(new Camera());
        peripheralList.add(new UltraSonicSensor());
        this.url = url;
    }

    @Override
    public String execute(Object... o) {
        return (String) this.getPeripheralList()
                .stream()
                .filter(i-> i instanceof Led)
                .findFirst()
                .get()
                .execute(this, o);
    }
}
