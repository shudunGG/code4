package com.hanweb.yyjr.zhangye.util;

import com.alibaba.fastjson.JSONObject;
import com.github.houbb.heaven.util.codec.Hex;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 签名工具类 zhixy
 */
public class Rsa2SignUtil {
    private static final String SIGN_ALGORITHMS = "SHA256WithRSA";
    private static final String CHARSET = "utf-8";

     public static void main(String[] args) {
     // 平台公私钥
     String platformPriKey =
     "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANx4e5YOpvpvzF/BWZOzm/T+6I31FL1ca+GzIlQJUh0RZGxbHoAUxs2LzZ3k1aqV88DruCIBdajvdvvnZboESC5QNjf0/6pImy7Iw824E5NOG1bxcfvQHeRyzbtQwWmVMgiiQHEVThVXdBokuf0exLNVNdwa104UMHZ/gHp/A1u1AgMBAAECgYEApHjxYZmesffTVKAObmSwdQ3xeW2bQkT/aknT0zN84ZiG/VrgnxZg1Rh1lVQQngjt5j8zh5LlLtkuP3B58FKfcSJBMDyqRq5zzCQMEEmmoUoNTR7XL3GACUlqoc3pA273Ufchnmloni66beWjtYHlvmR3nrIm25/P2jZoCG8BOiECQQD92UYJg7yht6/lsfXa9UPRrPr2erKGkNQ/EO3nz0Abb4BSmh20szniTrPTs6wg/UIkFz291xbSN68Bc7YU1f6JAkEA3lbLkjc6d6wBVJk7djk2zfhqFfOP2I17uUvM1hwiEmOsyffSxb75GPn1GFskGp5Pjx5h0wKQFHz7K/TYG5BIzQJAN8fDtRmGnL6hX0bgsHGOqodpSjoh+qHekdElhferrZutxfjpqChuAsWqjE2B+GnOW+HjhlC6Sg1EXnxPuXpgiQJAQfmxaI7Y25//uRHLMjaiC5Z2LFNc0N6S4xYaD0LD3X8US84tDh6wHfg2AWcyHG/znb1fO9Ub2eiIQ6/g3LUA1QJBAOFgxFg5MnJ+Xrf34xfh3R/PfX7O/vq3KwhfSu2TnT0hTPmDaTfFKiav7xJZ1bChYVlPp0QNiyX8lHxAdtmCbJ8=";
     String platformPubKey =
     "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDceHuWDqb6b8xfwVmTs5v0/uiN9RS9XGvhsyJUCVIdEWRsWx6AFMbNi82d5NWqlfPA67giAXWo73b752W6BEguUDY39P+qSJsuyMPNuBOTThtW8XH70B3kcs27UMFplTIIokBxFU4VV3QaJLn9HsSzVTXcGtdOFDB2f4B6fwNbtQIDAQAB";

     JSONObject requestObject = new JSONObject();
     requestObject.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));

     Map<String, Object> params = new HashMap<>();
     params.put("code", "01");
     params.put("data", "NmEwMDYxZGM4OWVkNGFlMWE2NDcyM2U5ODE2NGIyYzc=");
     params.put("msg", "success");
     String sign = signByRSA(requestObject, platformPriKey);
     boolean check = verifyByRSA(requestObject, URLDecoder.decode(URLEncoder.encode(sign, StandardCharsets.UTF_8),StandardCharsets.UTF_8 ), platformPubKey);
     System.out.println(URLEncoder.encode(sign, StandardCharsets.UTF_8));
     System.out.println(URLDecoder.decode(sign, StandardCharsets.UTF_8));
     System.out.println(check);
     }

    public static String signByRSA(Map<String, Object> params, String privateKey) {
        try {
            String content = createLinkString(paramsFilter(params));
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(CHARSET));
            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verifyByRSA(Map<String, Object> params, String sign, String publicKey) {
        try {
            String content = createLinkString(paramsFilter(params));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(CHARSET));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Map<String, String> paramsFilter(Map<String, Object> params) {
        Map<String, String> result = new HashMap<>();
        if (params == null || params.size() == 0) {
            return result;
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() == null || "sign".equals(entry.getKey())) {
                continue;
            }
            result.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return result;
    }

    private static String createLinkString(Map<String, String> params) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (value != null && value.equals("") == false && // 忽略sign字段
                    key.equals("sign") == false) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }

    public  static String sm4Encode(String data, String key1) {
        Security.addProvider(new BouncyCastleProvider());

        try {
            // 这里假设你已经有了16字节的密钥数据，如果没有，可以使用随机数生成

            SecretKeySpec key = new SecretKeySpec(key1.getBytes(StandardCharsets.UTF_8), "SM4");
            Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS5Padding", "BC");
            byte[] input = data.getBytes("UTF-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
            int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
            ctLength += cipher.doFinal(cipherText, ctLength);
            return new String(Hex.encode(cipherText)).toLowerCase(Locale.ROOT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sm4Decode(String hexEncodedData, String key1) {
        Security.addProvider(new BouncyCastleProvider());

        try {
            SecretKeySpec key = new SecretKeySpec(key1.getBytes(StandardCharsets.UTF_8), "SM4");
            Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS5Padding", "BC");
            byte[] cipherText = Hex.decode(hexEncodedData.toUpperCase(Locale.ROOT));
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainText = new byte[cipher.getOutputSize(cipherText.length)];
            int ptLength = cipher.update(cipherText, 0, cipherText.length, plainText, 0);
            ptLength += cipher.doFinal(plainText, ptLength);
            return new String(plainText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
