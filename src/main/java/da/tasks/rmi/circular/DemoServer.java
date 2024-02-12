package da.tasks.rmi.circular;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoServer {


    public static void main(String[] args) throws IOException, NotBoundException {


        Registry r;

        if (args.length == 0) {
            r = LocateRegistry.createRegistry(1099);
            System.out.println("[circular] registering service.");
            r.rebind("Service", new ServiceImpl());

        } else {

            r = LocateRegistry.getRegistry(1099);

            final int clientLength = 1000;
            System.out.println("[circular] registering " + clientLength + " clients...");
            DataImpl d = new DataImpl();
            Service s = (Service) r.lookup("Service");
            List<Processor> ps = new ArrayList<>();

            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    for (int i = 0; i < clientLength; i++) {
                        s.remove(ps.get(i));
                    }
                    System.out.println(Arrays.toString(d.getValues().toArray()));
                }  catch (InterruptedException|RemoteException ignored) {}
            });
            t.start();


            for (int i = 0; i < clientLength; i++) {
                Processor p = new ProcessorImpl(s);
                ps.add(p);
                s.add(p);
            }


        }


    }

}
