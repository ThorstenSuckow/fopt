package klausurvorbereitung.foptws23;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloDemo {
    public static void main(String[] args) {
        Registry registry;
        if (args.length == 0) {
            try {
                registry = LocateRegistry.createRegistry(8888);
                registry.rebind("hello", new HelloImpl());
            } catch (RemoteException e) {
                System.err.println("[server] " + e);
            }
        } else {

            try {
                registry = LocateRegistry.getRegistry(8888);
                Hello hello = (Hello)registry.lookup("hello");
                System.err.println(hello.hello("World"));
            } catch (RemoteException | NotBoundException e) {
                System.err.println("[client] " + e);
            }
        }
    }
}
