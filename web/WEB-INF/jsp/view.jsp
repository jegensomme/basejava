<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <p></p>
    <hr/>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
            <tr>
                <td colspan="2">
                    <h2><a name="type.name">${sectionEntry.key.title}</a></h2>
                </td>
            </tr>
            <c:choose>
                <c:when test="${sectionEntry.key == SectionType.PERSONAL}">
                    <tr>
                        <td colspan="2">
                            <h3><%=((TextSection)sectionEntry.getValue()).getContent()%></h3>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${sectionEntry.key == SectionType.OBJECTIVE}">
                    <tr>
                        <td colspan="2">
                            <%=((TextSection)sectionEntry.getValue()).getContent()%>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${sectionEntry.key == SectionType.ACHIEVEMENT || sectionEntry.key == SectionType.QUALIFICATIONS}">
                    <tr>
                        <td colspan="2">
                            <ul>
                                <c:forEach var="item" items="<%=((ListSection)sectionEntry.getValue()).getItems()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${sectionEntry.key == SectionType.EXPERIENCE || sectionEntry.key == SectionType.EDUCATION}">
                    <c:forEach var="organisation" items="<%=((OrganizationSection)sectionEntry.getValue()).getOrganizations()%>">
                        <tr>
                            <td colspan="2">
                                <h3>
                                    <a href="${organisation.homePage.url}">${organisation.homePage.name}</a>
                                </h3>
                            </td>
                        </tr>
                        <tr>
                            <c:forEach var="position" items="${organisation.positions}">
                                <tr>
                                    <td width="15%" style="vertical-align: top">${DateUtil.getPeriod(position.startDate, position.endDate)}</td>
                                    <td>
                                        <b>${position.title}</b>
                                        <br>${position.description}
                                    </td>
                                </tr>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

