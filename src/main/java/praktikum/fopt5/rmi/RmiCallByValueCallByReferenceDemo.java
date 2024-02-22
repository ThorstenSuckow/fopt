package praktikum.fopt5.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiCallByValueCallByReferenceDemo {




    public void createServer() {

        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("demo", new DemoImpl());

        } catch (Exception e) {
            System.err.println("[server] " + e);
        }
    }

    public void createClient() {

        try {
            Registry registry = LocateRegistry.getRegistry();

            Demo demo = (Demo) registry.lookup("demo");


            RmiData rmiData = new RmiData(0);
            SerializedData serializedData = new SerializedData(0);
            PlainData plainData = new PlainData(0);

            System.out.println("[ops] " + demo.getOperations());

            System.out.println("[exported]++ " + demo.increment(rmiData));
            System.out.println("[exported]++ " + demo.increment(rmiData));

            System.out.println("[serialized]++ " + demo.increment(plainData));
            System.out.println("[serialized]++ " + demo.increment(plainData));

            System.out.println("[plain]++ " + demo.increment(serializedData));
            System.out.println("[plain]++ " + demo.increment(serializedData));

            System.out.println("[ops]" + demo.getOperations());


        } catch (Exception e) {
            System.err.println("[client] " + e);
        }
    }


    public static void main (String... args) {

        if (args.length == 0) {
            RmiCallByValueCallByReferenceDemo rmi = new RmiCallByValueCallByReferenceDemo();
            rmi.createServer();
        } else {

            RmiCallByValueCallByReferenceDemo rmi = new RmiCallByValueCallByReferenceDemo();
            rmi.createClient();

            System.exit(0);
        }
    }
}
