package exoscale.itsdanjerous;

/**
 * Utility class to retrieve current epoch in seconds
 */
public class Epoch {

    /**
     * Retrieve current epoch in seconds
     */
    static long now() {
        return (long)(System.currentTimeMillis() / 1000);
    }
}