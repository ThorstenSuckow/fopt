package fopt6.sandbox;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/sandbox/sleep-servlet")
public class SleepServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        long timeout = Long.parseLong(
                request.getParameter("timeout") != null ? request.getParameter("timeout") : "0");

        try {
            Thread.sleep(timeout);
        } catch (InterruptedException ignored) {

        }

        response.setContentType("text/html");

        response.getWriter().println(
                "<form method=\"POST\">" +
                    "<select name=\"timeout\">" +
                        "<option value=\"1000\">1000ms</option>" +
                        "<option value=\"2000\">2000ms</option>" +
                        "<option value=\"3000\">3000ms</option>" +
                        "<option value=\"4000\">4000ms</option>" +
                    "</select>" +
                    "<input type=\"submit\" />" +
                "</form>"
        );



    }

    public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        doGet(request, response);

    }

}
