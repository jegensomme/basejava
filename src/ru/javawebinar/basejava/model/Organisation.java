package ru.javawebinar.basejava.model;

public class Organisation {

    private final Link link;

    private final String period;

    private final String title;

    private final String description;

    public Organisation(Link link, String period, String title, String description) {
        this.link = link;
        this.period = period;
        this.title = title;
        this.description = description;
    }

    public Link getLink() {
        return link;
    }

    public String getPeriod() {
        return period;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return link.toString() + '\n'
                + period + '\n'
                + title + '\n'
                + description;
    }
}
