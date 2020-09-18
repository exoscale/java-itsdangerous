package exoscale.itsdangerous;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Config {

    private byte[] salt;
    private Algorithm algorithm;
    private List<String> privateKeys;

    Config(final String salt,
           final Algorithm algorithm,
           final List<String> privateKeys) {
        this.salt = salt.getBytes();
        this.algorithm = algorithm;
        this.privateKeys = privateKeys;
    }

    private Boolean constantTimeEquals(final String astr, final String bstr) {
        final byte[] a = astr.getBytes();
        final byte[] b = bstr.getBytes();
        if (a.length != b.length) {
            return false;
        }
    
        int result = 0;
        for (int i = 0; i < a.length; i++) {
          result |= a[i] ^ b[i];
        }
        return result == 0;
    }

    private byte[] signPayload(final byte[] input, final byte[] key) throws UnsupportedEncodingException {
        try {
            final SecretKeySpec keyspec = new SecretKeySpec(key,
                                                            algorithm.toString());
            final Mac mac = Mac.getInstance(algorithm.toString());
            mac.init(keyspec);

            return mac.doFinal(input);
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedEncodingException(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new UnsupportedEncodingException(e.getMessage());
        }
    }

    private String signatureFor(final String toSign) throws UnsupportedEncodingException {
        return signatureFor(toSign, privateKeys.get(0));
    }

    private String signatureFor(final String toSign, final String privateKey) throws UnsupportedEncodingException {
        final byte[] derivedKey = signPayload(salt, privateKey.getBytes());
        return Codec.toBase64(signPayload(toSign.getBytes(), derivedKey));
    }

    public String sign(final String payload) throws UnsupportedEncodingException {
        return sign(payload, Epoch.now());
    }

    public String sign(final String payload, final Long epoch) throws UnsupportedEncodingException {
        final String toSign = Codec.toBase64(payload) + "." + Codec.integertoBase64(epoch);

        return toSign + "." + signatureFor(toSign);
    }

    private Boolean isValid(final Message message , final String privateKey) {
        try {
            return constantTimeEquals(message.getSignature(),
                                      signatureFor(message.getStringToSign(), privateKey));
        } catch (Exception e) {
            return false;
        }
    }

    public String verify(final String token, final Long maxAge) throws UnsupportedEncodingException, InvalidSignatureException, TokenValidityExpiredException {
        final Message message = new Message(token);

        final Boolean result = privateKeys
            .stream()
            .map(k -> isValid(message, k))
            .anyMatch(Boolean::booleanValue);

        if (!result) {
            throw new InvalidSignatureException();
        }

        if (maxAge == null) {
            return message.getPayload();
        }

        if (maxAge < (Epoch.now() - message.getTimestamp())) {
            throw new TokenValidityExpiredException();
        }
        return message.getPayload();
    }

    public String verify(final String token) throws UnsupportedEncodingException, InvalidSignatureException, TokenValidityExpiredException {
        return verify(token, null);
    }

}
