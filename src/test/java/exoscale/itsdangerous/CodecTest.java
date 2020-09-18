package exoscale.itsdangerous;

import static org.junit.Assert.assertTrue;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class CodecTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldCoerceKnownValues() throws UnsupportedEncodingException
    {
        final String b64 = Codec.toBase64("foobar");
        assertTrue( (Codec.toBase64("foobar").equals(b64)) );
    }
}
