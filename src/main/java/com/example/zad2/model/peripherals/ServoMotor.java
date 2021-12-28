package com.example.zad2.model.peripherals;

import com.example.zad2.model.abstractions.EmbeddedSystem;
import com.example.zad2.model.abstractions.Peripheral;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ServoMotor extends Peripheral {

    public ServoMotor(){
        pName = "ServoMotor";
        pDescription = "A motor that is designed for precise movement.";
    }

    @Override
    public String execute(Object... o) {
        HttpURLConnection connection = null;
        BufferedReader reader;
        String line;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            connection = ((EmbeddedSystem) o[0]).openConnection();//open connection

            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);


            int status = connection.getResponseCode();
            if (status < 299 && status > 199) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return stringBuilder.toString();
    }

}
