package praktikum.fopt5.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;

public class SerializedData implements Data, Serializable {

    private int value;

    public SerializedData(int value) {
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
