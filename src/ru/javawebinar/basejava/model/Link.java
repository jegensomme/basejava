package ru.javawebinar.basejava.model;

import java.net.URL;
import java.util.Objects;

public class Link {

    private final String name;

    private final URL url;

    public Link(String text, URL url) {
        Objects.requireNonNull(text);
        this.name = text;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Link o = (Link) obj;
        if (!name.equals(o.name)) {
            return false;
        }
        return url != null ? url.equals(o.url) : o.url == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
