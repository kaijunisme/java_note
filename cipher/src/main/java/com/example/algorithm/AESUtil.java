package com.example.algorithm;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    // KEY 長度只能是16,25或32個字節
    private static final byte[] KEY = "12345678abcdefgh".getBytes(StandardCharsets.UTF_8);
    // CBC 模式需要用到初始向量参数
    private static final byte[] IV = "IVIVIVIVIVIVIVIV".getBytes(StandardCharsets.UTF_8);
    // GCM 模式使用，長度無限制並且可為空
    private static final byte[] AAD = "".getBytes(StandardCharsets.UTF_8);
    // GCM 模式使用，tag 長度必須是128,120,112,104,96其中之一
    private static final int TAG_LENGTH = 128;
    private static final String ALGORITHM = "AES";
    private static final String ECB = "AES/ECB/PKCS5Padding";
    private static final String CBC = "AES/CBC/PKCS5Padding";
    private static final String GCM = "AES/GCM/NoPadding";

    /**
     * 使用 ECB 模式加密
     *
     * @param plaintext
     * @return
     */
    public static String encryptByECB(String plaintext) {
        try {
            Cipher cipher = Cipher.getInstance(ECB);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY, ALGORITHM));

            byte[] result = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用 ECB 模式解密
     *
     * @param ciphertext
     * @return
     */
    public static String decryptByECB(String ciphertext) {
        try {
            Cipher cipher = Cipher.getInstance(ECB);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY, ALGORITHM));

            byte[] result = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new String(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用 CBC 模式加密
     *
     * @param plaintext
     * @return
     */
    public static String encryptByCBC(String plaintext) {
        try {
            Cipher cipher = Cipher.getInstance(CBC);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY, ALGORITHM), new IvParameterSpec(IV));

            byte[] result = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用CBC 模式解密
     *
     * @param ciphertext
     * @return
     */
    public static String decryptByCBC(String ciphertext) {
        try {
            Cipher cipher = Cipher.getInstance(CBC);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY, ALGORITHM), new IvParameterSpec(IV));

            byte[] result = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new String(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用 GCM 模式加密
     *
     * @param plaintext
     * @return
     */
    public static String encryptByGCM(String plaintext) {
        try {
            Cipher cipher = Cipher.getInstance(GCM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY, ALGORITHM), new GCMParameterSpec(TAG_LENGTH, IV));
            cipher.updateAAD(AAD);

            byte[] result = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用 GCM 模式解密
     *
     * @param ciphertext
     * @return
     */
    public static String decryptByGCM(String ciphertext) {
        try {
            Cipher cipher = Cipher.getInstance(GCM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY, ALGORITHM), new GCMParameterSpec(TAG_LENGTH, IV));
            cipher.updateAAD(AAD);

            byte[] result = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new String(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String message = "MESSAGE_Message_message", ciphertext;
        System.out.println("訊息: " + message);

        System.out.println("=== ECB ===");
        ciphertext = encryptByECB(message);
        System.out.println("ECB密文: " + ciphertext);
        System.out.println("ECB明文: " + decryptByECB(ciphertext));

        System.out.println("=== CBC ===");
        ciphertext = encryptByCBC(message);
        System.out.println("CBC密文: " + ciphertext);
        System.out.println("CBC明文: " + decryptByCBC(ciphertext));

        System.out.println("=== GCM ===");
        ciphertext = encryptByGCM(message);
        System.out.println("GCM密文: " + ciphertext);
        System.out.println("GCM明文: " + decryptByGCM(ciphertext));
    }

}
