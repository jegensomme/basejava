<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page import="java.util.Random" %>
<%@ page import="java.util.UUID" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" required></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <c:forEach var="type" items="${SectionType.values()}">
            <jsp:useBean id="type" type="ru.javawebinar.basejava.model.SectionType"/>
            <h3>${type.title}:</h3>
            <c:choose>
                <c:when test="${type == SectionType.OBJECTIVE}">
                    <%pageContext.setAttribute("textSection", ((TextSection)resume.getSection(type)));%>
                    <input type="text" size="60" name="${type.name()}" value="${textSection == null ? null : textSection.content}">
                </c:when>
                <c:when test="${type == SectionType.PERSONAL}">
                    <%pageContext.setAttribute("textSection", ((TextSection)resume.getSection(type)));%>
                    <textarea cols="75" rows="5" name="${type.name()}">${textSection == null ? null : textSection.content}</textarea>
                </c:when>
                <c:when test="${type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS}">
                    <%pageContext.setAttribute("listSection", ((ListSection)resume.getSection(type)));%>
                    <table cellpadding="5" id="${type.name()}">
                        <c:if test="${listSection != null}">
                            <c:forEach var="item" items="${listSection.items}">
                                <c:set var="itemId" value="${UUID.randomUUID().toString()}"/>
                                <tr id="${itemId}">
                                    <td >
                                        <textarea cols="75" rows="3" name="${type.name()}">${item}</textarea>
                                    </td>
                                    <td>
                                        <button type="button" onclick="deleteItem('${itemId}')">Удалить</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </table>
                    <button type="button" onclick="addItem('${type.name()}')">Добавить</button>
                </c:when>
                <c:when test="${type == SectionType.EXPERIENCE || type == SectionType.EDUCATION}">
                    <%pageContext.setAttribute("organisationSection", ((OrganizationSection)resume.getSection(type)));%>
                    <section id="${type.name()}">
                        <c:if test="${organisationSection != null}">
                            <c:forEach var="organisation" items="${organisationSection.organizations}">
                                <c:set var="orgId" value="${UUID.randomUUID().toString()}"/>
                                <section id="${orgId}">
                                    <input type="hidden" name="${type.name()}" value="${orgId}">
                                    <dl>
                                        <dt><b>Название:</b></dt>
                                        <dd><input type="text" name="${orgId}.name" size=50 value="${organisation.homePage.name}" required></dd>
                                    </dl>
                                    <dl>
                                        <dt>Ссылка:</dt>
                                        <dd><input type="text" name="${orgId}.url" size=50 value="${organisation.homePage.url}"></dd>
                                    </dl>
                                    <button type="button" onclick="addPosition('${orgId}')">Добавить позицию</button>
                                    <button type="button" onclick="deleteOrganisation('${orgId}')" style="margin-left: 5px">Удалить организацию</button>
                                    <table id="${orgId}.posTable" cellpadding="10">
                                        <c:forEach var="position" items="${organisation.positions}">
                                            <c:set var="posId" value="${UUID.randomUUID().toString()}"/>
                                            <c:set var="posTrId" value="${UUID.randomUUID().toString()}"/>
                                            <tr id="${posTrId}">
                                                <input type="hidden" name="${orgId}.position" value="${posId}">
                                                <td colspan="2">
                                                    <dl>
                                                        <dt><b>Позиция:</b></dt>
                                                        <dd><input type="text" name="${posId}.title" size=50 value="${position.title}" required></dd>
                                                    </dl>
                                                    <dl>
                                                        <dt>Начало:</dt>
                                                        <dd><input type="date" name="${posId}.startDate" size=50 value="${DateUtil.getNullIfAfterNow(position.startDate)}" required></dd>
                                                    </dl>
                                                    <dl>
                                                        <dt>Окончание:</dt>
                                                        <dd><input type="date", da name="${posId}.endDate" size=50 value="${DateUtil.getNullIfAfterNow(position.endDate)}"></dd>
                                                    </dl>
                                                    <dl>
                                                        <dt>Описание:</dt>
                                                        <dd><textarea name="${posId}.description" cols="75" rows="5">${position.description}</textarea></dd>
                                                    </dl>
                                                    <button type="button" onclick="deletePosition('${posTrId}')">Удалить</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                    <hr>
                                </section>
                            </c:forEach>
                        </c:if>
                    </section>
                    <button type="button" onclick="addOrganisation('${type.name()}')">Добавить организацию</button>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
<script src="js/edit.js"></script>
</body>
</html>

