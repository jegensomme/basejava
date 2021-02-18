package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        List<Resume> resumes = storage.getAllSorted();
        StringBuilder htmlBuilder = new StringBuilder("""
                <table>
                     <tr>
                       <td>ID</td>
                       <td>Полное имя</td>
                     </tr>
                """);
        resumes.forEach(resume -> {
            htmlBuilder.append("\n  <tr>");
            htmlBuilder.append("\n      <td>" + resume.getUuid() + "</td>\n");
            htmlBuilder.append("\n      <td>" + resume.getFullName() + "</td>\n");
            htmlBuilder.append("\n  </tr>");
        });
        htmlBuilder.append("</table>");
        response.getWriter().write(htmlBuilder.toString());
    }
}
