package da.tasks.rmi.central;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServiceImpl extends UnicastRemoteObject implements Service {

    private Data data;

    private boolean isOpen;

    protected ServiceImpl() throws RemoteException {
        isOpen = true;
    }
    protected ServiceImpl(Data d) throws RemoteException {
        this();
        data = d;
    }


    @Override
    public synchronized Data open() throws RemoteException {
        isOpen = true;
        return getData();
    }

    @Override
    public synchronized Data get() throws RemoteException {
        return null;
    }

    @Override
    public synchronized Data close() throws RemoteException {
        isOpen = false;
        return getData();
    }

    @Override
    public synchronized boolean isOpen() throws RemoteException {
        return isOpen;
    }

    private Data getData() throws RemoteException {

        if (isOpen()) {
            return data;
        }

        ArrayList<String> values = data.getValues();

        DataImpl d = new DataImpl();

        for (String value: values) {
            d.append(value);
        }

        return d;
    }

}
