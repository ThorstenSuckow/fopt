package fopt6.uebung17_2;

import java.rmi.Naming;

public class RmiSessionClient {

    public static void main(String[] args) {

        try {

            RmiSessionContext sessionContext = (RmiSessionContext) Naming.lookup("sessionContext");


            RmiSession session = sessionContext.getSession("myId");

            if (args.length >= 1 && args[0].equals("set")) {
                session.setAttribute(args[1], args[2]);
            }

            if (args.length >= 1 && args[0].equals("get")) {
                System.out.println(session.getAttribute(args[1]));
            }

        } catch (Exception e) {
            System.err.println("[RmiSessionClient] " + e.getMessage());
        }

    }


}
