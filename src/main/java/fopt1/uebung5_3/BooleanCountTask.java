package fopt1.uebung5_3;

import java.util.ArrayList;
import java.util.List;

public class BooleanCountTask implements Task<BooleanCountResult> {


    final int splitFactor;

    final int MIN_LENGTH = 100;

    private final boolean[] bools;

    private final int start;

    private final int end;


    public BooleanCountTask(boolean[] bools, int splitFactor) {
        this(bools, 0, bools.length - 1, splitFactor);
    }


    public BooleanCountTask(boolean[] bools, int start, int end, int splitFactor) {

        if (start >= end) {
            throw new RuntimeException("start must be less than end");
        }

        this.splitFactor = splitFactor;
        this.bools = bools;
        this.start = start;
        this.end = end;

    }

    @Override
    public boolean isDivisible() {
        System.out.println("isDivisible: " + (this.end - this.start) + ">" + MIN_LENGTH);
        return this.end - this.start + 1> MIN_LENGTH;
    }

    @Override
    public List<Task<BooleanCountResult>> split() {

        List<Task<BooleanCountResult>> tasks = new ArrayList<>();
        
        int taskCount = (end - start + 1) / splitFactor;

        int taskLength = splitFactor;

        int taskStart = this.start;
        int taskEnd   = this.start + taskCount -1;

        for (int i = 0; i < taskLength; i++) {

            System.out.println(
                    Thread.currentThread().getName() +
                            "; adding task. " + this.start + "; "+ this.end +
                            "; taskLength: " + taskLength + "; ["+taskStart + ", " + taskEnd + "]"
            );
            tasks.add(new BooleanCountTask(this.bools, taskStart, taskEnd, splitFactor));

            taskStart += taskCount;
            taskEnd += (taskCount );

        }


        return tasks;
/*
        int tempStart = start;
        int tempEnd;
        int howMany = (end-start+1) / splitFactor;
        int numberOfTasks = splitFactor;
        if(howMany < 1)
        {
            howMany = 1;
            numberOfTasks = end-start+1;
        }
        List<Task<BooleanCountResult>> tasks = new ArrayList<>();
        for(int i = 0; i < numberOfTasks; i++)
        {
            if(i < numberOfTasks-1)
            {
                tempEnd = tempStart + howMany - 1;
            }
            else
            {
                tempEnd = end;
            }
            System.out.println(
                    Thread.currentThread().getName() +
                            "; adding task. " + this.start + "; "+ this.end +
                            "; taskLength: " + numberOfTasks + "; ["+tempStart + ", " + tempEnd + "]"
            );
            tasks.add(new BooleanCountTask(bools, tempStart, tempEnd, splitFactor));
            tempStart = tempEnd + 1;
        }
        return tasks;
*/
    }

    @Override
    public BooleanCountResult execute() {

        int result = 0;

        for (int i = start; i < end; i++) {
            if (bools[i]) {
                result++;
            }
        }

        return new BooleanCountResult(result);
    }

    @Override
    public BooleanCountResult combine(List<BooleanCountResult> results) {

        int res = 0;
        int depth = 0;
        int index = 0;

        for (BooleanCountResult result: results) {
            System.out.println("COMBINE");
            res += result.getResult();
            index += result.getNodeIndex();
            if (depth < result.getDepth()) {
                depth = result.getDepth();
            }

        }

        return new BooleanCountResult(res, depth + 1, index + 1);
    }
}
