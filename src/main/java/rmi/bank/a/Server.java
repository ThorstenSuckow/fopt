package rmi.bank.a;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    private final int port;

    public Server(int port) throws RemoteException {

        if (port < 1 || port >= 65_536) {
            throw new IllegalArgumentException();
        }

        this.port = port;

        registerAccounts();
    }


    private void registerAccounts() throws RemoteException {

        Registry registry = LocateRegistry.getRegistry(port);

        Account[] accounts = new Account[100];

        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new AccountImpl(0);
            registry.rebind("Konto" + i, accounts[i]);
        }
    }

}
