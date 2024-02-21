package praktikum.fopt5;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.sql.Date;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class TCPSerializedDemo {

    static class DummySerializable implements Serializable {

        private String id;

        public DummySerializable(String id) {
            this.id = id;
        }

        public String toString() {
            return "[DummySerializable] " + id;
        }
    }

    public static void main (String... args) {

        if (args.length == 0) {

            // create server
            try (ServerSocket server = new ServerSocket(9999)) {

                while (true) {

                    try (TCPSocketAdvanced client = new TCPSocketAdvanced(server.accept())) {

                        // our protocol:
                        // String - Int, Double, Bool, Object
                       client.writeString("server received str: " + client.readString());

                        client.writeString("server received int: " + client.readInt());

                        client.writeString("server received double: " + client.readDouble());

                        client.writeString("server received bool: " + client.readBoolean());

                        client.writeString("server received object: " + client.readObject());

                    } catch (Exception e) {
                        System.err.println("[server] " + e);
                    }
                }


            } catch (Exception e) {
                System.err.println("[error] " + e);
            }

        } else {

            try(TCPSocketAdvanced client = new TCPSocketAdvanced(9999)) {

                // our protocol:
                // String - Int, Double, Bool, Object
                System.out.println("client writes String Hello World!");
                client.writeString("Hello World!");
                System.out.println("  server said: " + client.readString());


                System.out.println("client writes int 9999");
                client.writeInt(9999);
                System.out.println("  server said: " + client.readString());

                System.out.println("client writes double 8888d");
                client.writeDouble(8888d);
                System.out.println("  server said: " + client.readString());

                System.out.println("client writes boolean true");
                client.writeBoolean(true);
                System.out.println("  server said: " + client.readString());

                System.out.println("client writes Serializable object");
                DummySerializable obj = new DummySerializable(
                    LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                );
                client.writeObject(obj);
                System.out.println("  server said: " + client.readString());

            } catch (Exception e) {
                System.err.println("[client] " + e);
            }


        }



    }


}
