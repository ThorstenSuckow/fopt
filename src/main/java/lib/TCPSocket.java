package lib;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * Listing 5.7 [Oec22, 286]
 */
public class TCPSocket implements AutoCloseable {

    private Socket socket;

    private BufferedReader istream;

    private BufferedWriter ostream;

    /**
     * Client constructor.
     *
     * @param serverAddress
     * @param serverPort
     * @throws UnknownHostException
     * @throws IOException
     */
    public TCPSocket(String serverAddress, int serverPort) throws UnknownHostException, IOException {
        socket = new Socket(serverAddress, serverPort);
        initializeStreams();
    }


    /**
     * Server constructor. Creates a new TCPSocket-object with the specified socket that listens to incoming
     * connections.
     *
     * @param socket
     */
    public TCPSocket(Socket socket) throws IOException {
        this.socket = socket;
        initializeStreams();
    }

    public void sendLine(String s) throws IOException {
        ostream.write(s);
        ostream.newLine();
        ostream.flush();
    }


    public String receiveLine() throws IOException {
        return istream.readLine();
    }

    public void setTimeout(int timeout) throws SocketException {
        socket.setSoTimeout(timeout);
    }


    private void initializeStreams() throws IOException {
        ostream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        istream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void close() throws IOException {

        socket.close();

    }

}
