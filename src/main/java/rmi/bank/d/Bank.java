package rmi.bank.d;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bank extends Remote {

    public Account getAccount(int accountNumber) throws RemoteException;

}
