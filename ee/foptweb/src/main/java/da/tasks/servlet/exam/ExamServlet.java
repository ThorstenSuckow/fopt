package da.tasks.servlet.exam;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletContext;
import java.io.IOException;

@WebServlet(urlPatterns = "/aufgabe6/exam-servlet")
public class ExamServlet extends BaseServlet {

    class RequestContext {

        enum CastType {
            BOOLEAN,

            STRING
        }
        enum ContainerType {
            SESSION,
            COOKIES,

            SERVLET_CONTEXT
        }
        private final HttpServletRequest request;

        private final HttpServletResponse response;

        private final ContainerType containerType;
        public RequestContext(HttpServletRequest request, HttpServletResponse response, ContainerType containerType) {
            this.request = request;
            this.response = response;
            this.containerType = containerType;
        }

        public void saveToContainer(String key, Object value, CastType castType) {
            ServletContext s = getServletContext();

            if (value == null) {
                return;
            }

            Object v = value;

            if (castType == CastType.BOOLEAN) {
                if (value.equals("y")) {
                    v = Boolean.TRUE;
                } else if (value.equals("n")) {
                    v = Boolean.FALSE;
                }
            }

            s.setAttribute(key, v);

            if (containerType == ContainerType.SESSION) {
                request.getSession().setAttribute(key, v);
            } else if (containerType == ContainerType.COOKIES) {
                response.addCookie(new Cookie(key, String.valueOf(v)));
            }
        }
        public String getFromContainer(String key) {

            Object v =  null;

            if (containerType == ContainerType.SESSION) {
                v = request.getSession().getAttribute(key);
            } else if (containerType == ContainerType.COOKIES) {
                Cookie[] c = request.getCookies();

                for (int i = 0; c != null && i < c.length; i++) {
                    if (c[i].getName().equals(key)) {
                        v = c[i].getValue();
                        break;
                    }
                }

            } else if (containerType == ContainerType.SERVLET_CONTEXT) {
                v = getServletContext().getAttribute(key);
            } else {
                throw new RuntimeException();
            }

            return v != null ? String.valueOf(v) : null;
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestContext requestContext = new RequestContext(
                request, response, RequestContext.ContainerType.COOKIES
        );

        processParams(request, requestContext);

        setOutput(getHtml(getForm(requestContext)));

        super.doGet(request, response);

    }


    protected void persistValue(String key, String value, RequestContext requestContext, RequestContext.CastType saveAs) {
        requestContext.saveToContainer(key, value, saveAs);
    }


    protected String getPersistedValue(String key, RequestContext requestContext) {
        return requestContext.getFromContainer(key);
    }


    protected void processParams(HttpServletRequest request, RequestContext requestContext) {

        String id = getPersistedValue(toKey("id"), requestContext);

        if (id == null || id.trim().isEmpty()) {
            id = request.getParameter("id");
            if (id != null && !id.trim().isEmpty()) {
                persistValue(toKey("id"), id, requestContext, RequestContext.CastType.STRING);
            }
        }

        if (id != null && !id.trim().isEmpty()) {
            persistValue(toKey("q1", id), request.getParameter("q1"), requestContext, RequestContext.CastType.BOOLEAN);
            persistValue(toKey("q2", id), request.getParameter("q2"), requestContext, RequestContext.CastType.BOOLEAN);
        }

    }

    private String toKey(String prefix) {
        return "exam-"+prefix;
    }

    private String toKey(String prefix, String attribute) {
        return prefix.equals("id") ? "exam-id" : "exam-" + prefix + "-" + attribute;
    }

    private String getForm(RequestContext requestContext) {

        String id = String.valueOf(getPersistedValue(toKey("id"), requestContext));


        Boolean q1 = null;
        Boolean q2 = null;

        if (getPersistedValue(toKey("q1", id), requestContext) != null) {
            q1 = Boolean.parseBoolean(getPersistedValue(toKey("q1", id), requestContext));
        }

        if (getPersistedValue(toKey("q2", id), requestContext) != null) {
            q2 = Boolean.parseBoolean(getPersistedValue(toKey("q2", id), requestContext));
        }


        return "<form method=\"GET\">" +
                "<p><input type=\"number\" value=\"" + id + "\" name=\"id\" /> Pruefungsnummer</p>" +
                "<p>3 + 1 = 4?</p>"+
                "<p><input type=\"radio\" name=\"q1\" " + (q1 != null && q1 ? "checked=\"checked\"" : "") + " value=\"y\" /> Ja</p>"+
                "<p><input type=\"radio\" name=\"q1\" " + (q1 != null && !q1 ? "checked=\"checked\"" : "") + " value=\"n\" /> Nein</p>"+
                "<p>5 + 6 = 10?</p>"+
                "<p><input type=\"radio\" name=\"q2\" " + (q2 != null && q2 ? "checked=\"checked\"" : "") + " value=\"y\" /> Ja</p>"+
                "<p><input type=\"radio\" name=\"q2\" " + (q2 != null && !q2 ? "checked=\"checked\"" : "") + " value=\"n\" /> Nein</p>"+
                "<p><input type=\"submit\" value=\"Einreichen\" /></p>" +
                "</form>";

    }

}
