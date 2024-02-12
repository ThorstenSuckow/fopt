package da.tasks.rmi.circular;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServiceImpl extends UnicastRemoteObject implements Service{

    private List<Processor> processors;

    private final Data data;

    public ServiceImpl() throws RemoteException {

        processors = new ArrayList<>();
        data = new DataImpl();

    }

    @Override
    public synchronized void add(Processor p) throws RemoteException {
        processors.add(p);
        if (processors.size() == 1) {
            new Thread(()-> {
                try {
                    p.execute(data);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

    }

    @Override
    public synchronized void remove(Processor p) throws RemoteException {
        processors.remove(p);
    }

    @Override
    public synchronized Processor next(Processor p) throws RemoteException {
        if (processors.isEmpty()) {
            return null;
        }

        if (!processors.contains(p) || processors.get(processors.size() - 1).equals(p)) {
            return processors.get(0);
        }

        return processors.get(processors.indexOf(p) + 1);
    }
}
