package praktikum.fopt5.probe;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiMeasuringStation {

    public final static int PORT = 9999;

    public final static int PROBE_SLEEP = 5000;


    public static void main(String... args) throws IOException, NotBoundException {

        if (args.length == 0) {
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("measuringstation", new MeasuringStationImpl());

        } else if (args.length == 2) {

            MeasuringStation station = (MeasuringStation) Naming.lookup("measuringstation");

            station.subscribe(new MeasurementCounterImpl(), Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } else  if (args.length == 1) {

            Probe.init();

        }


    }



}
