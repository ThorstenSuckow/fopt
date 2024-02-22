package praktikum.fopt5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.URL;
import java.util.Arrays;

public class FileReaderDemo {


    public static void main(String... args) throws IOException {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        String fileName = "./praktikum/fopt5/demo.txt";

        URL fileUrl = classloader.getResource(fileName);

        InputStream is = classloader.getResourceAsStream(fileName);

        FileOutputStream os = new FileOutputStream(fileUrl.getPath());

        //System.out.println("Writing bytes " + Arrays.toString("Hello World".getBytes()));
        //os.write("Hello World".getBytes()); // results in hex 4865 6c6c 6f20 576f 726c 64
                                            // translates to
                                            // dec 72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100
        //os.flush();
        //os.close();

        DataOutputStream dos = new DataOutputStream(os);
        dos.writeBoolean(true);
        dos.writeBoolean(false);
        dos.writeShort(99);
        dos.writeInt(100);
        dos.writeShort(101);


        dos.flush();
        dos.close();


        DataInputStream dis = new DataInputStream(new FileInputStream(fileUrl.getPath()));
        System.out.println("available: " + dis.available());
        System.out.println("reading: " + dis.readBoolean());

        // if throwing readLong in here, the stream will try to assemble 8 bytes (64 bits) in here
        // moving the file pointer 8 bytes ahead, skipping previously written bytes that were
        // meant to be of a specific type
        System.out.println("available: " + dis.available());
        System.out.println("reading: " + dis.readBoolean());

        System.out.println("available: " + dis.available());
        System.out.println("reading: " + dis.readShort());

        System.out.println("available: " + dis.available());
        System.out.println("reading: " + dis.readInt());

        System.out.println("available: " + dis.available());
        System.out.println("reading: " + dis.readShort());

        // 0 available - next read throws!
        System.out.println("available: " + dis.available());

        try {
            System.out.println("next read throws, since " + dis.read() + " available");
            dis.readShort();
        } catch (IOException e) {
            System.out.println("file end");
        }


        //PrintStream will convert to String, then write the resulting bytes into the file
        // https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/PrintStream.html
        // "All characters printed by a PrintStream are converted into bytes using the given #
        // encoding or charset, or the default charset if not specified. The PrintWriter class
        // should be used in situations that require writing characters rather than bytes."
        //  PrintStream pis = new PrintStream(new FileOutputStream(fileUrl.getPath())):
        //pis.println(true);

    }


}
