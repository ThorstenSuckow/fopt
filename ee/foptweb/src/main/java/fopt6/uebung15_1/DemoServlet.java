package fopt6.uebung15_1;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/uebung15_1/demo-servlet")
public class DemoServlet extends HttpServlet {

    private int refresh;

    public void init() {
        refresh = 1;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        /**
         * @see <a href="https://en.wikipedia.org/wiki/Meta_refresh#Alternatives"/>https://en.wikipedia.org/wiki/Meta_refresh#Alternatives</a>
         */
        if (request.getParameter("meta") == null || !request.getParameter("meta").equals("1")) {
           response.setHeader("Refresh", String.valueOf(refresh));
        }
        pw.println("<html><head>");//
        // ?meta=1
        if (request.getParameter("meta") != null && request.getParameter("meta").equals("1")) {
            pw.println("<meta http-equiv=\"refresh\" content=\"" + refresh + "\" />");
        }
        pw.println("</head><body>");
        pw.println(new java.util.Date());
        pw.println("</body></html>");

    }

}
