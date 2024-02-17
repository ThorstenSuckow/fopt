package klausurvorbereitung.foptws23;

import java.rmi.*;
public interface Hello extends Remote  {
    String hello(String name) throws RemoteException;
}