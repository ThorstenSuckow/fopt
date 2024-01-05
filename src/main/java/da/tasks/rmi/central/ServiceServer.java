package da.tasks.rmi.central;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServiceServer {


    public static void main(String[] args) {

        final int port = 1099;

        try {

            Registry r = LocateRegistry.createRegistry(port);

            DataImpl data = new DataImpl();
            data.append("123");
            data.append("456");

            assert(data.asString().equals("123456"));

            ServiceImpl service = new ServiceImpl(data);

            r.rebind("service", service);


        } catch (Exception e) {
            System.err.println("[serviceserver] error: " +e);
        }

        System.out.println("[serviceserver] running on " + port + ", exit with [CTRL + C]");

    }

}
