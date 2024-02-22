package praktikum.fopt5.rmi;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class RmiDiffDemo {
    private static interface RmiObject extends Remote {

    }

    private static class RmiObjectImpl extends UnicastRemoteObject implements  RmiObject {

        public RmiObjectImpl() throws RemoteException {

        }
    }

    public static void main(String... args) {


        try {
            Registry registry;

            if (args.length == 0) {

                registry = LocateRegistry.createRegistry(1099);

                registry.rebind("rmiobj1", new RmiObjectImpl());
                registry.rebind("rmiobj2", new RmiObjectImpl());


            } else {

                String[] list = Naming.list("rmi://localhost:1099");

                System.out.println(Arrays.toString(list));

                Remote obj1Same = Naming.lookup(list[0]);
                Remote obj2Same = Naming.lookup(list[0]);

                System.out.println("obj1Same == obj2Same produces: " + (obj1Same == obj2Same) + " (expected: false)");
                System.out.println("obj1Same.equals(obj2Same) produces: " + (obj1Same.equals(obj2Same)) + " (expected: true)");

                Remote obj1Diff = Naming.lookup(list[0]);
                Remote obj2Diff = Naming.lookup(list[1]);

                System.out.println("obj1Diff == obj2Diff produces: " + (obj1Diff == obj2Diff) + " (expected: false)");
                System.out.println("obj1Diff.equals(obj2Diff) produces: " + (obj1Diff.equals(obj2Diff)) + " (expected: false)");

                System.exit(0);
            }
        } catch (Exception e) {
            System.err.println(e);
        }

    }

}
