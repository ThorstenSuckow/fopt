package praktikum.fopt3und4;

import java.util.Arrays;

public class VarArgDemo {

    public static void varArg(String... elements) {
        System.out.println(Arrays.toString(elements));
    }


    public static void main(String[] args) {
        varArg("1");
        varArg();
    }

}
