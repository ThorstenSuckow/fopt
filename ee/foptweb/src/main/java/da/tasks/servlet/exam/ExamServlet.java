package da.tasks.servlet.exam;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletContext;
import java.awt.*;
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
            saveToContainer(key, value, castType, containerType);
        }

        public void saveToContainer(String key, Object value, CastType castType, ContainerType cType) {
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

            if (cType == ContainerType.SESSION) {
                request.getSession().setAttribute(key, v);
            } else if (cType == ContainerType.COOKIES) {
                response.addCookie(new Cookie(key, String.valueOf(v)));
            } else if (cType == ContainerType.SERVLET_CONTEXT) {
                ServletContext s = getServletContext();
                s.setAttribute(key, v);
            }
        }

        public String getFromContainer(String key) {
            return getFromContainer(key, ContainerType.SERVLET_CONTEXT);
        }
        public String getFromContainer(String key, ContainerType cType) {

            Object v =  null;

            if (cType == ContainerType.SESSION) {
                v = request.getSession().getAttribute(key);
            } else if (cType == ContainerType.COOKIES) {
                Cookie[] c = request.getCookies();

                for (int i = 0; c != null && i < c.length; i++) {
                    if (c[i].getName().equals(key)) {
                        v = c[i].getValue();
                        break;
                    }
                }

            } else if (cType == ContainerType.SERVLET_CONTEXT) {
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


    protected void persistValue(
        String key,
        String value,
        RequestContext requestContext,
        RequestContext.CastType saveAs,
        RequestContext.ContainerType containerType) {
        requestContext.saveToContainer(key, value, saveAs, containerType);
    }

    protected void persistValue(String key, String value, RequestContext requestContext, RequestContext.CastType saveAs) {
        requestContext.saveToContainer(key, value, saveAs, RequestContext.ContainerType.SERVLET_CONTEXT);
    }


    protected String getPersistedValue(String key, RequestContext requestContext) {
        return requestContext.getFromContainer(key, RequestContext.ContainerType.SERVLET_CONTEXT);
    }

    protected String getPersistedValue(String key, RequestContext requestContext, RequestContext.ContainerType containerType) {
        return requestContext.getFromContainer(key, containerType);
    }

    protected void processParams(HttpServletRequest request, RequestContext requestContext) {

        RequestContext.ContainerType cookieContainer =  RequestContext.ContainerType.COOKIES;
        RequestContext.CastType booleanType = RequestContext.CastType.BOOLEAN;
        RequestContext.CastType stringType = RequestContext.CastType.STRING;

        String id = getPersistedValue(toKey("id"), requestContext, cookieContainer);

        if (id == null || id.trim().isEmpty()) {
            id = request.getParameter("id");
            if (id != null && !id.trim().isEmpty()) {
                persistValue(toKey("id"), id, requestContext, stringType);
                persistValue("exam-id", id, requestContext, stringType, cookieContainer);
            }
        }

        if (id != null && !id.trim().isEmpty()) {
            System.out.println("persist for " + id);
            persistValue(toKey("q1", id), request.getParameter("q1"), requestContext, booleanType);
            persistValue(toKey("q2", id), request.getParameter("q2"), requestContext, booleanType);
            persistValue("exam-q1", request.getParameter("q1"), requestContext, booleanType, cookieContainer);
            persistValue("exam-q2", request.getParameter("q2"), requestContext, booleanType, cookieContainer);

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

        String q1Raw = getPersistedValue(toKey("q1", id), requestContext);
        String q2Raw = getPersistedValue(toKey("q2", id), requestContext);

        if (q1Raw != null) {
            q1 = Boolean.parseBoolean(q1Raw);
        }

        if (q2Raw != null) {
            q2 = Boolean.parseBoolean(q2Raw);
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
