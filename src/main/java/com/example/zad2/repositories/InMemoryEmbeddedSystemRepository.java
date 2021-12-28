package com.example.zad2.repositories;

import com.example.zad2.bootStrap.DataHolder;
import com.example.zad2.model.abstractions.EmbeddedSystem;
import com.example.zad2.model.ServerSentEventsStream;
import com.example.zad2.model.embeddedSystems.PlantCareEmbeddedSystem;
import com.example.zad2.model.embeddedSystems.SecurityCameraEmbeddedSystem;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                .filter(i -> i instanceof PlantCareEmbeddedSystem)
                .findFirst()
                .get()
                .execute(humidity);
    }

    public ServerSentEventsStream getSoilMoistureStream(){
        return (ServerSentEventsStream) DataHolder.embeddedSystemList.stream()
                .filter(i -> i instanceof PlantCareEmbeddedSystem)
                .findFirst()
                .get()
                .execute();
    }


}