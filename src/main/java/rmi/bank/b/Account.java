package rmi.bank.b;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface Account extends Remote {


    public double readBalance() throws RemoteException;

    public void changeBalance(double amount) throws RemoteException;


}
