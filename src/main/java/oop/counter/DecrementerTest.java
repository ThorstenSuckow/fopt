package oop.counter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DecrementerTest {

    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    @DisplayName("Decrementer")
    void testDecrementer() {

        Decrementer dec = new Decrementer(1);

        Assertions.assertSame(1, dec.getValue());
        dec.decrement();
        Assertions.assertSame(0, dec.getValue());
        dec.decrement();
        Assertions.assertSame(-1, dec.getValue());

    }

}
