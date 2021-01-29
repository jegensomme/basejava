package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class OrganisationSection extends Section {

    private final List<Organisation> organisations;

    public OrganisationSection(SectionType sectionType, List<Organisation> organisations) {
        super(sectionType);
        Objects.requireNonNull(organisations);
        this.organisations = organisations;
    }

    public List<Organisation> getOrganisations() {
        return organisations;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(sectionType.getTitle() + '\n');
        organisations.forEach(o -> result.append(o.toString() + '\n'));
        return result.toString();
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
        OrganisationSection organisationSection = (OrganisationSection) obj;
        return sectionType.equals(organisationSection.sectionType) && organisations.equals(organisationSection.organisations);
    }

    @Override
    public int hashCode() {
        int result = sectionType.hashCode();
        result = 31 * result + organisations.hashCode();
        return result;
    }
}

