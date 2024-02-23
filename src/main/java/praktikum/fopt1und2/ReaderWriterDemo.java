package praktikum.fopt1und2;

public class ReaderWriterDemo {

    private final static int READ_SLEEP = 100;

    private final static int WRITE_SLEEP = 5000;


    private static class Book {

        private int activeReaders;

        private int activeWriters;

        private int waitingReaders;

        private int waitingWriters;

        private int requests;

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

            requests++;

            waitingWriters++;

            int ticket = requests;

            // only write if there are no activeReaders and no activeWriter available
            // -> wait if there is a write / read operation going on
            //while (activeReaders != 0 || activeWriters != 0) {
            while (activeReaders != 0 || activeWriters != 0) {
                try {
                    System.out.println("[write] " + Thread.currentThread().getName() + " waiting...(activeReaders: " + activeReaders + ", waitingReaders: " + waitingReaders + "; ticket " + ticket +")");
                    this.wait();
                } catch (InterruptedException e) {

                }
            }
            waitingWriters--;
            activeWriters++;
        }

        private synchronized void canRead() {

            requests++;
            waitingReaders++;

            int ticket = requests;

            // give writers priority
            // while (activeWriters != 0 || waitingWriters != 0) {
            while (activeWriters != 0 || waitingWriters != 0) {
                try {
                    System.out.println("[read] " + Thread.currentThread().getName() + " waiting...(activeWriters: " + activeWriters + ", waitingWriters: " + waitingWriters + "; ticket: " + ticket + ")");
                    this.wait();
                } catch (InterruptedException e) {

                }
            }


            waitingReaders--;
            activeReaders++;
        }

        private void reallyRead() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            System.out.println("   The book is being read by " + Thread.currentThread().getName());
        }

        private void reallyWrite() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            System.out.println("   The book is being written by " + Thread.currentThread().getName());
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
                System.out.println("[read] " + Thread.currentThread().getName() + " wants to read.");
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
                System.out.println("[write] " + Thread.currentThread().getName() + " wants to write.");
                book.write();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                }
            }
        }

    }



    public static void main (String[] args) {


        Book book = new Book();



        final int readers = 1;
        final int writers = 1;

        for (int i = 0; i < readers; i++) {
            new Reader(book);
        }

        for (int i = 0; i < writers; i++) {
            new Writer(book);
        }

    }


}
