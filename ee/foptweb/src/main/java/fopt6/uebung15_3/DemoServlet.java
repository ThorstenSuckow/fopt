package fopt6.uebung15_3;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(urlPatterns = "/uebung15_3/demo-servlet", loadOnStartup = 1)
public class DemoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // changes once server gets (re)deployed - until then, one object of this servlet
        // serves all requests
        response.getWriter().println(this + " " + Integer.toHexString(hashCode()));
    }

}
