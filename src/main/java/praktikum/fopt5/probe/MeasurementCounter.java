package praktikum.fopt5.probe;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MeasurementCounter extends Remote {
    void dataAvailable(int value) throws RemoteException;
}
