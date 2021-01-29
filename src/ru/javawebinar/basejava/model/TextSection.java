package ru.javawebinar.basejava.model;

import java.util.Objects;

public class TextSection extends Section {

    private final String text;

    public TextSection(SectionType sectionType, String text) {
        super(sectionType);
        Objects.requireNonNull(text);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return sectionType.getTitle() + '\n' + text;
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
        TextSection textSection = (TextSection) obj;
        return sectionType.equals(textSection.sectionType) && text.equals(textSection.text);
    }

    @Override
    public int hashCode() {
        int result = sectionType.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }
}
