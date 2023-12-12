package fopt4.uebung2_3.login.test.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import fopt4.uebung2_3.login.Model;

public class ModelTest
{
    private Model model;

    @BeforeEach
    public void init()
    {
        model = new Model();
    }

    @Test
    public void testCorrectNameAndPassword()
    {
        assertTrue(model.isOkay("wolf", "passwordx4"), "Test f�r erfolgreiches Login");
    }


    @Test
    public void testWrongName()
    {
        assertFalse(model.isOkay("xxxwolf", "password4"), "Test f�r falschen Namen");
    }

    @Test
    public void testWrongPassword()
    {
        assertFalse(model.isOkay("wolf", "password1"), "Test f�r falsches Passwort");
    }
}
