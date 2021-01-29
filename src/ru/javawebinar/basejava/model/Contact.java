package ru.javawebinar.basejava.model;

import java.util.Objects;

public class Contact {

    private final ContactType contactType;

    private final Link link;

    public Contact(ContactType contactType, Link link) {
        Objects.requireNonNull(contactType);
        Objects.requireNonNull(link);
        this.contactType = contactType;
        this.link = link;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public Link getLink() {
        return link;
    }

    @Override
    public String toString() {
        return contactType.getTitle() + link.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Contact contact = (Contact) obj;
        return contactType.equals(contact.contactType) && link.equals(contact.link);
    }

    @Override
    public int hashCode() {
        int result = contactType.hashCode();
        result = 31 * result + link.hashCode();
        return result;
    }
}
