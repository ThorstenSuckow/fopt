package sandbox;

public class LooperDemo {

    static class Looper {

        public Looper() {
             //init(); // falsch
            new Thread(this::init).start(); // richtig
            System.out.println("done");
        }

        private void init() {
            while (true) {

            }
        }

    }

    public static void main(String... args) {

        Looper l = new Looper();

    }

}
