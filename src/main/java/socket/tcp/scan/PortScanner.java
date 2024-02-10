package socket.tcp.scan;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class PortScanner {

    private InetAddress target;

    public static int connectionTimeout = 250;
    public static boolean verbose = true;
    private int from;

    private int to;

    public PortScanner(InetAddress address, int from, int to) {

        if (from <= 0 || to >= 65_536 || to < from) {
            throw new IllegalArgumentException();
        }

        target = address;
        this.from = from;
        this.to = to;
    }


    public List<Integer> scan() {

        int port = from;

        int timeout = Math.abs(connectionTimeout);

        List<Integer> connections = new ArrayList<>();
        while (port <= to) {

            final InetSocketAddress sckAddr = new InetSocketAddress(target, port);
            try(Socket scanSocket = new Socket()) {
                log("[scan] trying to connect to \"" + sckAddr + "\"...");

                scanSocket.connect(sckAddr, timeout);
                log("[scan]    connection established  to \"" + sckAddr + "\" >]");
                connections.add(port);
            } catch (IOException e) {
                logErr("[scan]     error while trying to connect to " + sckAddr + ": " + e);
            }

            port++;
        }

        return connections;
    }

    private void logErr(String msg) {
        if (!verbose) {
            return;
        }
        System.err.println(msg);
    }

    private void log(String msg) {
        if (!verbose) {
            return;
        }
        System.out.println(msg);
    }


    public static void main(String[] args) {

        if (args.length < 3) {
            throw new IllegalArgumentException();
        }

        InetAddress address;

        try {
            address = InetAddress.getByName(args[0]);
        } catch (UnknownHostException | SecurityException e) {
            System.err.println("[scan] failed to resolve host \"" + args[0] + "\"");
            return;
        }

        int from;
        int to;
        try {
            from = Integer.parseInt(args[1]);
            to = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("[scan] invalid port numbers submitted: \"from\" was " + args[1] + ", \"to\" was "+ args[2]);
            return;
        }

        PortScanner scanner = new PortScanner(address, from, to);
        List<Integer> openPorts = scanner.scan();

        if (openPorts.isEmpty()) {
            System.out.println("[scan] no open ports found for " + address + ":[" + from + ", " +  to+ "]");
            return;
        }
        for(Integer port: openPorts) {
            System.out.println("[scan] found open port " + port);
        }

    }
}