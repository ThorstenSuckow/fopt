package praktikum.fopt5.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;

public class PlainData implements Serializable {
    private int value;

    public PlainData(int value) {
        this.value = value;
    }

    public int getValue() throws RemoteException {
        return value;
    }

    public void setValue(int value) throws RemoteException {
        this.value = value;
    }

}
