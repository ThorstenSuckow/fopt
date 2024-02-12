package da.tasks.rmi.circular;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServiceImpl extends UnicastRemoteObject implements Service{

    List<Processor> processors;


    public ServiceImpl() throws RemoteException {

        processors = new ArrayList<>();

    }

    @Override
    public void add(Processor p) throws RemoteException {
        processors.add(p);
    }

    @Override
    public void remove(Processor p) throws RemoteException {
        processors.remove(p);
    }

    @Override
    public Processor next(Processor p) throws RemoteException {
        if (processors.isEmpty()) {
            return null;
        }

        if (!processors.contains(p) || processors.get(processors.size() - 1).equals(p)) {
            return processors.get(0);
        }

        return processors.get(processors.indexOf(p) + 1);
    }
}
