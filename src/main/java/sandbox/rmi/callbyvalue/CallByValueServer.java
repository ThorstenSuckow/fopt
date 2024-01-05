package sandbox.rmi.callbyvalue;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CallByValueServer {


    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("callbyvalue", new CallByValueImpl());

        } catch (Exception e) {
            System.out.println("[server] error: " + e.getMessage());
        }



    }

}
