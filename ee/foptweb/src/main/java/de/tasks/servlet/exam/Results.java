package de.tasks.servlet.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Results {


    Map<Question, List<Integer>> yesAnswers;

    Map<Question, List<Integer>> noAnswers;

    public Results() {
        yesAnswers = new HashMap<>();
        noAnswers = new HashMap<>();
        clear();
    }

    public void addAnswer(Question q, Boolean answer, int id) {

        if (answer) {
            noAnswers.get(q).remove(Integer.valueOf(id));
            yesAnswers.get(q).add(Integer.valueOf(id));
        } else {
            yesAnswers.get(q).remove(Integer.valueOf(id));
            noAnswers.get(q).add(Integer.valueOf(id));
        }

    }

    public List<Integer> getYesAnswers(Question q) {
        return yesAnswers.get(q);
    }

    public List<Integer> getNoAnswers(Question q) {
        return noAnswers.get(q);
    }

    public void clear() {
        yesAnswers.put(Question.Q1, new ArrayList<>());
        yesAnswers.put(Question.Q2, new ArrayList<>());

        noAnswers.put(Question.Q1, new ArrayList<>());
        noAnswers.put(Question.Q2, new ArrayList<>());

    }
}
