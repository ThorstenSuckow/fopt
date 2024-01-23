package da.tasks.servlet.exam;

import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.util.Arrays;

@WebListener
public class MultiListener implements ServletContextListener, ServletContextAttributeListener {

    private Results results;


    public void contextInitialized(javax.servlet.ServletContextEvent sce) {
        results = new Results();
        sce.getServletContext().setAttribute("results", results);
    }

    public void attributeAdded(javax.servlet.ServletContextAttributeEvent event) {
        String key = event.getName();
        Object value = event.getValue();

        boolean isYes = (boolean)value;
        String question = "";
        Integer id = null;

        String[] parts = key.split("-");
        System.out.println(Arrays.toString(parts));
        if (parts.length == 3) {
            question = parts[1];
            id = Integer.parseInt(parts[2]);
            if (!question.equals("q1") && !question.equals("q2")) {
                return;
            }
        }

        if (id == null) {
            return;
        }

        Question q = question.equals("q1") ? Question.Q1 : Question.Q2;

        results.addAnswer(q, isYes, id);

    }

    public void contextDestroyed(javax.servlet.ServletContextEvent sce) {

    }

    public void attributeRemoved(javax.servlet.ServletContextAttributeEvent scae) {

    }

    public void attributeReplaced(javax.servlet.ServletContextAttributeEvent scae) {

    }

}
