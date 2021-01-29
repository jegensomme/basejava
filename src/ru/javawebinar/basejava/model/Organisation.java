package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organisation {

    private final Link link;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final String title;

    private final String description;

    public Organisation(Link link, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(link);
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        Objects.requireNonNull(title);
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public Link getLink() {
        return link;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
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
                + startDate + '\n'
                + endDate + '\n'
                + title + '\n'
                + description;
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
        Organisation organisation = (Organisation) obj;
        if (!(link.equals(organisation.link) && startDate.equals(organisation.startDate) &&
                endDate.equals(organisation.endDate) && title.equals(organisation.title))) {
            return false;
        }
        return description != null ? description.equals(organisation.description) : organisation.description == null;
    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
