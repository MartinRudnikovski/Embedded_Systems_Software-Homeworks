package com.example.zad2.repositories;

import com.example.zad2.bootStrap.DataHolder;
import com.example.zad2.model.EmbeddedSystem;
import com.example.zad2.model.ServerSentEventsStream;
import org.springframework.stereotype.Repository;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class InMemoryEmbeddedSystemRepository {

    public List<EmbeddedSystem> getAll(){
        return DataHolder.embeddedSystemList;
    }

    public void moveServoMotor(String path){
        DataHolder.embeddedSystemList.stream()
                .filter(i -> i.getEName().equals("Servo motor operator"))
                .findFirst()
                .get()
                .execute(path);
    }

    public void ledInteract(){
        DataHolder.embeddedSystemList.stream()
                .filter(i -> i.getEName().equals("Security camera"))
                .findFirst()
                .get()
                .execute();
    }

    public ServerSentEventsStream getAirHumidityStream(boolean humidity){
        return (ServerSentEventsStream) DataHolder.embeddedSystemList.stream()
                .filter(i ->i.getEName().equals("Plant care device"))
                .findFirst()
                .get()
                .execute(humidity);
    }

    public ServerSentEventsStream getSoilMoistureStream(){
        return (ServerSentEventsStream) DataHolder.embeddedSystemList.stream()
                .filter(i -> i.getEName().equals("Plant care device"))
                .findFirst()
                .get()
                .execute();
    }

}