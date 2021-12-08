package com.example.zad2.model;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ServoMotor extends Peripheral{
    public ServoMotor(){
        pName = "ServoMotor";
        pDescription = "A motor that is designed for precise movement.";
    }

    @Override
    public Object execute(Object... o) {
        HttpURLConnection connection = (HttpURLConnection) o[0];
        URL url = (URL) o[1];
        if (o.length == 1)
            System.out.printf("Sending %s to the %s", Arrays.stream(o).map(Object::toString).collect(Collectors.joining()),  this.getPName());
        else
            System.out.println("Invalid input.");
        return null;
    }

}
