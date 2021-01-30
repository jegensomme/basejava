package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * gkislin
 * 19.07.2016
 */
public class Organization {
    private final Link homePage;

    public Organization(String name, String url) {
        this.homePage = new Link(name, url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        return homePage.equals(that.homePage);

    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return homePage.toString();
    }
}
