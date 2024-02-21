package praktikum.fopt5.probe;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MeasuringStation  extends Remote {
    void subscribe(MeasurementCounter mc, int from, int to) throws RemoteException;
}