package com.example.zad2.model.abstractions;

import lombok.Data;

@Data
public abstract class Peripheral {
    protected String pName;
    protected String pDescription;

    public abstract Object execute(Object... o);
}