package com.example.zad2.bootStrap;

import com.example.zad2.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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
    }
}