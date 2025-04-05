//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.onechain.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @author lhb
 */
public class RsaSignUtil {
    private static final Logger log = LoggerFactory.getLogger(RsaSignUtil.class);


    public RsaSignUtil() {
    }

    public static String sign(Object object, String privateKeyStr) {
        try {
            Map<String, String> map = convertToMap(object);
            String content = createLinkString(map);
            log.info("signString:{}", content);
            byte[] keyBytes = decryptBase64(privateKeyStr);
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(content.getBytes(StandardCharsets.UTF_8));
            byte[] sig = signature.sign();
            return encryptBase64(sig).replaceAll("\n", "");
        } catch (
                Exception var10) {
            var10.printStackTrace();
            return null;
        }
    }

    public static String signMap(HashMap map, String privateKeyStr) {
        try {
            String content = createLinkString(map);
            byte[] keyBytes = decryptBase64(privateKeyStr);
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(content.getBytes(StandardCharsets.UTF_8));
            byte[] sig = signature.sign();
            return encryptBase64(sig).replaceAll("\n", "");
        } catch (
                Exception var9) {
            var9.printStackTrace();
            return null;
        }
    }

    public static boolean verify(String sign, Object object, String puklicKeyStr) {
        byte[] pubkeyBytes = decryptBase64(puklicKeyStr);
        X509EncodedKeySpec pubkey = new X509EncodedKeySpec(pubkeyBytes);
        Map<String, String> map = convertToMap(object);
        String content = createLinkString(map);

        try {
            log.info("signString:{}", content);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(pubkey);
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(content.getBytes(StandardCharsets.UTF_8));
            return signature.verify(decryptBase64(sign));
        } catch (
                Exception var10) {
            var10.printStackTrace();
            return false;
        }
    }

    public static Map<String, String> convertToMap(Object obj) {
        return paraFilter(JSON.parseObject(JSON.toJSONString(obj)));
    }

    private static Map<String, String> paraFilter(Map<String, Object> map) {
        Map<String, String> result = new HashMap<>();
        if (map != null && map.size() > 0) {
            for (String key : map.keySet()) {
                Object value = map.get(key);
                if (value != null && !"".equals(value) && !"merchantSign".equalsIgnoreCase(key)) {
                    String tmp;
                    if (value instanceof BigDecimal) {
                        tmp = ((BigDecimal) value).stripTrailingZeros().toPlainString();
                    } else if (value instanceof Long) {
                        tmp = String.valueOf(value);
                    } else {
                        tmp = value.toString();
                    }

                    result.put(key, tmp);
                }
            }

            return result;
        } else {
            return result;
        }
    }

    private static String createLinkString(Map<String, String> params) {
        return createLinkString(params, false);
    }

    public static String createLinkString(Map<String, String> params, boolean encode) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < keys.size(); ++i) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                value = urlEncode(value, "UTF-8");
            }

            if (i == keys.size() - 1) {
                stringBuilder.append(key).append("=").append(value);
            } else {
                stringBuilder.append(key).append("=").append(value).append("&");
            }
        }

        return stringBuilder.toString();
    }

    private static String urlEncode(String content, String charset) {
        try {
            return URLEncoder.encode(content, charset);
        } catch (
                UnsupportedEncodingException var3) {
            throw new RuntimeException("encoding fail :" + charset);
        }
    }

    private static byte[] decryptBase64(String key) {
        return Base64.getDecoder().decode(key);
    }

    private static String encryptBase64(byte[] key) {
        return Base64.getEncoder().encodeToString(key);
    }

}
