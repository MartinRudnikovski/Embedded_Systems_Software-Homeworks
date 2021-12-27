package com.example.zad2.controllers;

import com.example.zad2.model.ServerSentEventsStream;
import com.example.zad2.model.User;
import com.example.zad2.services.EmbeddedSystemService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;
import java.util.stream.Stream;


@Controller
public class MainMenuController {
    private final EmbeddedSystemService embeddedSystemService;
    String ussAlerts = "-1";
    Integer servoCurrentDegree = 0;
    Integer inf = 0;
    String airH = "Cannot connect to server.";
    String airT = "Cannot connect to server.";
    String soil = "Cannot connect to server.";

    public MainMenuController(EmbeddedSystemService embeddedSystemService) {
        this.embeddedSystemService = embeddedSystemService;

        Thread airHumidityThread = new Thread(() -> {
            ServerSentEventsStream sse = embeddedSystemService.airHumiditySensor();
            Stream<String> str;
            try {
                str = sse.getClient().send(sse.getRequest(), HttpResponse.BodyHandlers.ofLines()).body();
            } catch (IOException | InterruptedException e) {
                return;
            }
            str.filter( f ->f.contains(":")).map( m -> m.split(":")[1]).forEach( i -> airH = i);
        });
        airHumidityThread.start();

        Thread airTemperatureThread = new Thread(() -> {
            ServerSentEventsStream sse = embeddedSystemService.airTemperatureSensor();
            Stream<String> str;
            try {
                str = sse.getClient().send(sse.getRequest(), HttpResponse.BodyHandlers.ofLines()).body();
            } catch (IOException | InterruptedException e) {
                return;
            }
            str.filter( f ->f.contains(":")).map( m -> m.split(":")[1]).forEach( i -> airT = i);
        });
        airTemperatureThread.start();



        Thread soilMoistureThread = new Thread(() -> {
            ServerSentEventsStream sse = embeddedSystemService.soilMoistureSensor();
            Stream<String> str;
            try {
                str = sse.getClient().send(sse.getRequest(), HttpResponse.BodyHandlers.ofLines()).body();
            } catch (IOException | InterruptedException e) {
                return;
            }
            str.filter( f ->f.contains(":"))
                    .map( m -> m.split(":")[1].trim())
                    .map( st -> ( ( (float)(4095.0 - 1500.0) - (Integer.parseInt(st) - 1500) )/(4095.0 - 1500.0) ) * 100)
                    .map(integer -> Integer.toString(integer.intValue()))
                    .forEach( i ->{ soil = i;
                        System.out.println(soil);});
        });
        soilMoistureThread.start();

    }

    @GetMapping("/login")
    public String getLogIn(){
        return "login";
    }

    @GetMapping("/")
    public String navigateToLogIn(){
        return "redirect:/login";
    }

    @PostMapping("/mainMenu")
    public String validateLogIn(HttpServletRequest request, @RequestParam String password){
        User v;
        if ((v = User.validatePassword(password)) != null) {
            request.getSession().setAttribute("user", v);
            return "redirect:/mainMenu";
        }
        request.getSession().setAttribute("user", null);
        return "redirect:/login";
    }



    @GetMapping("/mainMenu")
    public String getMainMenu(Model model){
        model.addAttribute("embeddedSystems", this.embeddedSystemService.listAll());
        model.addAttribute("servoCurrentDegree", servoCurrentDegree);
        return "mainmenu";
    }

    @GetMapping(value = "/ussStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> ussStream(){
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> Collections.singletonList(ussAlerts))
                .flatMapIterable(stream -> stream)
                .distinctUntilChanged();
    }


    @PostMapping("/ussStream")
    public String getUSSUpdatesFromEmbeddedSystem(@RequestBody String update){
        ussAlerts = update.split("=")[1];
        return "usstemplate";
    }

    @PostMapping("/clearAlert")
    public String clearAlert(){
        ussAlerts = "-1";
        return "redirect:/mainMenu";
    }

    @PostMapping("/moveServoMotor")
    public String moveServoMotorRight(@RequestParam String direction){
        embeddedSystemService.moveServoMotor(direction.equals("<=") ? "/moveLeft" : "/moveRight");
        return "redirect:/mainMenu";
    }


    @GetMapping(value = "/servoStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> servoStream(){
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> Collections.singletonList(servoCurrentDegree))
                .flatMapIterable(stream -> stream)
                .distinctUntilChanged();
    }

    @PostMapping("/servoStream")
    public String servoWasMoved(@RequestBody String position){
        servoCurrentDegree = Integer.parseInt(position.split("=")[1]);
        return "usstemplate";
    }

    //Embedded system can change the infiltration state on the server
    @PostMapping("/infiltration")
    public String inf(){
        inf = 1;
        return "usstemplate";
    }

    //Update the clients if there is an infiltration
    @GetMapping("/inf")
    public Flux<Integer> infiltrationStream(){
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> Collections.singletonList(inf))
                .flatMapIterable(stream -> stream)
                .distinctUntilChanged();
    }

    //Clients can reset infiltrator alarm.
    @PostMapping("/infOk")
    public String isOk(){
        inf = 0;
        return "redirect:/mainMenu";
    }

    //Clients can toggle the LED
    @PostMapping("/led")
    public String led(){
        embeddedSystemService.ledInteract();
        return "redirect:/mainMenu";
    }

    @GetMapping(value = "/airHumidity", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> airHumidity(){
        return Flux.interval(Duration.ofMillis(500))
                .map( i -> Collections.singletonList(airH))
                .flatMapIterable(streams -> streams)
                .distinctUntilChanged();
    }

    @GetMapping(value = "/airTemperature", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> airTemperature(){
        return Flux.interval(Duration.ofMillis(500))
                .map( i -> Collections.singletonList(airT))
                .flatMapIterable(streams -> streams)
                .distinctUntilChanged();
    }

    @GetMapping(value = "/soilMoisture", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> soilMoisture(){
        return Flux.interval(Duration.ofMillis(500))
                .map( i -> Collections.singletonList(soil))
                .flatMapIterable(streams -> streams)
                .distinctUntilChanged();
    }

}





















