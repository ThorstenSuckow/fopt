package fopt1.uebung2_2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Loop1Test {

    @Test
    @DisplayName("Loop1 implements Runnable")
    void implementsInterface()
    {
        assertTrue(Runnable.class.isAssignableFrom(Loop1.class));
    }

}