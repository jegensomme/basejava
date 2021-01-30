package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * gkislin
 * 19.07.2016
 */
public class OrganizationSection extends Section {
    private final List<Participation> participationList;

    public OrganizationSection(List<Participation> participations) {
        Objects.requireNonNull(participations, "participationList must not be null");
        this.participationList = participations;
    }

    public List<Participation> getParticipationList() {
        return participationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return participationList.equals(that.participationList);

    }

    @Override
    public int hashCode() {
        return participationList.hashCode();
    }

    @Override
    public String toString() {
        return participationList.toString();
    }

    private class Participation {
        final Organization organization;
        final LocalDate startDate;
        final LocalDate endDate;
        final String title;
        final String description;

        public Participation(Organization organization, LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(organization, "organisation must not be null");
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.organization = organization;
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Participation that = (Participation) o;

            if (!organization.equals(that.organization)) return false;
            if (!startDate.equals(that.startDate)) return false;
            if (!endDate.equals(that.endDate)) return false;
            if (!title.equals(that.title)) return false;
            return description != null ? description.equals(that.description) : that.description == null;

        }

        @Override
        public int hashCode() {
            int result = organization.hashCode();
            result = 31 * result + startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Organization{" +
                    "organisation=" + organization +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}