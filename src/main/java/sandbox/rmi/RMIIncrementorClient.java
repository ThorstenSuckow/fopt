package sandbox.rmi;


import java.net.InetAddress;
import java.rmi.Naming;

public class RMIIncrementorClient {

    public static void main(String[] args) {

        /**
         * throws UnknownHostException, RemoteException, MalformedURLException, NotBoundException
         */
        try {
            String target = InetAddress.getLocalHost().getHostAddress();

            RMIIncrementor incrementor = (RMIIncrementor) Naming.lookup("rmi://" + target + "/incrementor");

            incrementor.reset();
            System.out.println(incrementor.increment());
        } catch (Exception e) {
            System.out.println("[client exception] " + e);
        }


    }
}
