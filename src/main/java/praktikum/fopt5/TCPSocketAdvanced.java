package praktikum.fopt5;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

public class TCPSocketAdvanced implements AutoCloseable {

    private Socket socket;

    private DataInputStream dis;

    private DataOutputStream dos;

    private ObjectOutputStream oos;

    private ObjectInputStream ois;

    private BufferedWriter bufferedWriter;

    private BufferedReader bufferedReader;


    /**
     * Client.
     * @param port
     * @throws IOException
     */
    public TCPSocketAdvanced(int port) throws IOException {
        this(new Socket(InetAddress.getLocalHost().getHostAddress(), port));
    }

    public TCPSocketAdvanced(Socket socket) throws IOException {

        this.socket = socket;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    public void writeInt(int i) throws IOException {
        dos.writeInt(i);
        dos.flush();
    }
    public int readInt() throws IOException {
        return dis.readInt();

    }

    public void writeString(String str) throws IOException {
        if (bufferedWriter == null) {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        bufferedWriter.write(str);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
    public String readString() throws IOException {
        if (bufferedReader == null) {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        return bufferedReader.readLine();
    }

    public void writeDouble(double d) throws IOException {
        dos.writeDouble(d);
        dos.flush();
    }
    public double readDouble() throws IOException {
        return dis.readDouble();
    }

    public void writeBoolean(boolean b) throws IOException {
        dos.writeBoolean(b);
        dos.flush();
    }
    public boolean readBoolean() throws IOException {
        return dis.readBoolean();
    }

    public void writeObject(Serializable o) throws IOException {
        if (oos == null) {
            oos = new ObjectOutputStream(socket.getOutputStream());
        }
        oos.writeObject(o);
        oos.flush();
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        if (ois == null) {
            ois = new ObjectInputStream(socket.getInputStream());
        }

        return ois.readObject();
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }
}
