package oop.counter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IncrementerTest {

    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    @DisplayName("Incrementer")
    void testIncrementer() {

        Incrementer inc = new Incrementer(1);

        Assertions.assertSame(1, inc.getValue());
        inc.increment();
        Assertions.assertSame(2, inc.getValue());
        inc.increment();
        Assertions.assertSame(3, inc.getValue());

    }

}
