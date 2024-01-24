package da.tasks.servlet.exam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
public class BaseServlet extends HttpServlet {


    protected String output;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter p = response.getWriter();

        p.println(getOutput());

        p.flush();

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{



    }


    protected String getOutput() {
        return output;
    }

    protected void setOutput(String str) {
        output = str;
    }

    protected String getHtml(String body) {
        return "<html><head></head><body>" + body + "</body></html>";
    }



}
