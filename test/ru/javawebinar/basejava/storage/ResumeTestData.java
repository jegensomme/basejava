package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {

    public static final Organization JAVA_OPS = new Organization("Java Online Projects", "https://javaops.ru/");

    public static final Organization WRIKE = new Organization("wrike", "https://www.wrike.com/");

    public static final Organization COURSERA = new Organization("Coursera", "https://www.coursera.org/learn/progfun1");

    public static final Organization LUXOFT = new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366");

    public static Resume getNew(String uuid, String fullName) {
        Map<ContactType, String> contacts = new HashMap<>(Map.of(
                ContactType.PHONE, "+7(921) 855-0482",
                ContactType.SKYPE, "grigory.kislin",
                ContactType.MAIL, "gkislin@yandex.ru",
                ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin",
                ContactType.GITHUB, "https://github.com/gkislin",
                ContactType.STATCKOVERFLOW, "https://stackoverflow.com/users/548473"
        ));
        Map<SectionType, Section> sections = new HashMap<>(Map.of(
                SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"),
                SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."),
                SectionType.ACHIEVEMENT, new ListSection(List.of(
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
                )),
                SectionType.QUALIFICATIONS, new ListSection(List.of(
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
                )),
                SectionType.EXPERIENCE, new OrganizationSection(List.of(
                        new OrganisationParticipation(JAVA_OPS,
                                LocalDate.of(2013, 10, 1), LocalDate.now(), "Автор проекта.",
                                "Создание, организация и проведение Java онлайн проектов и стажировок."),
                        new OrganisationParticipation(WRIKE,
                                LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1), "Старший разработчик (backend)",
                                """
                                        Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, 
                                        MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, 
                                        JWT SSO.
                                        """)
                )),
                SectionType.EDUCATION, new OrganizationSection(List.of(
                        new OrganisationParticipation(COURSERA,
                                LocalDate.of(2013, 3, 1), LocalDate.of(2013, 3, 30),
                                "Functional Programming Principles in Scala\" by Martin Odersky", null),
                        new OrganisationParticipation(LUXOFT,
                                LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1),
                                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null))
                )));
        return new Resume(uuid, fullName, contacts, sections);
    }
}
