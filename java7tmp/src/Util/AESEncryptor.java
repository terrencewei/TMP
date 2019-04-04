package Util;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by terrencewei on 2017/10/23.
 */
public class AESEncryptor {
    private static final String    KEY       = "cooyBfuNLnSPekwv";
    private static final String    INITV     = "OvFGGgdsjqrQlODH";
    private static final String    CHARSET   = "UTF-8";
    private static final String    ALGORITHM = "AES";

    private static IvParameterSpec ivps      = null;
    private static SecretKeySpec   sks       = null;

    static {
        try {
            ivps = new IvParameterSpec(INITV.getBytes(CHARSET));
            sks = new SecretKeySpec(KEY.getBytes(CHARSET), ALGORITHM);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }



    public static String encrypt(String pPlaintext) throws Exception {
        if (isEmpty(pPlaintext)) {
            return null;
        }

        Cipher cip = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cip.init(Cipher.ENCRYPT_MODE, sks, ivps);
        return new BASE64Encoder().encode(cip.doFinal(pPlaintext.getBytes()));
    }



    public static String decrypt(String pCiphertext) throws Exception {
        if (isEmpty(pCiphertext)) {
            return null;
        }
        Cipher cip = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cip.init(Cipher.DECRYPT_MODE, sks, ivps);
        return new String(cip.doFinal(new BASE64Decoder().decodeBuffer(pCiphertext)));
    }



    public static boolean isEmpty(String pStr) {
        return pStr == null || pStr.length() == 0;
    }
}
