package da.tasks.servlet.exam;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.util.Arrays;

@WebListener
public class MultiListener implements ServletContextListener, ServletContextAttributeListener {

    private Results results;


    public void contextInitialized(ServletContextEvent sce) {
        results = new Results();
        sce.getServletContext().setAttribute("results", results);
    }

    public void attributeAdded(ServletContextAttributeEvent event) {
        updateAnswerFromEvent(event);
    }

    public void contextDestroyed(ServletContextEvent event) {

    }

    public void attributeRemoved(ServletContextAttributeEvent event) {

    }

    public void attributeReplaced(ServletContextAttributeEvent event) {
        updateAnswerFromEvent(event);
    }

    private void updateAnswerFromEvent(ServletContextAttributeEvent event) {
        String key = event.getName();

        if (key.equals("results")) {
            return;
        }

        Object value = event.getValue();

        String question = "";
        Integer id = null;
        boolean isYes = false;


        String[] parts = key.split("-");
        System.out.println(Arrays.toString(parts));
        if (parts.length == 3) {
            question = parts[1];
            if (!question.equals("q1") && !question.equals("q2")) {
                return;
            }
            id = Integer.parseInt(parts[2]);
            isYes = (boolean)value;

        }

        if (id == null) {
            return;
        }

        Question q = question.equals("q1") ? Question.Q1 : Question.Q2;

        results.addAnswer(q, isYes, id);
    }

}
