package praktischephase;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@WebServlet(urlPatterns="/praktischephase/wiki")
public class Wiki extends HttpServlet {

    private static final int LOCKING_DURATION = 10;
    private record EditStamp(LocalDateTime date, String userId){

    }

    private record Entry(String text) {

    }

    /**
     * This wiki represents one entry.
     */
    private Entry entry;

    private EditStamp editStamp;


    public void init() {
        entry = new Entry(" - ready to be edited - ");
    }

    protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response) {

        editStamp = getEditStamp(request);
        response.setContentType("text/html");

        String body = getEntryAsHtml();

        try {
            PrintWriter writer = response.getWriter();

            writer.println(getEditForm(request));
            writer.println(getEntryAsHtml());


        } catch (IOException e) {
            response.setStatus(500);
        }
    }


    private HttpSession createSession(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") == null) {
            session.setAttribute("id", generateId());
        }

        return session;
    }

    private EditStamp getEditStamp(HttpServletRequest request) {
        HttpSession session = createSession(request);

        if (editStamp == null) {
            editStamp = new EditStamp(LocalDateTime.now(), session.getId());
        }

        return editStamp;
    }

    private EditStamp renewEditStamp(HttpServletRequest request) {
        HttpSession session = createSession(request);
        editStamp = new EditStamp(LocalDateTime.now(), session.getId());
        return editStamp;
    }

    /**
     * Editing follows in most parts the proposed solution in [439, Oec22]
     * @param request
     * @param response
     */
    protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = createSession(request);
        editStamp = getEditStamp(request);

        boolean mayEdit = true;
        if (!editStamp.userId().equals(session.getId())) {
            mayEdit = false;
            if (Duration.between(editStamp.date(), LocalDateTime.now()).getSeconds() > LOCKING_DURATION) {
                renewEditStamp(request);
                mayEdit = true;
            }
        }

        String text = request.getParameter("text");
        if (mayEdit && text != null && !text.isEmpty()){
            entry = new Entry(text);
        }

        doGet(request, response);
    }


    private String generateId() {
        byte[] array = new byte[32];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }


    private String getEntryAsHtml() {

        return "<div>" + entry.text() + "</div>";

    }

    private String getEntryAsPlainText() {

        return entry.text();

    }


    private String getEditForm(HttpServletRequest request) {

        editStamp = getEditStamp(request);

        String text = getEntryAsPlainText();
        String info = "";

        if (!editStamp.userId().equals(request.getSession().getId())) {
            text = request.getParameter("text");
            info = "<span style=\"color:red; font-weight: bold;\">Editing not allowed.<br /> " +
                    editStamp.userId() + " locked at " + editStamp.date() + "</span> ";
        }

        return "<div style=\"background:#c0c0c0\"><form method=\"post\">" +
                "<div><textarea name=\"text\">" +
                text +
                "</textarea></div>" + info +
                "<input type =\"submit\" value=\"Speichern\"/></form></div>";
    }


}
