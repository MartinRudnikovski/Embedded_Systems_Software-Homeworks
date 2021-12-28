package com.example.zad2.model.abstractions;

import lombok.Data;
import lombok.NonNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public abstract class EmbeddedSystem {

    public enum EmbeddedSystems {
        PLANTCAREDEVICE,
        SECURITYCAMERA
    }

    protected String eName;
    protected String eDescription;
    protected List<Peripheral> peripheralList;

    protected String url;

    public EmbeddedSystem(@NonNull String eName, @NonNull String eDescription,@NonNull String url, @NonNull Peripheral... peripherals) {
        this.eName = eName;
        this.eDescription = eDescription;
        this.peripheralList = new ArrayList<>();
        this.peripheralList.addAll(Arrays.asList(peripherals));
        this.url = url;
    }

    //Used to make an embedded system model
    public EmbeddedSystem(){

    }

    public HttpURLConnection openConnection() throws IOException {
        return (HttpURLConnection) new URL(url).openConnection();
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
