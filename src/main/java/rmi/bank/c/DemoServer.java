package rmi.bank.c;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class DemoServer {


    public static void main(String[] args) throws RemoteException {


        int port = 1099;

        LocateRegistry.createRegistry(1099);

        Server server = new Server(port);

        Client c = new Client(port, cmd(5, 10));
        c = new Client(port, cmd(10));
        c = new Client(port, cmd(5, -20));
        c = new Client(port, cmd(5));
    }


    private static String[] cmd(int account) {
        return new String[]{"l", "" + account};
    }

    private static String[] cmd(int account, double amount) {
        return new String[]{"s", "" + account, "" + amount};
    }

}
