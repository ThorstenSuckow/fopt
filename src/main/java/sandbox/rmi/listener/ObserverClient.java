package sandbox.rmi.listener;

import java.rmi.Naming;

public class ObserverClient {

    public static void main(String[] args) {

        try {

            Subject s = (Subject) Naming.lookup("subject");

            s.addObserver(new ObserverImpl());


        } catch (Exception e) {
            System.out.println("[observerdemo] " + e);
        }

        System.out.println("client running, exit with Ctrl + C");
    }

}
