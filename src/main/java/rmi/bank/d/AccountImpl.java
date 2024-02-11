package rmi.bank.d;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AccountImpl implements Account, Serializable {

    private double balance;
    public AccountImpl(double balance) throws RemoteException {
        this.balance = balance;
    }

    @Override
    public synchronized double readBalance() throws RemoteException {
        return balance;
    }

    @Override
    public synchronized void changeBalance(double amount) throws RemoteException {
        if (balance + amount < 0) {
            throw new OverdrawAccountException();
        }
        balance += amount;
    }
}
