package da.tasks.rmi.circular;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Data extends Remote {

    void append(String s) throws RemoteException;

    List<String> getValues() throws RemoteException;

}
