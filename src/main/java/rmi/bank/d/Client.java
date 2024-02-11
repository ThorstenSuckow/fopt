package rmi.bank.d;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private int port;

    public Client(int port, String[] args)  {

        if (port < 1 || port >= 65_536 || args.length < 2) {
            throw new IllegalArgumentException();
        }
        this.port = port;


        String operation = args[0];
        int accountNumber;
        double amount = 0;

        if (operation.equals("s") && args.length < 3) {
            throw new IllegalArgumentException();
        }

        try {
            if (args.length > 2) {
                amount = Double.parseDouble(args[2]);
            }
            accountNumber = Integer.parseInt(args[1]);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }

        if (operation.equals("s")) {
            this.updateAccount(accountNumber, amount);
        } else {
            this.readAccount(accountNumber);
        }

    }



    private void updateAccount(int accountNumber, double amount) {
        try {
            Account account = getAccount(accountNumber);
            account.changeBalance(amount);
        } catch (NotBoundException|RemoteException e) {
            System.err.println("[client] error while updating account: " + e);
        }

    }

    private void readAccount(int accountNumber) {

        try {
            Account account = getAccount(accountNumber);
            System.out.println("Kontostand des Kontos " + accountNumber + ": " + account.readBalance());
        } catch (NotBoundException|RemoteException e) {
            System.err.println("[client] error while reading account: " + e);
        }
    }

    private Account getAccount(int accountNumber) throws RemoteException, NotBoundException {

        return getBank().getAccount(accountNumber);

    }

    private Bank getBank() throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry(port);

        return (Bank) registry.lookup("Bank");

    }

}
