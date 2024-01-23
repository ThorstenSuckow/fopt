package da.tasks.servlet.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Results {


    private final Map<Question, List<Integer>> yesAnswers;

    private final Map<Question, List<Integer>> noAnswers;

    public Results() {
        yesAnswers = new HashMap<>();
        noAnswers = new HashMap<>();
        clear();
    }

    public synchronized void addAnswer(Question q, Boolean answer, Integer id) {

        System.out.println("add: " + q + " " + answer + " " + id );
        if (answer) {
            noAnswers.get(q).remove(id);
            yesAnswers.get(q).add(id);
        } else {
            yesAnswers.get(q).remove(id);
            noAnswers.get(q).add(id);
        }

    }

    public synchronized List<Integer> getYesAnswers(Question q) {
        return yesAnswers.get(q);
    }

    public synchronized List<Integer> getNoAnswers(Question q) {
        return noAnswers.get(q);
    }

    public synchronized void clear() {
        yesAnswers.put(Question.Q1, new ArrayList<>());
        yesAnswers.put(Question.Q2, new ArrayList<>());

        noAnswers.put(Question.Q1, new ArrayList<>());
        noAnswers.put(Question.Q2, new ArrayList<>());

    }
}
