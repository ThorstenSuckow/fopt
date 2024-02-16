package klausurvorbereitung.foptws23;

import java.io.IOException;
import java.rmi.*;
public interface Hello extends Remote  {

    public String hello(String name) throws RemoteException;

    public String hello2(String name) ;
}