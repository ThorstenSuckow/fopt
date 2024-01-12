package fopt6.sandbox;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(name = "formServlet", value = "/sandbox/form-servlet")
public class FormServlet extends HttpServlet {
    private String message;

    public void init() {

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();

        open(out);

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {

            String name = parameterNames.nextElement();
            String[] values = request.getParameterValues(name);
            for (String value : values) {
                out.println("<div>" + request.getMethod() +"-Parameter \"name\" was: " + value + " </div>");
            }

        }

        printPostForm(out);
        printGetForm(out);
        close(out);
    }

    private void printPostForm(PrintWriter out) {
        out.println("<h1>POST</h1><form action=\"sandbox/form-servlet\" method=\"POST\">");
        out.println("<input type=\"text\" name=\"name\" value=\"Name1\" />");
        out.println("<input type=\"text\" name=\"name\" value=\"Name2\" />");
        out.println("<input type=\"reset\" /> ");
        out.println("<input type=\"submit\" /> </form>");
    }

    private void open(PrintWriter pw) {
        pw.println("<html><head></head><body>");
    }

    private void close(PrintWriter pw) {
        pw.println("</body></html>");
    }

    private void printGetForm(PrintWriter out) {
        out.println("<h1>GET</h1><form method=\"GET\">");
        out.println("<input type=\"text\" name=\"name\" value=\"Name\" />");

        out.println("<input type=\"reset\" /> ");
        out.println("<input type=\"submit\" /> </form>");
    }

    /**
     * Also called during redeploy.
     */
    public void destroy() {
        System.out.println("Bye!");
    }
}