package Util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang.StringUtils;

/**
 * This is the encryptor tools used to encrypt MD5 or SHA1.
 *
 * @author: terrencewei
 * @version: 1.0, Aug 9, 2017
 */

public class HashEncryptor {

    public static String getMD5(String pPlaintext) throws Exception {
        if (StringUtils.isEmpty(pPlaintext)) {
            return null;
        }
        return new String(Base64.encodeBase64(DigestUtils.md5(pPlaintext)));
    }



    public static String getSHA1(String pPlaintext) throws Exception {
        if (StringUtils.isEmpty(pPlaintext)) {
            return null;
        }
        return new String(Base64.encodeBase64(DigestUtils.sha(pPlaintext)));
    }



    public static String getHMACSHA1(String pPlaintext, String pSharedSecretKey) {
        if (StringUtils.isEmpty(pPlaintext)) {
            return null;
        }
        if (pSharedSecretKey == null) {
            pSharedSecretKey = "";
        }
        return new String(Base64.encodeBase64(HmacUtils.hmacSha1(pSharedSecretKey.getBytes(), pPlaintext.getBytes())));
    }

}
