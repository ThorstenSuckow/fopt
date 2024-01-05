package sandbox.rmi.listener;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

public class SubjectServer {

    public static void main(String[] args) {

        try {

            Registry r = LocateRegistry.createRegistry(1099);

            SubjectImpl subject = new SubjectImpl();
            r.rebind("subject", subject);

            System.out.println("subject listening on 1099...");

            while (true) {

                String msg = "message broadcast at " + new Date();
                System.out.println("[preparing message] " + "message broadcast at " + new Date());
                subject.notifyAll(msg);
                Thread.sleep((long) (Math.random() * 10000));

            }


        } catch (Exception e) {
            System.out.println("[SubjectServer] " + e);
        }

        System.exit(0);
    }

}
