package ru.javawebinar.basejava.model;

import java.util.List;

public class ListSection extends Section {

    private final List<String> list;

    public ListSection(SectionType sectionType, List<String> list) {
        super(sectionType);
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
}
