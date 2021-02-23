package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = "".equals(uuid) ? new Resume() : storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    String value = request.getParameter(type.name());
                    if (value != null && value.trim().length() != 0) {
                        r.addSection(type, new TextSection(value));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String[] items = request.getParameterValues(type.name());
                    if (items != null) {
                        r.addSection(type, new ListSection(items));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    String[] orgIds = request.getParameterValues(type.name());
                    if (orgIds != null) {
                        List<Organization> organizations = new ArrayList<>(orgIds.length);
                        for (String orgId : orgIds) {
                            String name = request.getParameter(orgId + ".name");
                            String url = request.getParameter(orgId + ".url");
                            String[] posIds = request.getParameterValues(orgId + ".position");
                            List<Organization.Position> positions = null;
                            if (posIds != null) {
                                positions = new ArrayList<>(posIds.length);
                                for (String posId : posIds) {
                                    String title = request.getParameter(posId +".title");
                                    LocalDate startDate = LocalDate.parse(request.getParameter(posId +".startDate"));
                                    String endDateStr = (request.getParameter(posId +".endDate"));
                                    LocalDate endDate = endDateStr == "" ? DateUtil.NOW : LocalDate.parse(endDateStr);
                                    String description = request.getParameter(posId +".description");
                                    positions.add((new Organization.Position(startDate, endDate, title, description)));
                                }
                            }
                            organizations.add(new Organization(new Link(name, url), positions != null ? positions : new ArrayList<>()));
                        }
                        r.addSection(type, new OrganizationSection(organizations));
                    } else {
                        r.getSections().remove(type);
                    }
            }
        }
        if (r.isNew()) {
            r.setUuid(UUID.randomUUID().toString());
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "add":
                r = new Resume();
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
