package com.example.zad2.bootStrap;

import com.example.zad2.model.EmbeddedSystem;
import com.example.zad2.model.ServoMotor;
import com.example.zad2.model.UltraSonicSensor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<EmbeddedSystem> embeddedSystemList = new ArrayList<>();

    @PostConstruct
    public void init(){
        URL URL = null;
        try {
            URL = new URL("http://192.168.100.27/hello");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        embeddedSystemList.add(new EmbeddedSystem("Door entrance sensor", "Uses an ultrasonic sensor to detect if someone has entered through the room.", new UltraSonicSensor()) {
            @Override
            public Flux<String> execute(Object... o) {

                return (Flux<String>) getPeripheralList().get(0).execute(o[0]);
            }
        });
        java.net.URL finalURL = URL;
        embeddedSystemList.add(new EmbeddedSystem("Servo motor operator", "This device can move the servo motor.", new ServoMotor()) {
            @Override
            public Object execute(Object... o)  {
                setUrl(finalURL);
                getPeripheralList().get(0).execute(getHttpURLConnection(), getUrl(), o);
                return null;
            }
        });

    }
}