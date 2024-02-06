package klausurvorbereitung.uebungen;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.SocketException;

public class TypeCheckDemo {


    public static void main(String[] args)  {



        try (DatagramSocket updSocket = new DatagramSocket(1234);
             PrintWriter pw = new PrintWriter("foo.txt");) {


        } catch (SocketException | FileNotFoundException se) {

        }


    }

}
