package sandbox.rmi.sleep;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class SleepClient {


    public static void main (String[] args) {

        if (args.length < 3) {
            System.out.println("Usage: java SleepClient [useThreadForAsync:boolean] [sleepType:\"async\"|\"sync\")] [obj_id:\"sleep1\"|\"sleep2\"] [sleep_duration:int..]");
            System.exit(-1);
        }

        try {

            boolean useThreads = Boolean.parseBoolean(args[0]);
            String sleepType = args[1];
            String objId = args[2];
            // omitting the url seems to work just fine
            ISleep sleep = (ISleep) Naming.lookup(objId);

            if (sleepType.equals("sync")) {
                for (int i = 3; i < args.length; i++) {
                    System.out.println("putting " + objId + " to sleep...");
                    sleep.sleep(Integer.parseInt(args[i]));
                    System.out.println(objId + " done sleeping.");
                }
            }

            if (sleepType.equals("async")) {
                for (int i = 3; i < args.length; i++) {

                    int finalI = i;

                    if (useThreads) {
                        Thread t = new Thread(() -> {
                            System.out.println("putting " + objId + " to async sleep...");
                            try {
                                sleep.sleepAsync(Integer.parseInt(args[finalI]));
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println(objId + " done sleeping.");
                        });
                        t.start();
                    } else {
                        System.out.println("putting " + objId + " to async sleep...");
                        sleep.sleepAsync(Integer.parseInt(args[finalI]));
                        System.out.println(objId + " done sleeping.");
                    }


                }
            }

        } catch (Exception e) {
            System.out.println("[client] error: " + e);
        }

    }

}
