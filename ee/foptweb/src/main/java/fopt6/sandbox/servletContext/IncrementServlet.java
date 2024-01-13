package fopt6.sandbox.servletContext;

import fopt6.sandbox.util.Counter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/sandbox/context-servlet/reset", loadOnStartup = 2)
public class IncrementServlet extends HttpServlet {

    private Counter counter;

    /**
     * if counter is not available in the ServletContext, doGet() will produce a 500 internal server error
     * once the method tries to access it (@see ResetServlet#loadOnStartup).
     */
    public void init() {
        counter = (Counter) getServletContext().getAttribute("counter");
        System.out.println("init() accessing servlet context in sandbox/context-servlet w/ " + counter);
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