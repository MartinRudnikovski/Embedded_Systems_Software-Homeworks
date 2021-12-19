package com.example.zad2.model;

import java.io.IOException;
import java.net.HttpURLConnection;

public class Led extends Peripheral{
    @Override
    public String execute(Object... o) {
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();

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
        return stringBuilder.toString();
    }
}
