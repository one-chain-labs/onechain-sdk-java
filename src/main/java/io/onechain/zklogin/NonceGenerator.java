package io.onechain.zklogin;

import io.onechain.crypto.SignatureScheme;
import io.onechain.crypto.OneChainKeyPair;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;


public class NonceGenerator {
    public static final int NONCE_LENGTH = 27;

    public static String toHEX(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            // 将每个字节转换为两位的十六进制字符串
            String hex = String.format("%02x", b & 0xFF);
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // 将字节数组转换为大整数
    public static BigInteger toBigIntBE(byte[] bytes) {
        String hex = toHEX(bytes);
        if (hex.length() == 0) {
            return BigInteger.ZERO;
        }
        // 将十六进制字符串转换为 BigInteger
        return new BigInteger(hex, 16);
    }
    public static String generateNonce(byte[] bytes, BigInteger maxEpoch, BigInteger randomness) {
        // 将公钥字节数组转换为大端序的 BigInteger
        BigInteger publicKeyBytes = toBigIntBE(bytes);
        // 计算公钥的两部分
        BigInteger eph_public_key_0 = publicKeyBytes.divide(BigInteger.valueOf(2).pow(128));
        BigInteger eph_public_key_1 = publicKeyBytes.mod(BigInteger.valueOf(2).pow(128));
        // 构建输入数组
        BigInteger[] inputs = {eph_public_key_0, eph_public_key_1, maxEpoch, randomness};
        // 计算 Poseidon 哈希
        BigInteger bigNum = Poseidon.poseidon(inputs);
        // 将哈希结果转换为指定长度的大端序字节数组
        byte[] Z = toPaddedBigEndianBytes(bigNum, 20);
        // 使用 Base64 URL 编码
        String nonce = Base64.getUrlEncoder().withoutPadding().encodeToString(Z);
        // 检查生成的随机数长度是否符合要求
        if (nonce.length() != NONCE_LENGTH) {
            throw new RuntimeException("Length of nonce " + nonce + " (" + nonce.length() + ") is not equal to " + NONCE_LENGTH);
        }
        return nonce;
    }

    public static BigInteger generateRandomness(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] entropy = new byte[16];
        secureRandom.nextBytes(entropy);
        return toBigIntBE(entropy);
    }

    private static byte[] toPaddedBigEndianBytes(BigInteger value, int length) {
        byte[] bytes = new byte[length];
        byte[] valueBytes = value.toByteArray();
        if (valueBytes.length <= length) {
            System.arraycopy(valueBytes, 0, bytes, length - valueBytes.length, valueBytes.length);
        } else {
            System.arraycopy(valueBytes, valueBytes.length - length, bytes, 0, length);
        }
        return bytes;
    }
    public static void main(String[] args) {
        // 示例公钥字节数组
        byte[] priKey = {SignatureScheme.ED25519.getScheme(),
                (byte) 155, (byte) 244, (byte) 154, (byte) 106, (byte) 7, (byte) 85, (byte) 249, (byte) 83,
                (byte) 129, (byte) 31, (byte) 206, (byte) 18, (byte) 95, (byte) 38, (byte) 131, (byte) 213,
                (byte) 4, (byte) 41, (byte) 195, (byte) 187, (byte) 73, (byte) 224, (byte) 116, (byte) 20,
                (byte) 126, (byte) 0, (byte) 137, (byte) 165, (byte) 46, (byte) 174, (byte) 21, (byte) 95
        };
        OneChainKeyPair<?> oneChainKeyPair = OneChainKeyPair.decodeBase64(Base64.getEncoder().encodeToString(priKey));
        System.out.println(oneChainKeyPair.publicKey());

        BigInteger maxEpoch = BigInteger.valueOf(70);
        BigInteger randomness = new BigInteger("94854898718581193458284373993138886850");
        // 生成随机数
        String nonce = generateNonce(oneChainKeyPair.publicKeyBytes(), maxEpoch, randomness);
        System.out.println("Generated Nonce: " + nonce);
        System.out.println("Generated Nonce: " + "_2Og_nSmIWQE20Dl19usp3vxc2M");
        System.out.println(generateRandomness());
        System.out.println("94854898718581193458284373993138886850");
    }
}