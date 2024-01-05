package sandbox.rmi.listener;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class SubjectImpl extends UnicastRemoteObject implements Subject {

    private ArrayList<Observer> obs;

    protected SubjectImpl() throws RemoteException {
        obs = new ArrayList<>();
    }

    @Override
    public synchronized void addObserver(Observer ob) throws RemoteException {
        if (!obs.contains(ob)) {
            obs.add(ob);
        }
    }

    public synchronized void notifyAll(String msg) throws RemoteException {

            for (Iterator<Observer> iter = obs.iterator(); iter.hasNext(); ) {
                Observer ob = iter.next();

                // remove faulty clients
                try {
                    ob.notify(msg);
                } catch (RemoteException e) {
                    iter.remove();
                }
            }

    }

}
