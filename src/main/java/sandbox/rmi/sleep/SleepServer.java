package sandbox.rmi.sleep;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SleepServer {


    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("sleep1", new SleepImpl());
            registry.rebind("sleep2", new SleepImpl());

        } catch (Exception e) {
            System.out.println("[server] error: " + e.getMessage());
        }



    }

}
