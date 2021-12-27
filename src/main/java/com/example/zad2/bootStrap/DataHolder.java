package com.example.zad2.bootStrap;

import com.example.zad2.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class DataHolder {
    public static List<EmbeddedSystem> embeddedSystemList = new ArrayList<>();

    @PostConstruct
    public void init(){
        embeddedSystemList.add(new EmbeddedSystem("Door entrance sensor"
                , "Uses an ultrasonic sensor to detect if someone has entered through the room."
                , "http://192.168.100.27"
                , new UltraSonicSensor()) {
            @Override
            public Object execute(Object... o) {
                return null;
            }
        });


        embeddedSystemList.add(new EmbeddedSystem("Servo motor operator"
                , "This device can move the servo motor."
                ,"http://192.168.100.27/moveRight"
                , new ServoMotor()) {
            @Override
            public String execute(Object... o)  {
                setUrl(String.format("http://192.168.100.27%s",  o[0]));
                return (String) this.getPeripheralList().get(0).execute(this, o);
            }
        });

        embeddedSystemList.add(new EmbeddedSystem(
                "Security camera",
                "Can stream live video with camera and uses ultrasonic sensor to detect movement.",
                "http://192.168.100.24/led",
                new UltraSonicSensor(),
                new Led(),
                new Camera()
        ) {
            @Override
            public String execute(Object... o) {
                return (String) this.getPeripheralList().stream().filter(i-> i instanceof Led).findFirst().get().execute(this, o);
            }
        });

        embeddedSystemList.add(new EmbeddedSystem(
                "Plant care device",
                "Measures the humidity and temperature of the air.",
                "http://192.168.100.27",
                new AirHumidityAndTemperatureSensor(),
                new SoilMoistureSensor()
        ) {
            @Override
            public ServerSentEventsStream execute(Object... o) {
                if (o.length == 0)
                    return (ServerSentEventsStream) this.getPeripheralList().stream()
                            .filter(i -> i.getPName().equals("Soil moisture sensor"))
                            .findFirst()
                            .get()
                            .execute(this);
                return (ServerSentEventsStream) this.getPeripheralList().stream()
                        .findFirst()
                        .get()
                        .execute(this, o[0]);
            }
        });
    }
}