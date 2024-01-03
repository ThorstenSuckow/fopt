package sandbox.rmicallbyvalue;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallByValueImpl extends UnicastRemoteObject implements ICallByValue {

    private Dummy foo;

    public CallByValueImpl() throws RemoteException {

    }

    public void setFoo(Dummy f) throws RemoteException {
        foo = f;
    }

}
