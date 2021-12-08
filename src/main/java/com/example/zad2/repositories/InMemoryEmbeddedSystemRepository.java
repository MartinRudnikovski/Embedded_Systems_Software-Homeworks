package com.example.zad2.repositories;

import com.example.zad2.bootStrap.DataHolder;
import com.example.zad2.model.EmbeddedSystem;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public class InMemoryEmbeddedSystemRepository {

    public List<EmbeddedSystem> getAll(){
        return DataHolder.embeddedSystemList;
    }

    public Flux<String> getUSSAlerts(String s){
        return (Flux<String>) DataHolder.embeddedSystemList.get(0).execute(s);
    }

}