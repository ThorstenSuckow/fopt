package fopt6.uebung17_2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiSessionServer {

    public static void main(String[] args) {

        try {

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("sessionContext", new RmiSessionContextImpl());

        } catch (Exception e) {
            System.err.println("[RmiSessionDemo] " + e.getMessage());
        }

    }

}
