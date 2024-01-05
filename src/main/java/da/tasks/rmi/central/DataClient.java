package da.tasks.rmi.central;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

public class DataClient {

    public static void myAssert(String s, boolean truthy) {

        System.out.println(s);
        assert(truthy);
    }

    public static void main(String[] args) {

        try {

            Service service = (Service) Naming.lookup("service");

            Data d = service.get();

            System.out.println(d.asString());



            myAssert(d.asString(), d.asString().equals("123456"));

            d.append("789");
            myAssert(d.asString(), d.asString().equals("123456789"));

            myAssert(service.open().asString(), service.open().asString().equals("123456789"));

            d = service.close();
            myAssert(d.asString(), d.asString().equals("123456789"));
            d.append("0");
            myAssert(d.asString(), d.asString().equals("1234567890"));
            myAssert(service.close().asString(), service.close().asString().equals("123456789"));




        } catch (Exception e) {
            System.err.println("[dataclient] " + e);
        }

    }


}
