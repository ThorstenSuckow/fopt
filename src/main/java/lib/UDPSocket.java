package lib;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


/**
 * Listing 5.1 [Oec22, 269]
 */
public class UDPSocket implements AutoCloseable {


    protected DatagramSocket socket;
    private InetAddress address;
    private int port;

    public UDPSocket() throws SocketException {
        this(new DatagramSocket());
    }

    public UDPSocket(int port) throws SocketException {
        this(new DatagramSocket(port));
    }

    protected UDPSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    public void send(String s, InetAddress receiverAddress, int receiverPort) throws IOException {
        byte[] outBuffer = s.getBytes();
        DatagramPacket outPacket = new DatagramPacket(outBuffer, outBuffer.length, receiverAddress, receiverPort);
        socket.send(outPacket);
    }

    public String receive(int maxBytes) throws IOException {
        byte[] inBuffer = new byte[maxBytes];
        DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);
        socket.receive(inPacket);
        address = inPacket.getAddress();
        port = inPacket.getPort();

        return new String(inBuffer, 0, inPacket.getLength());
    }

    public void reply(String s) throws IOException {
        if (address == null) {
            throw new IOException("no one to reply");
        }

        send(s, address, port);
    }

    public InetAddress getSenderAddress() {
        return address;
    }

    public int getSenderPort() {
        return port;
    }

    public void setTimeout(int timeout) throws SocketException {
        socket.setSoTimeout(timeout);
    }

    public void close() {
        socket.close();
    }


}
