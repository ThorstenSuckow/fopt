package praktikum.fopt5.probe;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeasuringStationImpl extends UnicastRemoteObject implements MeasuringStation {

    private record Client(MeasurementCounter measurementCounter, int from, int to) {}
    private List<MeasuringStationImpl.Client> list;
    public MeasuringStationImpl() throws IOException {
        list = new ArrayList<>();
        startReceiving();
    }

    private void receive() {

        System.out.println("[MeasuringStation] communicating with probe on port " + RmiMeasuringStation.PORT);

        try (DatagramSocket socket = new DatagramSocket(RmiMeasuringStation.PORT)) {

            System.out.println("[MeasuringStation] socket created. Waiting for signals...");
            System.out.println("                   The truth is out there.");

            DatagramPacket packet = new DatagramPacket(new byte[4], 4);

            while (true) {
                socket.receive(packet);
                DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.getData()));
                int value = dis.readInt();

                System.out.println("[MeasuringStation] received " + value);
                Iterator<MeasuringStationImpl.Client> clientIt = list.iterator();
                while (clientIt.hasNext()) {
                    MeasuringStationImpl.Client client = clientIt.next();
                    if (client.from() <= value && value <= client.to()) {
                        client.measurementCounter().dataAvailable(value);
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void startReceiving() {
        new Thread(this::receive).start();
    }

    public synchronized void subscribe(MeasurementCounter mc, int from, int to) throws RemoteException {
        list.add(new MeasuringStationImpl.Client(mc, from, to));
    }
}
