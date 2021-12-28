package com.example.zad2.model.peripherals;


import com.example.zad2.model.abstractions.EmbeddedSystem;
import com.example.zad2.model.abstractions.Peripheral;

import java.io.IOException;
import java.net.HttpURLConnection;


public class Led extends Peripheral {

    public Led() {
        pName = "LED";
        pDescription = "Used for lighting up the space around it.";
    }

    @Override
    public String execute(Object... o) {
        HttpURLConnection connection = null;

        try {
            connection = ((EmbeddedSystem) o[0]).openConnection();//open connection

            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);


            connection.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return "";
    }
}
