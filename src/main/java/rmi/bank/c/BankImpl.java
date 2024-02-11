package rmi.bank.c;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BankImpl extends UnicastRemoteObject implements Bank {

    private Account[] accounts;

    public BankImpl() throws RemoteException{
        accounts = new Account[100];

        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new AccountImpl(0);
        }
    }

    public Account getAccount(int accountNumber) throws RemoteException {
        if (accountNumber < 0 || accountNumber >= accounts.length) {
            throw new IllegalArgumentException();
        }

        return accounts[accountNumber];
    }

}
