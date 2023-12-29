package lib;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

/**
 * Listing 5.4 [Oec22, 277]
 *
 * Added param port and removed casting to MuliticastSocket in join()/leave(), since DatagramSockets
 * implement joinGroup since Java 17.
 * Apparently, the port specified for the InetAddress in joinGroup()/leaveGroup() is ignored, and the port
 * of the MulticastSocket is given precedence.
 */
public class UDPMulticastSocket extends UDPSocket{


    public UDPMulticastSocket(int port) throws IOException {
        super(new MulticastSocket(6789));
    }


    public void join(String mcAddress) throws IOException {
        InetSocketAddress group = new InetSocketAddress(mcAddress, 0);
        socket.joinGroup(group, null);
    }

    public void leave(String mcAddress) throws IOException {
        InetSocketAddress group = new InetSocketAddress(mcAddress, 0);
        socket.leaveGroup(group, null);
    }


}
