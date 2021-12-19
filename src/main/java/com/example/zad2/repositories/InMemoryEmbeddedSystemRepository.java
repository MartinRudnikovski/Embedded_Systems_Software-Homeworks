package com.example.zad2.repositories;

import com.example.zad2.bootStrap.DataHolder;
import com.example.zad2.model.EmbeddedSystem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryEmbeddedSystemRepository {

    public List<EmbeddedSystem> getAll(){
        return DataHolder.embeddedSystemList;
    }

    public String moveServoMotor(String path){
        return (String) DataHolder.embeddedSystemList.stream()
                .filter(i -> i.getEName().equals("Servo motor operator"))
                .findFirst()
                .get()
                .execute(path);
    }

    public String ledInteract(){
        return (String) DataHolder.embeddedSystemList.stream()
                .filter(i -> i.getEName().equals("Security camera"))
                .findFirst()
                .get()
                .execute();
    }

}