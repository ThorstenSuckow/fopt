package praktikum.fopt5.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MultiplyDemo {

    private static interface Multiply extends Remote {
        int multiply(int x, int y) throws RemoteException;
    }

    private static class MultiplyImpl extends UnicastRemoteObject implements Multiply {

        public MultiplyImpl() throws RemoteException {

        }
        @Override
        public int multiply(int x, int y) throws RemoteException {
            return x * y;
        }
    }

    public static void main(String... args) {


        try {
            Registry registry;

            if (args.length == 0) {

                registry = LocateRegistry.createRegistry(1099);

                registry.rebind("mul", new MultiplyImpl());

            } else {

                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);

                registry = LocateRegistry.getRegistry();

                Multiply mul = (Multiply) registry.lookup("mul");

                System.out.println(mul.multiply(x, y));

                System.exit(0);
            }
        } catch (Exception e) {
            System.err.println(e);
        }

    }

}
