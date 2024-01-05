package sandbox.rmi.callbyvalue;


import java.rmi.Naming;

public class CallByValueClient {


    public static void main (String[] args)  {


        try {
            ICallByValue callbyvalue = (ICallByValue) Naming.lookup("callbyvalue");

            // will throw an exception since Dummy is not serializable
            callbyvalue.setFoo(new Dummy(1));

        } catch (Exception e) {
            System.out.println("[client] error: " + e);
        }

    }

}
