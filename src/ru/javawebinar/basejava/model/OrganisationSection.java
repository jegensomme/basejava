package ru.javawebinar.basejava.model;

import java.util.List;

public class OrganisationSection extends Section {

    private final List<Organisation> organisations;

    public OrganisationSection(SectionType sectionType, List<Organisation> organisations) {
        super(sectionType);
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
}

