package jag.opcode.login.pow;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProofOfWork {

    private final MessageDigest algorithm;

    ProofOfWork() {
        algorithm = getAlgorithm();
    }

    boolean apply(int requiredZeroBitCount, String seed, long nonce) {
        byte[] digestedBytes = digest(seed, nonce);
        return getZeroBitCount(digestedBytes) >= requiredZeroBitCount;
    }

    public byte[] digest(String seed, long nonce) {
        algorithm.reset();
        algorithm.update((seed + Long.toHexString(nonce)).getBytes(StandardCharsets.UTF_8));
        return algorithm.digest();
    }

    public MessageDigest getAlgorithm() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    static int getZeroBitCount(byte[] digested) {
        int accumulated = 0;
        for (byte value : digested) {
            int count = getZeroBitCount(value);
            accumulated += count;
            if (count != 8) {
                break;
            }
        }
        return accumulated;
    }

    public static int getZeroBitCount(byte value) {
        int count = 0;
        if (value == 0) {
            count = 8;
        } else {
            for (int a = value & 255; (a & 128) == 0; a <<= 1) {
                ++count;
            }
        }
        return count;
    }
}
