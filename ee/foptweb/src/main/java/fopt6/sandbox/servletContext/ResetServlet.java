package fopt6.sandbox.servletContext;

import fopt6.sandbox.util.Counter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * loadOnStartup needs to hold a value less than the value assigned to this attribute in IncrementServlet,
 * since IncrementServlet relies on the availability of this property.
 */
@WebServlet(urlPatterns = "/sandbox/context-servlet/increment", loadOnStartup = 1)
public class ResetServlet extends HttpServlet {

    private Counter counter;

    public void init() {
        counter = new Counter();
        getServletContext().setAttribute("counter", counter);
        System.out.println("init() registering counter in servlet context at sandbox/util-servlet w/ " + counter);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType(("text/html"));
        boolean reset = request.getParameter("reset") != null;
        boolean increment = request.getParameter("increment") != null;

        if (reset) {
            counter.reset();
        } else if (increment) {
            counter.increment();
        }

        response.getWriter().println(
                "<form method=\"POST\">" +
                        "<div> "  + counter + "</div>" +
                        "<input type=\"submit\" name=\"reset\" value=\"reset\"/>" +
                        "<input type=\"submit\" name=\"increment\" value=\"increment\" />" +
                        "</form>"
        );


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        doGet(request, response);

    }

}