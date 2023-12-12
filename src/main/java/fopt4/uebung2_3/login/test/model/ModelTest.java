package fopt4.uebung2_3.login.test.model;

import fopt4.uebung2_3.login.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
