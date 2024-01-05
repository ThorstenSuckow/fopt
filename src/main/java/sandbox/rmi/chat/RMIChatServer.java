package sandbox.rmi.chat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIChatServer {


    public static void main(String[] args) {

        try {

            boolean useDelay = true;
            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("rmichatserver", new ChatServerImpl(useDelay));


            System.out.println("[rmichatserver] listening on 1099, using delay (" + useDelay + ")...");
        } catch (Exception e) {
            System.err.println("[rmichatserver] " +e);
        }


    }

}
