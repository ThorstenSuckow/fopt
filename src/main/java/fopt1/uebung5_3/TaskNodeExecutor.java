package fopt1.uebung5_3;


import java.util.ArrayList;
import java.util.List;

class TaskNodeExecutor<T> extends Thread {
    private Task<T> task;
    private T result;

    public TaskNodeExecutor(Task<T> task) {
        this.task = task;
    }

    public void run() {

        if(task.isDivisible()) {

            List<Task<T>> subtasks = task.split();
            List<TaskNodeExecutor<T>> threads = new ArrayList<TaskNodeExecutor<T>>();

            for(Task<T> subtask: subtasks) {
                TaskNodeExecutor<T> thread = new TaskNodeExecutor<T>(subtask);
                threads.add(thread);
                thread.start();
            }

            List<T> subresults = new ArrayList<T>();

            for(TaskNodeExecutor<T> thread: threads) {
                try  {
                    thread.join();
                } catch(InterruptedException e) {
                    // intentionally left empty
                }
                subresults.add(thread.getResult());
            }

            result = task.combine(subresults);

        } else { //!task.isDivisible()
            result = task.execute();
        }
    }

    public T getResult() {
        return result;
    }

    public static <T> T executeAll(Task<T> task) {
        TaskNodeExecutor<T> root = new TaskNodeExecutor<T>(task);
        root.run();
        return root.getResult();
    }
}
