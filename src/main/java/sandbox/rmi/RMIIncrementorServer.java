package sandbox.rmi;

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
            registry.rebind("incrementor", incrementor);
            // use Naming if Registry is started as external process
            //Naming.rebind("incrementor", incrementor);
        } catch (Exception e) {
            System.out.println("Excpetion while creating incrementor: " + e);
        }
    }

}
