package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {

    private final List<String> list;

    public ListSection(SectionType sectionType, List<String> list) {
        super(sectionType);
        Objects.requireNonNull(list);
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(sectionType.getTitle() + '\n');
        list.forEach(s -> result.append(s + '\n'));
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
        ListSection listSection = (ListSection) obj;
        return sectionType.equals(listSection.sectionType) && list.equals(listSection.list);
    }

    @Override
    public int hashCode() {
        int result = sectionType.hashCode();
        result = 31 * result + list.hashCode();
        return result;
    }
}
