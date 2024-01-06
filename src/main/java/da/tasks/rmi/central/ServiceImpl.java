package da.tasks.rmi.central;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServiceImpl extends UnicastRemoteObject implements Service {

    private DataImpl dataImpl;

    private Data exported;
    private boolean isOpen;

    public ServiceImpl() throws RemoteException {
        dataImpl = new DataImpl();
        open();
    }


    @Override
    public synchronized Data open() throws RemoteException {
        isOpen = true;
        return get();
    }


    @Override
    public synchronized Data close() throws RemoteException {
        isOpen = false;
        return get();
    }

    @Override
    public synchronized boolean isOpen() throws RemoteException {
        return isOpen;
    }

    @Override
    public synchronized Data get() throws RemoteException {

        if (isOpen()) {
            if (exported == null) {
                exported = (Data) UnicastRemoteObject.exportObject(dataImpl, 0);
            }
            return exported;
        } else {
            if (exported != null) {
                UnicastRemoteObject.unexportObject(dataImpl, true);
                exported = null;
            }
        }

        return dataImpl;
    }

}
