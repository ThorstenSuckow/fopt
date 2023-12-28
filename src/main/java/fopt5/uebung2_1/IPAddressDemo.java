package fopt5.uebung2_1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Includes uebung 2_2 (isReachable())
 *
 * Usage example:
 * <code>
 *  java IPAddressDemo host1 host2 host3
 * </code>
 *
 */
public class IPAddressDemo {

    public static int TIMEOUT = 20;
    public static String[] buildSampleHosts() {

        return new String[]{
            "www.google.de",
            "www.conjoon.org",
            "errorinhostname",
            "www.microsoft.com",
            "thorsten.suckow-homberg.de"
        };
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            args = buildSampleHosts();
        }

        StringBuilder s = new StringBuilder();
        s.append("Resolving host names...\n");
        for (String host: args) {

            s.append("Resolving host ").append(host);

            try {

                InetAddress main = InetAddress.getByName(host);
                s.append(" (").append(main.getHostName()).append(":");
                boolean reachable = false;

                InetAddress[] addresses = InetAddress.getAllByName(host);
                int j = 0;
                StringBuilder s2 = new StringBuilder();
                for (InetAddress address: addresses) {
                    s2.append(" ").append(j).append(": ").append(address.getHostAddress());

                    if (!reachable) {
                        try {
                            reachable = address.isReachable(20);
                        } catch (IOException ignored) {
                        }
                    }

                    if (j++ < addresses.length - 1) {
                        s2.append("\n");
                    }
                }
                if (!reachable) {
                    s.append(" not");
                }
                s.append(" reachable)\n");
                s.append(s2);

            } catch (UnknownHostException e) {
                s.append(" [error: unknown host]");
            }

            s.append("\n");
        }

        System.out.println(s.toString());
    }


}
