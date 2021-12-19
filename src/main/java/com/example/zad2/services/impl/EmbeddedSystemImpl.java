package com.example.zad2.services.impl;

import com.example.zad2.model.EmbeddedSystem;
import com.example.zad2.repositories.InMemoryEmbeddedSystemRepository;
import com.example.zad2.services.EmbeddedSystemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddedSystemImpl implements EmbeddedSystemService {

    public final InMemoryEmbeddedSystemRepository inMemoryEmbeddedSystemRepository;

    public EmbeddedSystemImpl(InMemoryEmbeddedSystemRepository inMemoryEmbeddedSystemRepository) {
        this.inMemoryEmbeddedSystemRepository = inMemoryEmbeddedSystemRepository;
    }


    //=================================Services
    @Override
    public List<EmbeddedSystem> listAll() {
        return inMemoryEmbeddedSystemRepository.getAll();
    }

    @Override
    public void moveServoMotor(String path) {
        inMemoryEmbeddedSystemRepository.moveServoMotor(path);
    }

    @Override
    public void ledInteract() {
        inMemoryEmbeddedSystemRepository.ledInteract();
    }


}
