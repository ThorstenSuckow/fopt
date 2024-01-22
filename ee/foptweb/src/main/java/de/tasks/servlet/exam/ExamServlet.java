package de.tasks.servlet.exam;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/aufgabe6/exam-servlet")
public class ExamServlet extends BaseServlet {

    protected HttpServletRequest requestContext;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String storageContext = "session";

        requestContext = request;

        processParams(request, storageContext);

        setOutput(getHtml(getForm(storageContext)));

        super.doGet(request, response);

    }


    protected void persistValue(String key, String value, String saveAs, String container) {
        ServletContext s = getServletContext();

        if (value == null) {
            return;
        }

        if (saveAs.equals("bool")) {
            if (value.equals("y")) {
                s.setAttribute(key, Boolean.TRUE);
                requestContext.getSession().setAttribute(key, Boolean.TRUE);
            } else if (value.equals("n")) {
                s.setAttribute(key, Boolean.FALSE);
                requestContext.getSession().setAttribute(key, Boolean.FALSE);
            }
            return;
        }

        s.setAttribute(key, value);
        requestContext.getSession().setAttribute(key, value);
    }


    protected String getPersistedValue(String key, String container) {
        Object v;
        if (container.equals("session")) {
            v = requestContext.getSession().getAttribute(key);
        } else if (container.equals("cookie")) {
            throw new RuntimeException();//return String.valueOf(requestContext.getCookies().ggetAttribute(key));
        } else if (container.equals("context")){
            v = getServletContext().getAttribute(key);
        } else {
            throw new RuntimeException();
        }


        return v != null ? String.valueOf(v) : null;
    }


    protected void processParams(HttpServletRequest request, String container) {

        String id = getPersistedValue(toKey("id"), container);

        if (id == null || id.trim().isEmpty()) {
            id = request.getParameter("id");
            if (id != null && !id.trim().isEmpty()) {
                persistValue(toKey("id"), id, "string", container);
            }
        }

        if (id != null && !id.trim().isEmpty()) {
            persistValue(toKey("q1", id), request.getParameter("q1"), "bool", container);
            persistValue(toKey("q2", id), request.getParameter("q2"), "bool", container);
        }

    }

    private String toKey(String prefix) {
        return "exam-"+prefix;
    }

    private String toKey(String prefix, String attribute) {
        return prefix.equals("id") ? "exam-id" : "exam-" + prefix + "-" + attribute;
    }

    private String getForm(String container) {

        String id = String.valueOf(getPersistedValue(toKey("id"), container));


        boolean q1 = Boolean.parseBoolean(getPersistedValue(toKey("q1", id), container));
        boolean q2 = Boolean.parseBoolean(getPersistedValue(toKey("q2", id), container));

        return "<form method=\"GET\">" +
                "<p><input type=\"number\" value=\"" + id + "\" name=\"id\" /> Pruefungsnummer</p>" +
                "<p>3 + 1 = 4?</p>"+
                "<p><input type=\"radio\" name=\"q1\" " + (q1 ? "checked=\"checked\"" : "") + " value=\"y\" /> Ja</p>"+
                "<p><input type=\"radio\" name=\"q1\" " + (!q1 ? "checked=\"checked\"" : "") + " value=\"n\" /> Nein</p>"+
                "<p>5 + 6 = 10?</p>"+
                "<p><input type=\"radio\" name=\"q2\" " + (q2 ? "checked=\"checked\"" : "") + " value=\"y\" /> Ja</p>"+
                "<p><input type=\"radio\" name=\"q2\" " + (!q2 ? "checked=\"checked\"" : "") + " value=\"n\" /> Nein</p>"+
                "<p><input type=\"submit\" value=\"Einreichen\" /></p>" +
                "</form>";

    }

}
