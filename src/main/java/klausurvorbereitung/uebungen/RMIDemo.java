package klausurvorbereitung.uebungen;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * noEcho demonstrates blocking behavior of RMI calls.
 *
 */
public class RMIDemo {

    interface Echo extends Remote {
        String echo(String msg) throws RemoteException;
        void noEcho() throws RemoteException;
    }

    static class EchoImpl extends UnicastRemoteObject implements Echo {

        public EchoImpl() throws RemoteException{}

        @Override
        public String echo(String msg) throws RemoteException {
            return msg;
        }

        @Override
        public void noEcho() throws RemoteException {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            // Server
            try {
                Registry registry = LocateRegistry.createRegistry(1099);
                registry.rebind("echo", new EchoImpl());
            } catch (RemoteException e) {
                System.err.println("[registry] " + e);
            }
            //Thread is started, it's okay to leave main() at this point.
            return;
        }

        // client
        try {
            Remote stub = Naming.lookup("rmi://localhost:1099/echo");
            Echo echo = (Echo) stub;
            System.out.println(echo.echo(args[0]));

            System.out.println(" calling [noEcho]...");
            echo.noEcho();
            System.out.println("  done calling [noEcho]!");

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.err.println("[client] " + e);
        }
    }


}
