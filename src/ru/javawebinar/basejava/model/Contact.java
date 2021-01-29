package ru.javawebinar.basejava.model;

public class Contact {

    private final ContactType contactType;

    private final Link link;

    public Contact(ContactType contactType, Link link) {
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
}
