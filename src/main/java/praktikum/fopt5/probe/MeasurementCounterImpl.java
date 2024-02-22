package praktikum.fopt5.probe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MeasurementCounterImpl  extends UnicastRemoteObject implements  MeasurementCounter {

    public MeasurementCounterImpl() throws RemoteException {
    }

    public void dataAvailable(int value) throws RemoteException {
        System.out.println("[client] probe measured " + value);
    }
}