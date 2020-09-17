package exoscale.itsdanjerous;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.assertTrue;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;

public class ConfigTest
{
    @Test
    public void shouldVerifyAndSign() throws UnsupportedEncodingException, InvalidSignatureException, TokenValidityExpiredException
    {
        final Config cfg = new Config("my-salt", Algorithm.SHA256, List.of("new-secret", "old-secret"));
        final Long timestamp = Long.valueOf(3);

        String token = cfg.sign("HELLO", timestamp);


        System.out.println(token);
        assertTrue( cfg.verify(token).equals("HELLO"));
        assertThrows(IllegalArgumentException.class, () -> { cfg.verify("foobar");});
        assertThrows(TokenValidityExpiredException.class, () -> {cfg.verify(token, 0L);});
    }
}
