package praktikum.fopt5.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MultiInterfaceDemo {

    private static interface Foo extends Remote {
        String foo() throws RemoteException;
    }

    private static interface Bar extends Remote {
        String bar() throws RemoteException;
    }

    private static class FooAndBar extends UnicastRemoteObject implements Foo, Bar {

        public FooAndBar() throws RemoteException {

        }

        @Override
        public String foo() throws RemoteException {
            return "foo";
        }

        @Override
        public String bar() throws RemoteException {
            return "bar";
        }
    }

    public static void main (String... args) {

        try {


            Registry registry;


            if (args.length == 0) {
                registry = LocateRegistry.createRegistry(1099);
                registry.rebind("FooAndBar", new FooAndBar());

            } else {

                registry = LocateRegistry.getRegistry();

                Remote remote = registry.lookup("FooAndBar");

                Foo foo = (Foo)remote;
                Bar bar = (Bar)remote;

                System.out.println(foo.foo());
                System.out.println(bar.bar());

            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }


}
