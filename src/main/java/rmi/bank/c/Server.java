package rmi.bank.c;

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

        BankImpl bank = new BankImpl();
        registry.rebind("Bank", bank);

    }

}
