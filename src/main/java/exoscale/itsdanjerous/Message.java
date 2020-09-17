package exoscale.itsdanjerous;

import java.util.regex.Pattern;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;

public class Message {

    private String payload;
    private String signature;
    private String stringToSign;
    private Long timestamp;

    private static final Pattern TOKEN_PATTERN = Pattern.compile("^([^.]*)\\.(?:([^.]+)\\.)?([^.]+)$");

    public Message(final String token) throws UnsupportedEncodingException {

        final Matcher m = TOKEN_PATTERN.matcher(token);

        if (!m.matches()) {
            throw new IllegalArgumentException("invalid token");
        }

        payload = Codec.toPlainString(m.group(1));
        if (m.group(2) != null) {
            stringToSign = m.group(1) + "." + m.group(2);
            timestamp = Codec.base64ToInteger(m.group(2));
        } else {
            stringToSign = m.group(1);
            timestamp = null;
        }
        signature = m.group(3);
    }

    public String getPayload() {
        return payload;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public String getStringToSign() {
        return stringToSign;
    }
}
