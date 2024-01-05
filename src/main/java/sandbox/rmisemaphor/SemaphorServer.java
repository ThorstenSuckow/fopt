package sandbox.rmisemaphor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SemaphorServer {


    public static void main(String[] args) {


        try {

            Registry registry = LocateRegistry.createRegistry(1099);

            SemaphorImpl semaphor = new SemaphorImpl();
            registry.rebind("semaphor", semaphor);

        } catch (Exception e) {
            System.out.println("[semaphorserver] " + e);
        }

    }

}
