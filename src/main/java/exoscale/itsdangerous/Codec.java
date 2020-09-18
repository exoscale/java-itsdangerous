package exoscale.itsdangerous;

import java.util.Base64;
import java.io.UnsupportedEncodingException;

/**
 * Utility class to perform conversions to and from Base64.
 */
public class Codec {
    /**
     * URL encode a byte array to base64
     */
    static String toBase64(final byte[] ba) {
        final Base64.Encoder encoder = Base64.getUrlEncoder();
        final String s = new String(encoder.encode(ba));
        return s.replaceFirst("=+$", "");
    }

    /**
     * URL encode an input string to base64
     */
    static String toBase64(final String input) throws UnsupportedEncodingException {
        final byte[] ba = input.getBytes("UTF-8");
        return toBase64(ba);
    }

    /**
     * Convert an URL encoded base64 input string to a byte array
     */
    static byte[] toBytes(final String input) throws UnsupportedEncodingException {
        final Base64.Decoder decoder = Base64.getUrlDecoder();
        final byte[] ba = input.getBytes("UTF-8");
        return decoder.decode(ba);
    }

    /**
     * Convert an URL encoded base64 input string to a string
     */
    static String toPlainString(final String input) throws UnsupportedEncodingException {
        final String output = new String(toBytes(input), "UTF-8");
        return output;
    }

    /**
     * Bit shifts for 4-byte integers
     */
    static int[] BIT_SHIFTS = new int[] { 24, 16, 8, 0 };

    /**
     * Convert an integer to a 4-wide byte-array
     */
    static byte[] integerToBytes(final long n) {
        int i = 0;
        final byte[] ba = new byte[4];

        for (final int shift : BIT_SHIFTS) {
            ba[i++] = (byte) ((n >> shift) & 0xff);
        }
        return ba;
    }

    /**
     * Convert a 4-wide byte-array to a signed integer
     */
    static Long bytesToInteger(final byte[] ba) {
        int i = 0;
        Long res = Long.valueOf(0);

        for (final int shift : BIT_SHIFTS) {
            res |= (ba[i++] & 0xff) << shift;
        }
        return res;
    }

    /**
     * Convert an integer to base64
     */
    static String integertoBase64(final long n) {
        return toBase64(integerToBytes(n));
    }

    /**
     * Convert a base64 string to an integer
     */
    static Long base64ToInteger(final String input) throws UnsupportedEncodingException {
        return bytesToInteger(toBytes(input));
    }
}
