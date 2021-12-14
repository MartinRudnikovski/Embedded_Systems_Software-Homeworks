package com.example.zad2.bootStrap;

import com.example.zad2.model.EmbeddedSystem;
import com.example.zad2.model.ServoMotor;
import com.example.zad2.model.UltraSonicSensor;
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
                return (String) getPeripheralList().get(0).execute(this, o);
            }
        });

    }
}