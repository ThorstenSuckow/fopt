package sandbox.rmicallbyvalue;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICallByValue extends Remote {

    void setFoo(Dummy foo) throws RemoteException;

}
