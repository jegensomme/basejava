package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DateUtil;
import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                Section section = entry.getValue();
                if (section.getClass() == TextSection.class) {
                    textSectionSerializer.serialize(section, dos);
                    continue;
                }
                if (section.getClass() == ListSection.class) {
                    listSectionSerializer.serialize(section, dos);
                    continue;
                }
                if (section.getClass() == OrganizationSection.class) {
                    organisationSectionSerializer.serialize(section, dos);
                    continue;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                Class clazz;
                try {
                    clazz = Class.forName(dis.readUTF());
                } catch (ClassNotFoundException e) {
                    throw new StorageException("Error read resume", null, e);
                }
                if (clazz == TextSection.class) {
                    resume.addSection(sectionType, textSectionSerializer.deserialize(dis));
                    continue;
                }
                if (clazz == ListSection.class) {
                    resume.addSection(sectionType, listSectionSerializer.deserialize(dis));
                    continue;
                }
                if (clazz == OrganizationSection.class) {
                    resume.addSection(sectionType, organisationSectionSerializer.deserialize(dis));
                    continue;
                }
            }
            return resume;
        }
    }

    private static abstract class SectionSerializer {

        void serialize(Section section, DataOutputStream dos) throws IOException {
            dos.writeUTF(section.getClass().getName());
            serializeInstance(section, dos);
        }

        Section deserialize(DataInputStream dis) throws IOException {
            return deserializeInstance(dis);
        }

        abstract Section deserializeInstance(DataInputStream dis) throws IOException;

        abstract void serializeInstance(Section section, DataOutputStream dos) throws IOException;
    }

    private static SectionSerializer textSectionSerializer = new SectionSerializer() {
        @Override
        Section deserializeInstance(DataInputStream dis) throws IOException {
            return new TextSection(dis.readUTF());
        }

        @Override
        void serializeInstance(Section section, DataOutputStream dos) throws IOException {
            TextSection textSection = (TextSection) section;
            dos.writeUTF(textSection.getContent());
        }
    };

    private static SectionSerializer listSectionSerializer = new SectionSerializer() {
        @Override
        Section deserializeInstance(DataInputStream dis) throws IOException {
            int size = dis.readInt();
            List<String> items = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                items.add(dis.readUTF());
            }
            return new ListSection(items);
        }

        @Override
        void serializeInstance(Section section, DataOutputStream dos) throws IOException {
            ListSection listSection = (ListSection) section;
            List<String> items = listSection.getItems();
            dos.writeInt(items.size());
            for (String item : items) {
                dos.writeUTF(item);
            }
        }
    };

    private static SectionSerializer organisationSectionSerializer = new SectionSerializer() {

        Organization deserializeOrganisation(DataInputStream dis) throws IOException {
            String name = dis.readUTF();
            String url = dis.readUTF();
            url = "".equals(url) ? null : url;
            Link homePage = new Link(name, url);
            int size = dis.readInt();
            List<Organization.Position> positions = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                LocalDate startDate = LocalDate.parse(dis.readUTF());
                LocalDate endDate = LocalDate.parse(dis.readUTF());
                String title = dis.readUTF();
                String description = dis.readUTF();
                description = "".equals(description) ? null : description;
                positions.add(new Organization.Position(startDate, endDate, title, description));
            }
            return new Organization(homePage, positions);
        }

        void serializeOrganisation(Organization organization, DataOutputStream dos) throws IOException {
            Link homePage = organization.getHomePage();
            dos.writeUTF(homePage.getName());
            dos.writeUTF(Objects.requireNonNullElse(homePage.getUrl(), ""));
            List<Organization.Position> positions = organization.getPositions();
            dos.writeInt(positions.size());
            for (Organization.Position position : positions) {
                dos.writeUTF(position.getStartDate().toString());
                dos.writeUTF(position.getEndDate().toString());
                dos.writeUTF(position.getTitle());
                dos.writeUTF(Objects.requireNonNullElse(position.getDescription(), ""));
            }
        }

        @Override
        Section deserializeInstance(DataInputStream dis) throws IOException {
            int size = dis.readInt();
            List<Organization> organizations = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                organizations.add(deserializeOrganisation(dis));
            }
            return new OrganizationSection(organizations);
        }

        @Override
        void serializeInstance(Section section, DataOutputStream dos) throws IOException {
            OrganizationSection organizationSection = (OrganizationSection) section;
            List<Organization> organizations = organizationSection.getOrganizations();
            dos.writeInt(organizations.size());
            for (Organization organization : organizations) {
                serializeOrganisation(organization, dos);
            }
        }
    };

}
