package fopt6.sandbox.servletContexListener;

import fopt6.sandbox.util.Counter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(urlPatterns = "/sandbox/contextlistener-servlet/increment", loadOnStartup = 0)
public class ResetServlet extends HttpServlet {

    private Counter counter;

    public void init() {
        counter = (Counter) getServletContext().getAttribute("counter_2");
        System.out.println("init() accessing counter_2 in servlet context (via listener) at sandbox/contextlistener-servlet w/ " + counter);
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