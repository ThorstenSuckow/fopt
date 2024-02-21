package praktikum.fopt5.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DemoImpl extends UnicastRemoteObject implements Demo {

    private int ops;

    public DemoImpl() throws RemoteException {

    }

    public int increment(Data data) throws RemoteException {
        ops++;
        data.setValue(data.getValue() + 1);
        return data.getValue();
    }

    public int increment(PlainData data) throws RemoteException {
        ops++;
        data.setValue(data.getValue() + 1);
        return data.getValue();
    }

    public int getOperations() {
        return ops;
    }
}
