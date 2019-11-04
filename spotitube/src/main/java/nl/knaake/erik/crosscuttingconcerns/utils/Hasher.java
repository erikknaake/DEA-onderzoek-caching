package nl.knaake.erik.crosscuttingconcerns.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Offers hashing functions
 */
public class Hasher {
    /**
     * Hashes the input with SHA-512
     * @param input String to hash
     * @return Hashed input as hex string
     */
    public static String hash(String input) {
        String result = null;
        try {
            result = String.format("%040x", new BigInteger(1, MessageDigest.getInstance("SHA-512").digest((input).getBytes())));
        }
        catch (NoSuchAlgorithmException e) { }
        return result;
    }

}
