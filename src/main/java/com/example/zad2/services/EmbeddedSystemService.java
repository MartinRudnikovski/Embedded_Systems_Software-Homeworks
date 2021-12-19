package com.example.zad2.services;

import com.example.zad2.model.EmbeddedSystem;

import java.util.List;

public interface EmbeddedSystemService {

    List<EmbeddedSystem> listAll();

    void moveServoMotor(String path);

    void ledInteract();

}
