package sandbox.rmi;

import java.rmi.Naming;

public class RMIIncrementorServer {

    public static void main(String[] args) {
        try {
            DefaultRMIIncrementor incrementor = new DefaultRMIIncrementor();
            /**
             * @note: rebind() "replaces the binding for the specified name in this registry with the supplied
             * remote reference. If there is an existing binding for the specified name, it is discarded."
             */
            Naming.rebind("incrementor", incrementor);
        } catch (Exception e) {
            System.out.println("Excpetion while creating incrementor: " + e);
        }
    }

}
