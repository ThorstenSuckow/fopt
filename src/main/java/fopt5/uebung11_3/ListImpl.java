package fopt5.uebung11_3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ListImpl extends UnicastRemoteObject implements List {

    protected ListImpl() throws RemoteException {
    }

    static class Node {
        private Node next;

        private final int value;

        public Node(int n) {
            value = n;
        }

    }

    Node first;

    Node last;



    public void add(int n) throws RemoteException {

        if (first == null) {
            first = new Node(n);
            last = first;
            return;
        }

        last.next = new Node(n);
        last = last.next;

    }

    public String asString()  {

        Node n = first;
        StringBuilder b = new StringBuilder();
        while (n != null) {
            b.append("[").append(n.value).append("]");
            n = n.next;
        }

        return b.toString();
    }





}
