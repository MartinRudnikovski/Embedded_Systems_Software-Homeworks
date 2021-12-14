package com.example.zad2.model;

import lombok.Data;

@Data
public abstract class Peripheral {
    String pName;
    String pDescription;

    public abstract Object execute(Object... o);
}