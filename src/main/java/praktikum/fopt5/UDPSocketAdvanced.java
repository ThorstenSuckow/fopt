package praktikum.fopt5;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPSocketAdvanced {

    private int ops;

    private DatagramSocket datagramSocket;
    public UDPSocketAdvanced(int port) throws SocketException {
        this(new DatagramSocket(port));
    }

    protected UDPSocketAdvanced(DatagramSocket datagramSocket) {

        this.datagramSocket = datagramSocket;
    }

    public int receiveInt() throws IOException{

        int i;

        DatagramPacket datagramPacket = new DatagramPacket(new byte[4], 4);
        datagramSocket.receive(datagramPacket);

        byte[] b = datagramPacket.getData();

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(b));
        return dis.readInt();
    }


    public void sendObject(Object obj, InetAddress address, int port) throws IOException {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        ObjectOutputStream obs = new ObjectOutputStream(boas);
        obs.writeObject(obj);
        obs.flush();
        obs.close();

        byte[] bytes = boas.toByteArray();

        DatagramPacket datagramPacket = new DatagramPacket(
            bytes, bytes.length, address, port
        );

        datagramSocket.send(datagramPacket);
    }

    public void sendInt(int value, InetAddress address, int port) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream ds = new DataOutputStream(baos);
        ds.writeInt(value);
        ds.flush();
        ds.close();

        DatagramPacket datagramPacket = new DatagramPacket(baos.toByteArray(), 4, address, port);
        datagramSocket.send(datagramPacket);
    }

    public Object readObject(int maxBytes) throws IOException, ClassNotFoundException {


        DatagramPacket packet = new DatagramPacket(new byte[maxBytes], maxBytes);
        datagramSocket.receive(packet);

        byte[] bytes = packet.getData();

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);

        sendInt(ops++, packet.getAddress(), packet.getPort());

        return ois.readObject();
    }

}
