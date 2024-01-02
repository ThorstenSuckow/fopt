package sandbox.rmi;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIIncrementorServer {

    public static void main(String[] args) {
        try {

            Registry registry = LocateRegistry.createRegistry(1099);

            DefaultRMIIncrementor incrementor = new DefaultRMIIncrementor();
            /**
             * @note: rebind() "replaces the binding for the specified name in this registry with the supplied
             * remote reference. If there is an existing binding for the specified name, it is discarded."
             */
            // use registry object
            registry.rebind("incrementor", incrementor);
            // ... or Naming:
            // Naming.rebind("incrementor", incrementor);
            // use Naming with url if Registry is running on port different to 1099
            // Naming.rebind("rmi://"+ InetAddress.getLocalHost().getHostAddress() +":1199/incrementor", incrementor);
        } catch (Exception e) {
            System.out.println("Excpetion while creating incrementor: " + e);
        }
    }

}
