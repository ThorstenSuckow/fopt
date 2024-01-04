package fopt5.uebung11_3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ListServer {


    public static void main(String[] args) {

        try {

            Registry r = LocateRegistry.createRegistry(1099);

            r.rebind("appender", new ListAppenderImpl());


        } catch (Exception e) {

            System.err.println("[ListServer] " + e);
        }



    }

}
