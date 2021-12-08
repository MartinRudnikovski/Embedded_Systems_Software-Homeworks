package com.example.zad2.model;

import lombok.Data;

import java.net.HttpURLConnection;
import java.net.URL;

@Data
public abstract class Peripheral {
    String pName;
    String pDescription;

    public abstract Object execute(Object... o);
}