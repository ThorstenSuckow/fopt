package praktischephase;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@WebServlet(urlPatterns="/praktischephase/gaestebuch")
public class Gaestebuch extends HttpServlet {

    private static final String LOGIN_KEY = "login";
    private record User(String name) {

    }
    private record Entry(String name, String post, LocalDateTime date) {

    }

    List<Entry> entries;

    public void init() {
        entries = new ArrayList<>();

        entries.add(new Entry("Max", "Super Webseite, bookmarked!", LocalDateTime.now()));
        entries.add(new Entry("Maria", "Essen spät geliefert (20Min nach Anruf!!!1) kalt angekommen 3/5", LocalDateTime.now()));
        entries.add(new Entry("Checker", "folgt mir für mehr rezepte", LocalDateTime.now()));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html");

        try {
            PrintWriter writer = response.getWriter();
            HttpSession session = request.getSession();

            boolean signedIn = false;

            synchronized(session) {
                if (session.getAttribute(LOGIN_KEY) != null) {
                    signedIn = true;
                }
            }

            if (!signedIn) {
                writer.print(getLoginForm() + " " +  getEntriesAsHtml());
            } else {
                writer.println(getPageAsSignedInUser(request));
            }

            writer.flush();
        } catch (IOException e) {
            response.setStatus(500);
        }

    }


    private String getLoginForm() {
        return "<form method=\"POST\">" +
                "<input type=\"text\" name=\"user\" />" +
                "<input type=\"submit\" value=\"login\" />" +
                "</form>";

    }

    private synchronized void processLogin(HttpServletRequest request, HttpServletResponse response) {

        String user = request.getParameter("user");
        HttpSession session = request.getSession();

        if (user == null || user.isEmpty()) {
            session.setAttribute(LOGIN_KEY, null);
        } else {
            session.setAttribute(LOGIN_KEY, new User(user));
        }

        doGet(request, response);
    }
    private String getPageAsSignedInUser(HttpServletRequest request) {
        return getForm(request) + " " + getEntriesAsHtml();
    }

    protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response) {

        if (request.getParameter("user") != null) {
            processLogin(request, response);
            return;
        }

        if (!request.getParameter("comment").isEmpty()) {

            String userName = "";
            HttpSession session = request.getSession();
            synchronized(session) {
                User user = (User)session.getAttribute(LOGIN_KEY);
                userName = user.name();
            }

            entries.add(0, new Entry(
                userName,
                request.getParameter("comment"),
                    LocalDateTime.now()
            ));
        }
        try {
            response.sendRedirect("./gaestebuch");
        } catch (IOException e) {

        }

    }

    private String getForm(HttpServletRequest request) {

        String userName = "";
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "FEHLER!";
        }

        synchronized(session) {
            User user = (User)session.getAttribute(LOGIN_KEY);
            if (user != null) {
                userName = user.name();
            }
        }

        return "<div style=\"background:#c0c0c0\"><form method=\"post\">" +
                "<div>Name: " +  userName + "</div>" + //<input type=\"text\" name=\"name\" /></div>" +
                "<div><textarea name=\"comment\"></textarea></div>" +
                "<input type =\"submit\" value=\"Abschicken\"/></form></div>";
    }


    private synchronized String getEntriesAsHtml() {

        StringBuilder html = new StringBuilder();
        String dateString = "";
        String pattern = "MM.dd.yyyy HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        for (Entry entry: entries) {
            dateString = entry.date().format(formatter);
            html.append("<div><div>" +
                entry.name() +
                " schreibt am " + dateString +  ": </div><div>" +
                entry.post() +
                "</div></div><hr />"
            );
        }

        return html.toString();

    }

}
