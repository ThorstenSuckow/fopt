package klausurvorbereitung.foptws23;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;

public class HelloImpl extends UnicastRemoteObject implements Hello{

    public HelloImpl() throws RemoteException {


    }

    public String hello(String name)
    {
        return "hello" + name;
    }

    public String hello2(String name) {
        return this.hello(name);
    }
}
