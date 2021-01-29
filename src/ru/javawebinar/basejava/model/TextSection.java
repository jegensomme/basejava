package ru.javawebinar.basejava.model;

public class TextSection extends Section {

    private final String text;

    public TextSection(SectionType sectionType, String text) {
        super(sectionType);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return sectionType.getTitle() + '\n' + text;
    }
}
