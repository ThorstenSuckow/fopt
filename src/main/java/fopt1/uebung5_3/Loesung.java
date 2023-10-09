package fopt1.uebung5_3;

public class Loesung {

    public static void main(String[] args) {


        int length = 10000;
        int splitFactor = 5;


        boolean[] bools = new boolean[length];

        for (int i = 0; i < bools.length; i++) {

            if ( i % 10 == 0) {
                bools[i] = true;
            }
        }

        BooleanCountResult result = TaskNodeExecutor.executeAll(new BooleanCountTask(bools, splitFactor));

        System.out.println("Result: " + result);

        if (result.getResult() != bools.length / 10) {
            System.out.println("Error. result is " + result);
        }


    }

}
