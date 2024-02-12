package da.tasks.rmi.circular;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Processor extends Remote {

    void execute(Data d) throws RemoteException;

}
