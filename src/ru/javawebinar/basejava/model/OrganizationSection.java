package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * gkislin
 * 19.07.2016
 */
public class OrganizationSection extends Section {
    private final List<OrganisationParticipation> participationList;

    public OrganizationSection(List<OrganisationParticipation> participationList) {
        Objects.requireNonNull(participationList, "participationList must not be null");
        this.participationList = participationList;
    }

    public List<OrganisationParticipation> getParticipationList() {
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
}