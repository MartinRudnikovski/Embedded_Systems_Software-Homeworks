package com.example.zad2.services;

import com.example.zad2.model.EmbeddedSystem;
import reactor.core.publisher.Flux;

import java.util.List;

public interface EmbeddedSystemService {

    List<EmbeddedSystem> listAll();

    Flux<String> getUSSAlerts(String s);

}
