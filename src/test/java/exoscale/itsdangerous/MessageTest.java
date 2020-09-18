package exoscale.itsdangerous;

import static org.junit.Assert.assertTrue;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class MessageTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldParseToken() throws UnsupportedEncodingException
    {
        final Message message = new Message("SEVMTE8.nppGBrCjzE0Ipz1pzm6gRLwi_rc");

        assertTrue(message.getPayload().equals("HELLO"));
        assertTrue(message.getTimestamp() == null);
        assertTrue(message.getSignature().equals("nppGBrCjzE0Ipz1pzm6gRLwi_rc"));
        assertTrue(message.getStringToSign().equals("SEVMTE8"));
    }
}
