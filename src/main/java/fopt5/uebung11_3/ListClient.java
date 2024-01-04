package fopt5.uebung11_3;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ListClient {


    public static void main (String[] args) throws MalformedURLException, NotBoundException, RemoteException {

        ListAppender listAppender = (ListAppender) Naming.lookup("appender");

        ListImpl list = new ListImpl();


        for (int i = 0; i < args.length; i++) {
            listAppender.append(list, Integer.parseInt(args[i]));

        }

        System.out.println(list.asString());

        System.exit(0);

    }

}
