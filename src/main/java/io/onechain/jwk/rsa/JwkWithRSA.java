package io.onechain.jwk.rsa;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chiyu
 * @date 2024/12/2 15:01
 */
public class JwkWithRSA {



    private final String kid;
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;
    private final Algorithm algorithm;

    private final Map<String, Object> header = new LinkedHashMap<>();


    protected JwkWithRSA(String kid, String priStr, String pubStr) {
        this.privateKey = getRsaPriKey(priStr);
        this.publicKey = getRsaPubKey(pubStr);
        this.kid = kid;
        this.algorithm = Algorithm.RSA256(publicKey, privateKey);
        header.put("alg", "RS256");
        header.put("typ", "JWT");
        header.put("kid", kid);
    }

    protected JwkWithRSA(String kid, RSAPrivateKey pri, RSAPublicKey pub) {
        this.privateKey = pri;
        this.publicKey = pub;
        this.kid = kid;
        this.algorithm = Algorithm.RSA256(publicKey, privateKey);
        header.put("alg", "RS256");
        header.put("typ", "JWT");
        header.put("kid", kid);
    }

    public static JwkWithRSA load(RSAKeyParam rsaKeyParam) {
        return new JwkWithRSA(rsaKeyParam.getKid(), rsaKeyParam.getPrivateKey(), rsaKeyParam.getPublicKey());
    }

    public String signatureJwt(Map<String, Object> payload) {
        return JWT.create().withPayload(payload).withHeader(header).sign(algorithm);
    }

    public boolean verify(String jwt) {
        DecodedJWT decode = JWT.decode(jwt);
        algorithm.verify(decode);
        return true;
    }

    public Map<String, String> toCert() {
        Map<String, String> jwk = new LinkedHashMap<>();
        jwk.put("kid", this.kid);
        jwk.put("kty", "RSA");
        jwk.put("alg", "RS256");
        jwk.put("use", "sig");
        jwk.put("n", Base64.getUrlEncoder().withoutPadding().encodeToString(publicKey.getModulus().toByteArray()));
        jwk.put("e", Base64.getUrlEncoder().withoutPadding().encodeToString(publicKey.getPublicExponent().toByteArray()));
        return jwk;
    }

    public String certToJson() {
        return new Gson().toJson(toCert());
    }


    private RSAPublicKey getRsaPubKey(String puklicKeyStr) {
        byte[] pubkeyBytes = Base64.getDecoder().decode(puklicKeyStr);
        X509EncodedKeySpec pubkey = new X509EncodedKeySpec(pubkeyBytes);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(pubkey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private RSAPrivateKey getRsaPriKey(String priKeyStr) {
        byte[] pubkeyBytes = Base64.getDecoder().decode(priKeyStr);
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(pubkeyBytes);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(priPKCS8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RSAKeyParam getParam() {
        RSAKeyParam rsaKeyParam = new RSAKeyParam();
        rsaKeyParam.setPrivateKey(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        rsaKeyParam.setPublicKey(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        rsaKeyParam.setKid(kid);
        return rsaKeyParam;
    }

    public static JwkWithRSA generateJwk() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPrivateKey rsaPriKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey rsaPubKey = (RSAPublicKey) keyPair.getPublic();
        return new JwkWithRSA(hashPubkey(rsaPubKey.getModulus()), rsaPriKey, rsaPubKey);
    }


    private static String hashPubkey(BigInteger n) {
        MessageDigest sha1 = null;
        try {
            sha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // 对BigInteger的字节表示进行哈希处理
        byte[] hashBytes = sha1.digest(n.toByteArray());
        // 将哈希字节数组转换为16进制字符串
        StringBuilder hexHash = new StringBuilder();
        for (byte b : hashBytes) {
            hexHash.append(String.format("%02x", b));
        }
        // 获取前40位的哈希值
        return hexHash.substring(0, 40);
    }

}
