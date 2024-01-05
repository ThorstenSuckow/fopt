package sandbox.rmi.semaphor;

import java.rmi.Naming;

public class SemaphorClient {


    public static void main(String[] args) {


            try {

                Semaphor semaphor = (Semaphor) Naming.lookup("semaphor");

                while (true) {

                    semaphor.p();
                    System.out.println(Thread.currentThread().getName() + " doing some heavy lifting...");
                    Thread.sleep((long) (Math.random() * 500));
                    semaphor.v();
                }

            } catch (Exception e) {

                System.out.println("[semaphorclient] " + e);

            }



    }

}
