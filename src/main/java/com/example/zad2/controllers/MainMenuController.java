package com.example.zad2.controllers;

import com.example.zad2.model.User;
import com.example.zad2.services.EmbeddedSystemService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Collections;


@Controller
public class MainMenuController {
    private final EmbeddedSystemService embeddedSystemService;
    String ussAlerts = "-1";

    public MainMenuController(EmbeddedSystemService embeddedSystemService) {
        this.embeddedSystemService = embeddedSystemService;
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
    public String getMainMenu(Model model, HttpServletRequest request){
        model.addAttribute("embeddedSystems", this.embeddedSystemService.listAll());
        return "mainmenu";//<- треба да се соовпаѓа со .html датотека во templates
    }

    @GetMapping(value = "/ussStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> ussStream(){
        return Flux.interval(Duration.ofMillis(500))
                .map(i-> Collections.singletonList(ussAlerts))
                .flatMapIterable(stream -> stream);
    }


    @PostMapping("/ussStream")
    public String getUSSUpdatesFromEmbeddedSystem(@RequestBody String update){
        ussAlerts = update.split("=")[1];
        return "usstemplate";
    }

    @PostMapping("/clearAlert")
    public String clearAlert(HttpServletRequest request){
        ussAlerts = "-1";
        return "redirect:/mainMenu";
    }
}
