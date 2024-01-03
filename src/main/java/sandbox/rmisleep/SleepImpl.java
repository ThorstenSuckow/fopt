package sandbox.rmisleep;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SleepImpl extends UnicastRemoteObject implements ISleep {


    public SleepImpl() throws RemoteException {

    }

    public synchronized void sleep(int seconds) {
        System.out.println("[sleeimpl] starting to sleep (invoked by " + Thread.currentThread().getName() + ")");
        try {
            Thread.sleep(seconds * 1000L);
        } catch (Exception e) {
            System.out.println("[sleepimpl] error: " + e.getMessage());
        }
        System.out.println("[sleeimpl] waking up (invoked by " + Thread.currentThread().getName() + ")");
    }

    public void sleepAsync(int seconds) {
        System.out.println("[sleeimpl(async)] starting to sleep (invoked by " + Thread.currentThread().getName() + ")");
        try {
            Thread.sleep(seconds * 1000L);
        } catch (Exception e) {
            System.out.println("[sleepimpl(async)] error: " + e.getMessage());
        }
        System.out.println("[sleeimpl(async)] waking up (invoked by " + Thread.currentThread().getName() + ")");
    }
}
