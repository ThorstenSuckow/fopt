package da.tasks.rmi.circular;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ProcessorImpl extends UnicastRemoteObject implements Processor {

    private Service service;

    public ProcessorImpl(Service service) throws RemoteException {

        this.service = service;

    }

    @Override
    public void execute(Data d) throws RemoteException {

        try {
            Thread.sleep((long) (Math.random() * 10));
        } catch (InterruptedException ignored) {}

        d.append(Character.toString((int)(Math.random() * 100)));

        Processor next = service.next(this);

        if (next == null) {
            return;
        }

        next.execute(d);
    }
}
