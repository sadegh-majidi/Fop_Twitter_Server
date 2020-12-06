package service;


import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    private final SecureRandom random = new SecureRandom();
    private final Base64.Encoder encoder = Base64.getUrlEncoder();

    public String generate() {
        byte[] randoms = new byte[24];
        random.nextBytes(randoms);
        return encoder.encodeToString(randoms);
    }
}
