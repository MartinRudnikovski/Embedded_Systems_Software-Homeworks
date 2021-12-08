package com.example.zad2.services.impl;

import com.example.zad2.model.EmbeddedSystem;
import com.example.zad2.repositories.InMemoryEmbeddedSystemRepository;
import com.example.zad2.services.EmbeddedSystemService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletRequest;
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
    public Flux<String> getUSSAlerts(String s) {
        return inMemoryEmbeddedSystemRepository.getUSSAlerts(s);
    }

    //==================================Shortcuts
    private EmbeddedSystem getCaller(HttpServletRequest request){
        return  inMemoryEmbeddedSystemRepository.getAll().stream()
                .filter(i -> request.getParameter(i.getEName()) != null)
                .findFirst().get();

    }

}
