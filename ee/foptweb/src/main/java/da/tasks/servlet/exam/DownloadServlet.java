package da.tasks.servlet.exam;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@WebServlet(urlPatterns = "/aufgabe6/download-servlet")
public class DownloadServlet extends BaseServlet {

    private Results results;

    public void init() {
        results = (Results)getServletContext().getAttribute("results");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain");
        response.addHeader("Content-Disposition", "attachment;filename=results.txt");

        setOutput(getText());

        response.getWriter().println(getOutput());

    }


    public synchronized String getText() {

        List<Integer> yesAnswersQ1 = results.getYesAnswers(Question.Q1);
        List<Integer> yesAnswersQ2 = results.getYesAnswers(Question.Q2);

        List<Integer> noAnswersQ1 = results.getNoAnswers(Question.Q1);
        List<Integer> noAnswersQ2 = results.getNoAnswers(Question.Q2);

        StringBuffer b = new StringBuffer();
        for (Integer yesAnswer: yesAnswersQ1) {
            b.append("exam-q1-").append(yesAnswer).append(": true");
        }
        for (Integer noAnswer: noAnswersQ1) {
            b.append("exam-q1-").append(noAnswer).append(": false");
        }
        for (Integer yesAnswer: yesAnswersQ2) {
            b.append("exam-q2-").append(yesAnswer).append(": true");
        }
        for (Integer noAnswer: noAnswersQ2) {
            b.append("exam-q2-").append(noAnswer).append(": false");
        }

        return b.toString();
    }





}
