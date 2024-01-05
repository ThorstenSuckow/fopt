package da.tasks.rmi.central;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Data extends Remote, Serializable {

    void append(String s) throws RemoteException;

    ArrayList<String> getValues() throws RemoteException;

    String asString() throws RemoteException;


}
