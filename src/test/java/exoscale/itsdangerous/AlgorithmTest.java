package exoscale.itsdangerous;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class AlgorithmTest
{
    @Test
    public void shouldYieldSHA1()
    {
        assertTrue(Algorithm.SHA1.toString().equals("HmacSHA1"));
    }

    @Test
    public void shouldYieldSHA256()
    {
        assertTrue(Algorithm.SHA256.toString().equals("HmacSHA256"));
    }
}
