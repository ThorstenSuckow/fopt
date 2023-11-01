package fopt2.sandbox;

import java.util.Arrays;

public class Pipe {


    final byte[] pipe;

    int head = 0;

    int tail = 0;

    int numberOfElements = 0;
    int size;

    public Pipe(int size) {

        pipe = new byte[size];
        this.size = size;

    }


    /**
     * Splits the message in parts equal to the size of this Pipe.
     * @param message
     * @return
     */
    private byte[][] splitMessage(byte[] message) {
        byte[][] parts;
        if (message.length > size) {
            int factor = (int) Math.ceil(message.length / (double) size);
            parts = new byte[factor][];

            System.out.println("factor: " + factor);
            for (int i = 0; i < factor; i++) {
                int from = size * i;
                int to = size * (i + 1);
                parts[i] = Arrays.copyOfRange(message, from, Math.min(to, message.length));
                System.out.println("copied - from:" + from + "; to: " + to + "; part: " + Arrays.toString(parts[i]));
            }
        } else {
            parts = new byte[1][];
            parts[0] = message;
        }

        return parts;
    }

    private void updatePipe(byte[] message) {

        for (int i = 0; i < message.length; i++) {
            pipe[tail++] = message[i];
            tail %= size;
            numberOfElements++;
        }
    }

    private byte[] readPipe(int length) {

        byte[] copy = new byte[length];
         for (int i = 0; i < length; i++) {
            copy[i] = pipe[head++];
            head %= size;
            numberOfElements--;
        }

        return copy;
    }

    /**
     * message > size: split message
     * message < size: wait for size - numberOfElements = message.length
     *
     * @param message
     */
    public synchronized void put(byte[] message) {


        if (message.length > size) {
            byte[][] messageParts = splitMessage(message);
            for (int i = 0; i < messageParts.length; i++) {
                put(messageParts[i]);
            }

            return;
        }

        while (numberOfElements == size || size - numberOfElements < message.length) {
            try {
                System.out.println("putting message on hold: " + Arrays.toString(message));
                this.wait();
            } catch (InterruptedException ignored) {
            }
        }

        System.out.println("updating pipe with message " + Arrays.toString(message));
        updatePipe(message);
        System.out.println(" => current pipe: " + Arrays.toString(pipe));

        notifyAll();
    }

    public synchronized byte[] get(int length) {

        if (length > size) {
            throw new IllegalArgumentException();
        }

        while (numberOfElements == 0) {
            try {
                System.out.println("waiting for pipe to contain data...");
                this.wait();
            } catch (InterruptedException ignored) {
            }
        }

        System.out.println("Pipe contains data!");

        byte[] message = readPipe(Math.min(length, numberOfElements));

        System.out.println("getting message from pipe " + Arrays.toString(message));
        notifyAll();
        return message;
    }


    @Override
    public String toString() {
        return Arrays.toString(pipe);
    }
}
