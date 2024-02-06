package klausurvorbereitung.uebungen;

public class TryCatchDemo {

    public String m1(boolean exc) {


        try {

            System.out.println("try 1.");

            if (exc) {
                throw new Exception();
            }
            System.out.println("try 2.");

            return "foo";

        } catch (Exception e) {

            return "Exception.";

        } finally {
            System.out.println("finally.");
        }
    }

    public static void main(String[] args) {
       TryCatchDemo demo = new TryCatchDemo();
       System.out.println(demo.m1(false));
       System.out.println();
       System.out.println(demo.m1(true));
    }

}
