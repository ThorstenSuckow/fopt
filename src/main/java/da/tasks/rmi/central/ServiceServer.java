package da.tasks.rmi.central;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServiceServer {


    public static void main(String[] args) {

        final int port = 1099;

        try {

            Registry r = LocateRegistry.createRegistry(port);

            ServiceImpl service = new ServiceImpl();

            service.get().append("123");
            service.get().append("456");

            r.rebind("service", service);


        } catch (Exception e) {
            System.err.println("[serviceserver] error: " +e);
        }

        System.out.println("[serviceserver] running on " + port + ", exit with [CTRL + C]");

    }

}
