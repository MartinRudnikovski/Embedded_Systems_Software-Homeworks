package com.example.zad2.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class ServerSentEventsStream {

    private final URI uri;
    private final HttpClient client;
    public ServerSentEventsStream(String uri) throws URISyntaxException {

        this.uri = new URI(uri);
        this.client = HttpClient.newHttpClient();
    }

    public HttpRequest getRequest(){
        return HttpRequest.newBuilder(uri).GET().build();
    }

    public HttpClient getClient() {
        return this.client;
    }
}
