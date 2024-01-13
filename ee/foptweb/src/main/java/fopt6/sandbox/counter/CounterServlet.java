package fopt6.sandbox.counter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/sandbox/counter-servlet", loadOnStartup = 1)
public class CounterServlet extends HttpServlet {

    private Counter counter;

    public void init() {
        counter = new Counter();
        System.out.println("init() sandbox/counter-servlet w/ " + counter);
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
