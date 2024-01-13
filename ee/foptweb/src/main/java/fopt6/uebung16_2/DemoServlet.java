package fopt6.uebung16_2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/uebung16_2/demo-servlet")
public class DemoServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.getWriter().println(
                "<a href=\"increment-servlet\">IncrementServlet</a><br />" +
                "<a href=\"reset-servlet\">ResetServlet</a><br />"
        );



    }

}
