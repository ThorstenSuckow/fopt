package praktikum.fopt5.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiData extends UnicastRemoteObject implements Data {

    private int value;

    public RmiData(int value) throws RemoteException {
        this.value = value;
    }

    @Override
    public int getValue() throws RemoteException {
        return value;
    }

    @Override
    public void setValue(int value) throws RemoteException {
        this.value = value;
    }
}
