package com.example.zad2.model;

import lombok.Data;
import lombok.NonNull;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public abstract class EmbeddedSystem {
    String eName;
    String eDescription;
    List<Peripheral> peripheralList;


    private HttpURLConnection httpURLConnection;
    private URL url;

    public EmbeddedSystem(@NonNull String eName, @NonNull String eDescription,@NonNull Peripheral... peripherals) {
        this.eName = eName;
        this.eDescription = eDescription;
        this.peripheralList = new ArrayList<>();
        this.peripheralList.addAll(Arrays.asList(peripherals));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Peripheral p : peripheralList)
            sb.append(p.getPName()).append(" ");
        return String.format("%s - %s Uses %s", eName, eDescription, sb);
    }

    public abstract Object execute(Object... o);
}
