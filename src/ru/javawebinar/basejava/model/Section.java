package ru.javawebinar.basejava.model;

import java.util.Objects;

public abstract class Section {

    protected final SectionType sectionType;

    public Section(SectionType sectionType) {
        Objects.requireNonNull(sectionType);
        this.sectionType = sectionType;
    }
}
