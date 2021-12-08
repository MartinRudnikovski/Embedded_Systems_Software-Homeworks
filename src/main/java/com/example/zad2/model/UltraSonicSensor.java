package com.example.zad2.model;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Collections;

public class UltraSonicSensor extends Peripheral{
    public UltraSonicSensor() {
        this.setPName("UltraSonicSensor");
        this.setPDescription("Can be used to measure the distance in front of it.");
    }

    @Override
    public Flux<String> execute(Object... o){
        return Flux.interval(Duration.ofMillis(500))
                .map(i-> Collections.singletonList((String) o[0]))
                .flatMapIterable(stream -> stream);
    }
}
