package klausurvorbereitung.uebungen;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RmiMvpDemo {
    interface Presenter extends Remote {
        void setView(View v) throws RemoteException;
        void updateView(int value) throws RemoteException;
    }

    interface Model extends Remote {
        void increment() throws RemoteException;
        void addClient(Presenter client) throws RemoteException;
    }
    static class PresenterImpl extends UnicastRemoteObject implements Presenter {
        private View view;
        public PresenterImpl() throws RemoteException{}
        public void setView(View v){
            this.view = v;
        }
        public void updateView(int value){
            this.view.update(value);
        }
    }
    static class ModelImpl extends UnicastRemoteObject implements Model {
        private List<Presenter> clients = new ArrayList<>();
        private int value = 0;
        public ModelImpl() throws RemoteException {
            Thread t1 = new Thread(()-> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        increment();
                    } catch (RemoteException | InterruptedException ignored) {
                    }
                }
            });

            t1.start();
        }
        public synchronized void addClient(Presenter c) throws RemoteException {
            clients.add(c);
        }
        public synchronized void increment() throws RemoteException {
            value++;
            System.out.println(value);
            for (Presenter client: clients) {
                client.updateView(value);
            }
        }
    }
    static class View {
        private final String name;
        public View(Presenter p, String n) throws RemoteException {
            this.name = n;
            p.setView(this);
        }
        public void update(int value) {
            System.out.println("[client:" + name + "] update " + value);
        }
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {

        if (args.length == 0) { // server
            System.out.println("starting Server...");
            ModelImpl counterModel = new ModelImpl();
            Registry r = LocateRegistry.createRegistry(1099);
            r.rebind("counterModel", counterModel);
        } else { // client, 1st arg is its displayname
            System.out.println("starting Client...");
            Model counterModel = (Model) Naming.lookup("counterModel");
            PresenterImpl presenter = new PresenterImpl();
            new View(presenter, args[0]);
            counterModel.addClient(presenter);
        }
    }
}
