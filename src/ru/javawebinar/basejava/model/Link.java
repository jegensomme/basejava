package ru.javawebinar.basejava.model;

import java.net.URL;

public class Link {

    private final String text;

    private final URL url;

    public Link(String text, URL url) {
        this.text = text;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return text;
    }
}
