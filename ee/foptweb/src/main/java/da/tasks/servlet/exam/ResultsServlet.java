package da.tasks.servlet.exam;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

@WebServlet(urlPatterns = "/aufgabe6/results-servlet")
public class ResultsServlet extends BaseServlet {

    private Results results;

    public void init() {

        results = (Results)getServletContext().getAttribute("results");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("reset") != null) {
            Enumeration<String> attributes = getServletContext().getAttributeNames();

            while (attributes.hasMoreElements()) {
                String att = attributes.nextElement();
                if (att.startsWith("exam-")) {
                    getServletContext().removeAttribute(att);
                }
            }
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

        int yesAnswersQ1Percentage = (int)(!yesAnswersQ1.isEmpty() ? (100d / answersQ1Size) * yesAnswersQ1.size() : 0);
        int noAnswersQ1Percentage = (int)(!noAnswersQ1.isEmpty() ? (100d / answersQ1Size) * noAnswersQ1.size() : 0);

        int yesAnswersQ2Percentage = (int)(!yesAnswersQ2.isEmpty() ? (100d / answersQ2Size) * yesAnswersQ2.size() : 0);
        int noAnswersQ2Percentage = (int)(!noAnswersQ2.isEmpty() ? (100d / answersQ2Size) * noAnswersQ2.size() : 0);



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
                "<input type=\"submit\" value=\"Zurücksetzen\" />" +
                "</form>" +
                "<form action=\"download-servlet\" method=\"GET\">" +
                "<input type=\"submit\" value=\"Download\" />" +
                "</form>";



    }


}
