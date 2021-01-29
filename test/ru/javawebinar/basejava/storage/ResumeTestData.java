package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ResumeTestData {

    public static List<Contact> contacts;
    static {
        try {
            contacts = List.of(
                    new Contact(ContactType.PHONE, new Link("+7(921) 855-0482", null)),
                    new Contact(ContactType.SKYPE, new Link("grigory.kislin", null)),
                    new Contact(ContactType.EMAIL, new Link("gkislin@yandex.ru", null)),
                    new Contact(ContactType.LINKEDIN, new Link(null, new URL("https://www.linkedin.com/authwall?trk=ripf&trkInfo=AQGstIVZlbY8RQAAAXdJ-j9oFcSfJZ0fprgUt27-rPTORYSicwsmrxFS11WVjELaQK1KQIQFEjvJoZbUiPO7t2diLculPZYnWTSYQaMu7aFfTYhZOi4FdXF1NEfRjMsUY_tIa78=&originalReferer=https://javawebinar.github.io/&sessionRedirect=https%3A%2F%2Fwww.linkedin.com%2Fin%2Fgkislin"))),
                    new Contact(ContactType.GITHUB, new Link(null, new URL("https://github.com/gkislin"))),
                    new Contact(ContactType.STACKOVERFLOW, new Link(null, new URL("https://stackoverflow.com/users/548473/grigory-kislin"))),
                    new Contact(ContactType.HOME_PAGE, new Link(null, new URL("http://gkislin.ru/")))
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static Section objective = new TextSection(SectionType.OBJECTIVE, "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

    public static Section personal = new TextSection(SectionType.PERSONAL, "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

    public static Section achievement = new ListSection(SectionType.ACHIEVEMENT, List.of(
            """
                   С 2013 года: разработка проектов "Разработка Web приложения","Java Enterprise", "Многомодульный maven.
                   Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)".
                   Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.
                    """,
            """
                    Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, 
                    DuoSecurity, Google Authenticator, Jira, Zendesk.
                    """,
            """
                    Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, 
                    CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO 
                    аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.
                    """,
            """
                    Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), 
                    Commet, HTML5, Highstock для алгоритмического трейдинга.
                    """,
            """
                    Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, 
                    JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. 
                    Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).
                    """,
            """
                    Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, 
                    Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.
                    """
    ));

    public static Section qualifications = new ListSection(SectionType.QUALIFICATIONS, List.of(
            "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
            "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
            "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
            "MySQL, SQLite, MS SQL, HSQLDB",
            "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
            "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
            """
                    Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA 
                    (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, 
                    Selenium (htmlelements).
                    """,
            "Python: Django.",
            "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
            "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
            """
                    Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, 
                    JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.
                    """,
            "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
            """
                    администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, 
                    pgBouncer.
                    """,
            """
                    Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, 
                    функционального программирования
                    """,
            "Родной русский, английский \"upper intermediate\""
    ));

    public static Section experience;

    static {
        try {
            experience = new OrganisationSection(SectionType.EXPERIENCE, List.of(
                    new Organisation(new Link("Java Online Projects", new URL("https://javaops.ru/")),
                            "10/2013 - Сейчас", "Автор проекта.",
                            "Создание, организация и проведение Java онлайн проектов и стажировок."),
                    new Organisation(new Link("Wrike", new URL("https://www.wrike.com/")),
                            "10/2014 - 01/2016", "Старший разработчик (backend)",
                            """
                                    Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, 
                                    MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, 
                                    JWT SSO.
                                    """)
            ));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static Section education;

    static {
        try {
            education = new OrganisationSection(SectionType.EDUCATION, List.of(
                    new Organisation(new Link("Coursera", new URL("https://www.coursera.org/learn/progfun1")),
                            "03/2013 - 05/2013", "Functional Programming Principles in Scala\" by Martin Odersky",
                            null)
            ));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static List<Section> sections = List.of(objective, personal, achievement, qualifications, experience, education);

    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин", contacts, sections);
        System.out.println(resume.getFullName() + '\n');
        resume.getContacts().forEach(contact -> System.out.println(contact));
        resume.getSections().forEach(section -> System.out.println(section));
    }
}
