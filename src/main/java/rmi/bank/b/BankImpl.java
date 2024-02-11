package rmi.bank.b;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BankImpl extends UnicastRemoteObject implements Bank {

    private Account[] accounts;

    public BankImpl(Account[] accounts) throws RemoteException{
        this.accounts = accounts;
    }

    public Account getAccount(int accountNumber) throws RemoteException {
        if (accountNumber < 0 || accountNumber >= accounts.length) {
            throw new IllegalArgumentException();
        }

        return accounts[accountNumber];
    }

}
