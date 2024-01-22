package de.tasks.servlet.exam;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
public class BaseServlet extends HttpServlet {


    protected String output;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
