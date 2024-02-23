package praktikum.fopt1und2;

import java.util.ArrayList;
import java.util.List;

public class FairReaderWriterDemo {

    private final static int READ_SLEEP = 100;

    private final static int WRITE_SLEEP = 5000;


    private static class Book {

        private int activeReaders;

        private int activeWriters;

        private int waitingReaders;

        private int waitingWriters;

        private int requests;

        private List<Object> queue;

        public Book () {
            queue = new ArrayList<>();
        }

        public void read() {
            canRead();

            try {
                reallyRead();
            } finally {
                endRead();
            }
        }

        public void write() {
            canWrite();

            try {
                reallyWrite();
            } finally {
                endWrite();
            }
        }


        private synchronized void canWrite() {

            queue.add(Thread.currentThread());

            // only write if there is no reader, no other writer and the first in queue is a writer
            while (!(queue.get(0) instanceof Writer) || activeReaders > 0 || activeWriters > 0) {
                try {
                    System.out.println("[write] " + Thread.currentThread().getName() + " waiting... (activeReaders: " + activeReaders+ "; activeWriters: " + activeWriters + ")" );
                    this.wait();
                } catch (InterruptedException e) {

                }
            }
            activeWriters++;
            queue.removeFirst();

        }

        private synchronized void canRead() {

            queue.add(Thread.currentThread());

            // read if first in queue is reader and no writer is active
            while (!(queue.get(0) instanceof Reader) || activeWriters > 0) {
                try {
                    System.out.println("[read] " + Thread.currentThread().getName() + " waiting...(activeReaders: " + activeReaders+ "; activeWriters: " + activeWriters + ")");
                    this.wait();
                } catch (InterruptedException e) {

                }
            }
            activeReaders++;
            queue.removeFirst();
            // notifyAll() to let other READERS pass the sentinel
            notifyAll();
        }

        private void reallyRead() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            System.out.println("  [read] " + Thread.currentThread().getName());
        }

        private void reallyWrite() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            System.out.println("    [write] " + Thread.currentThread().getName());
        }

        private synchronized void endWrite() {
            activeWriters--;
            notifyAll();
        }

        private synchronized void endRead() {
            activeReaders--;
            notifyAll();
        }
    }

    private static class Reader extends Thread {
        private Book book;
        public Reader(Book book) {
            this.book = book;
            start();
        }

        public void run() {
            while (true) {
               // System.out.println("[read] " + Thread.currentThread().getName() + " wants to read.");
                book.read();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                }
            }
        }
    }

    private static class Writer extends Thread {

        private Book book;
        public Writer(Book book) {
            this.book = book;
            start();
        }

        public void run() {
            while (true) {
               // System.out.println("[write] " + Thread.currentThread().getName() + " wants to write.");
                book.write();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                }
            }
        }

    }



    public static void main (String[] args) throws InterruptedException {


        Book book = new Book();



        final int readers = 1;
        final int writers = 1;

        for (int i = 0; i < readers; i++) {
            new Reader(book);
            new Reader(book);
            new Reader(book);
        }

        Thread.sleep(1000);
        for (int i = 0; i < writers; i++) {
            new Writer(book);
        }

    }


}
