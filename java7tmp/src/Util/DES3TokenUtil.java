package Util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by terrence on 2018/05/29.
 */
public class DES3TokenUtil {

    // unicode
    public static final String UTF8 = "UTF-8";

    // the secure key used for 3DES
    private static final String DES3_SECRET_KEY     = "xxxxx";
    private static final String HMACSHA1_SECRET_KEY = "xxxxxxx";
    // algorithm name - "DESede" presents 3DES
    private static final String KEY_ALGORITHM       = "DESede";
    // Auth key length
    private static final int    AUTH_LENGTH         = 40;
    private static final int    AUTH_ENCRYPT_LENGTH = 64;



    /**
     * get token by origin String
     *
     * @param pOriginStr
     * @return
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public static String getToken(String pOriginStr)
            throws IOException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            NoSuchPaddingException, InvalidKeyException {
        if (pOriginStr == null || pOriginStr.length() == 0) {
            return null;
        }
        String auth = Base64
                .encodeBase64String(getBytes(getHMACSHA1(UUID.randomUUID().toString(), HMACSHA1_SECRET_KEY)));
        return new StringBuilder(Base64.encodeBase64String(getBytes(pOriginStr))).append(auth).append(Base64
                .encodeBase64String(encrypt(getBytes(auth), DES3_SECRET_KEY))).toString();
    }



    /**
     * parse token by token
     *
     * @param pToken
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static String parseToken(String pToken)
            throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException,
            IllegalBlockSizeException, InvalidKeyException {
        if (pToken == null || pToken.length() == 0) {
            return null;
        }
        int totalAuthLength = AUTH_LENGTH + AUTH_ENCRYPT_LENGTH;
        if (pToken.length() <= totalAuthLength) {
            return null;
        }
        int infoLength = pToken.length() - totalAuthLength;
        if (!pToken.substring(infoLength, pToken.length() - AUTH_ENCRYPT_LENGTH).equals(new String(
                decrypt(Base64.decodeBase64(pToken.substring(pToken.length() - AUTH_ENCRYPT_LENGTH)),
                        DES3_SECRET_KEY)))) {
            return "Invalid token";
        }
        return new String(Base64.decodeBase64(getBytes(pToken.substring(0, infoLength))));
    }



    private static String getHMACSHA1(String pOrigintext, String pSecretKey) throws UnsupportedEncodingException {
        if (pOrigintext == null || pOrigintext.length() == 0) {
            return null;
        }
        if (pSecretKey == null) {
            pSecretKey = "";
        }
        return new String(Base64.encodeBase64(HmacUtils.hmacSha1(getBytes(pSecretKey), getBytes(pOrigintext))));
    }



    private static byte[] encrypt(final byte[] pOriginBytes, final String pSecretKey)
            throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException,
            NoSuchAlgorithmException, NoSuchPaddingException {
        final Key seckey = new SecretKeySpec(get24BytesArray(Base64.encodeBase64(getBytes(pSecretKey))), KEY_ALGORITHM);
        final Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, seckey);
        return cipher.doFinal(pOriginBytes);
    }



    private static byte[] decrypt(final byte[] pEncryptedBytes, final String pKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        final Key seckey = new SecretKeySpec(get24BytesArray(Base64.encodeBase64(getBytes(pKey))), KEY_ALGORITHM);
        final Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, seckey);
        return cipher.doFinal(pEncryptedBytes);
    }



    private static byte[] get24BytesArray(byte[] pKeyBytes) throws UnsupportedEncodingException {
        byte[] key = new byte[24];
        byte[] temp = pKeyBytes;

        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }



    private static byte[] getBytes(String pStr) throws UnsupportedEncodingException {
        if (pStr == null || pStr.length() == 0) {
            return null;
        }
        return pStr.getBytes(UTF8);
    }
}