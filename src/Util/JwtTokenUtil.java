package Util;

import java.util.Base64;
import java.util.UUID;

public class JwtTokenUtil {
    private static final String SECRET_KEY = "airline_reservation_system_secret_key_2026";

    public static String generateToken(String subject, String userType) {
        long issuedAt = System.currentTimeMillis();
        long expiresAt = issuedAt + (3600 * 1000); // 1 hour

        String payload = subject + "|" + userType + "|" + issuedAt + "|" + expiresAt;
        String encoded = Base64.getEncoder().encodeToString(payload.getBytes());
        
        return encoded + "." + generateSignature(encoded);
    }

    public static boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 2) return false;

            String payload = new String(Base64.getDecoder().decode(parts[0]));
            String[] data = payload.split("\\|");

            long expiresAt = Long.parseLong(data[3]);
            return System.currentTimeMillis() < expiresAt;
        } catch (Exception e) {
            return false;
        }
    }

    private static String generateSignature(String encoded) {
        return Base64.getEncoder().encodeToString((encoded + SECRET_KEY).getBytes());
    }
}
