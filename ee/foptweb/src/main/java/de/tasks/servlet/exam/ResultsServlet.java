package de.tasks.servlet.exam;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/aufgabe6/results-servlet")
public class ResultsServlet extends BaseServlet {

    Results results;

    public void init() {

        results = (Results)getServletContext().getAttribute("results");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("reset") != null) {
            results.clear();
        }

        setOutput(getHtml(getBody()));

        super.doGet(request, response);

    }


    public synchronized String getBody() {

        List<Integer> yesAnswersQ1 = results.getYesAnswers(Question.Q1);
        List<Integer> yesAnswersQ2 = results.getYesAnswers(Question.Q2);

        List<Integer> noAnswersQ1 = results.getNoAnswers(Question.Q1);
        List<Integer> noAnswersQ2 = results.getNoAnswers(Question.Q2);

        int answersQ1Size = yesAnswersQ1.size() + noAnswersQ1.size();
        int answersQ2Size = yesAnswersQ2.size() + noAnswersQ2.size();

        int yesAnswersQ1Percentage = !yesAnswersQ1.isEmpty() ? answersQ1Size / yesAnswersQ1.size() : 0;
        int noAnswersQ1Percentage = !noAnswersQ1.isEmpty() ? answersQ1Size / noAnswersQ1.size() : 0;

        int yesAnswersQ2Percentage = !yesAnswersQ2.isEmpty() ? answersQ2Size / yesAnswersQ2.size() : 0;
        int noAnswersQ2Percentage = !noAnswersQ2.isEmpty() ? answersQ2Size / noAnswersQ2.size() : 0;



        return "<table>" +
                "<tr>" +
                "<td>" + Question.Q1 +"</td>" +
                "<td>" + (answersQ1Size) + "</td>" +
                "<td>" + (yesAnswersQ1Percentage ) + "%</td>" +
                "<td>" + (noAnswersQ1Percentage ) + "%</td>" +
                "</tr>" +
                "<tr>" +
                "<td>" + Question.Q2 +"</td>" +
                "<td>" + (answersQ2Size) + "</td>" +
                "<td>" + (yesAnswersQ2Percentage ) + "%</td>" +
                "<td>" + (noAnswersQ2Percentage ) + "%</td>" +
                "</tr>" +
                "</table>" +
                "<form method=\"GET\">" +
                "<input type=\"hidden\" name=\"reset\" value=\"1\" />" +
                "<input type=\"submit\" value=\"Zu&uuml;cksetzen\" />" +
                "</form>";


    }


}
