package klausurvorbereitung.foptws23;

import java.rmi.*;
import java.rmi.server.*;

public class HelloImpl extends UnicastRemoteObject implements Hello{

    public HelloImpl() throws RemoteException {
    }

    public String hello(String name) throws RemoteException {
        return "hello" + name;
    }

}
