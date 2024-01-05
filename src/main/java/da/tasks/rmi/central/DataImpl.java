package da.tasks.rmi.central;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class DataImpl implements Data {

    private final ArrayList<String> values;

    public DataImpl() throws RemoteException {
        values = new ArrayList<>();
    }

    @Override
    public synchronized void append(String s) throws RemoteException {
        values.add(s);
    }

    @Override
    public synchronized ArrayList<String> getValues() throws RemoteException {
        return values;
    }


    public String asString() {
        StringBuilder b = new StringBuilder();
        for (String value: values) {
            b.append(value);
        }

        return b.toString();
    }
}
