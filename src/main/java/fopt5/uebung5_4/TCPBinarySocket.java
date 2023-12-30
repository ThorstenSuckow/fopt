package fopt5.uebung5_4;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPBinarySocket implements AutoCloseable {

    private Socket socket;

    private DataOutputStream outputStream;

    private DataInputStream inputStream;



    public TCPBinarySocket(String serverAddress, int port) throws UnknownHostException, IOException {
        socket = new Socket(InetAddress.getByName(serverAddress), port);
        initStreams();
    }

    public void setTimeout(int timeout) throws IOException {
        socket.setSoTimeout(timeout);
    }

    public TCPBinarySocket(Socket socket) throws IOException{
        this.socket = socket;
        initStreams();
    }

    private void initStreams() throws IOException {
        outputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = new DataInputStream(socket.getInputStream());
    }

    public void send(int msg) throws IOException, IllegalArgumentException {
        if (msg > 1 || msg < 0) {
            throw new IllegalArgumentException();
        }
        outputStream.writeInt(msg);
        outputStream.flush();
    }

    public int receive() throws IOException {
        return inputStream.readInt();
    }

    public void reply(int msg) throws IOException, IllegalArgumentException {
        outputStream.writeInt(msg);
        outputStream.flush();
    }

    public void close() throws IOException  {
        socket.close();
    }
}
