package com.example.algorithm;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SHAUtil {

    public static String encryptBySHA256(String paintText) {
        return encrypt(paintText, "SHA-256");
    }

    public static String encryptBySHA512(String paintText) {
        return encrypt(paintText, "SHA-512");
    }

    public static String encryptBySHA3512(String paintText) {
        return encrypt(paintText, "SHA3-512");
    }

    private static String encrypt(String paintText, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(paintText.getBytes(StandardCharsets.UTF_8));

            byte[] result = messageDigest.digest();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String message = "MESSAGE_Message_message";
        System.out.println("訊息: " + message);

        System.out.println("SHA256加密: " + encryptBySHA256(message));
        System.out.println("SHA512加密: " + encryptBySHA512(message));
        System.out.println("SHA3-512加密: " + encryptBySHA3512(message));
    }

}
