package io.onechain.zklogin;

import org.bouncycastle.crypto.digests.Blake2bDigest;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import static org.bouncycastle.util.Arrays.prepend;

public class ZkLoginAddressUtil {

    // 假设 SIGNATURE_SCHEME_TO_FLAG.ZkLogin 对应的值为 1，需要根据实际情况调整
    private static final int ZKLOGIN_FLAG = 0x05;
    // 假设 ONECHAIN_ADDRESS_LENGTH 为 20，需要根据实际情况调整
    private static final int ONECHAIN_ADDRESS_LENGTH = 32;

    /**
     * 计算 ZkLogin 地址
     *
     * @param claimName     声明名称
     * @param claimValue    声明值
     * @param iss           颁发者
     * @param aud           受众
     * @param userSalt      用户盐值
     * @param legacyAddress 是否使用旧地址格式
     * @return 计算得到的 ZkLogin 地址
     */
    public static String computeZkLoginAddress(String claimName, String claimValue, String iss, String aud, String userSalt, boolean legacyAddress) {
        BigInteger addressSeed = genAddressSeed(userSalt, claimName, claimValue, aud);
        return computeZkLoginAddressFromSeed(addressSeed, iss, legacyAddress);
    }

    /**
     * 从种子计算 ZkLogin 地址
     *
     * @param addressSeed   地址种子
     * @param iss           颁发者
     * @param legacyAddress 是否使用旧地址格式
     * @return 计算得到的 ZkLogin 地址
     */
    private static String computeZkLoginAddressFromSeed(BigInteger addressSeed, String iss, boolean legacyAddress) {
        byte[] addressSeedBytesBigEndian = legacyAddress ? toBigEndianBytes(addressSeed, 32) : toPaddedBigEndianBytes(addressSeed, 32);
        if ("accounts.google.com".equals(iss)) {
            iss = "https://accounts.google.com";
        }
        byte[] addressParamBytes = iss.getBytes(StandardCharsets.UTF_8);
        byte[] tmp = new byte[2 + addressSeedBytesBigEndian.length + addressParamBytes.length];

        tmp[0] = (byte) ZKLOGIN_FLAG;
        tmp[1] = (byte) addressParamBytes.length;
        System.arraycopy(addressParamBytes, 0, tmp, 2, addressParamBytes.length);
        System.arraycopy(addressSeedBytesBigEndian, 0, tmp, 2 + addressParamBytes.length, addressSeedBytesBigEndian.length);

        return normalizeSuiAddress(bytesToHex(blake2b(tmp, 32)).substring(0, ONECHAIN_ADDRESS_LENGTH * 2));
    }

    /**
     * 生成地址种子
     * 这里只是一个简单示例，需要根据实际情况实现
     *
     * @param userSalt   用户盐值
     * @param claimName  声明名称
     * @param claimValue 声明值
     * @param aud        受众
     * @return 生成的地址种子
     */
    private static BigInteger genAddressSeed(String userSalt, String claimName, String claimValue, String aud) {
        // 这里需要根据实际情况实现地址种子的生成逻辑
        // 暂时返回一个固定值作为示例
        return AddressSeedGenerator.genAddressSeed(new BigInteger(userSalt), claimName, claimValue, aud);
    }

    /**
     * 将 BigInteger 转换为大端字节数组
     *
     * @param value  要转换的 BigInteger
     * @param length 字节数组的长度
     * @return 大端字节数组
     */
    private static byte[] toBigEndianBytes(BigInteger value, int length) {
        byte[] bytes = value.toByteArray();
        if (bytes.length > length) {
            byte[] newBytes = new byte[length];
            System.arraycopy(bytes, bytes.length - length, newBytes, 0, length);
            return newBytes;
        } else if (bytes.length < length) {
            byte[] newBytes = new byte[length];
            System.arraycopy(bytes, 0, newBytes, length - bytes.length, bytes.length);
            return newBytes;
        }
        return bytes;
    }

    /**
     * 将 BigInteger 转换为填充的大端字节数组
     *
     * @param value  要转换的 BigInteger
     * @param length 字节数组的长度
     * @return 填充的大端字节数组
     */
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


    private static byte[] blake2b(byte[] input, int outputLength) {
        Blake2bDigest digest = new Blake2bDigest(outputLength * 8);
        digest.update(input, 0, input.length);
        byte[] output = new byte[outputLength];
        digest.doFinal(output, 0);
        return output;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 规范化 Sui 地址
     *
     * @param address 地址字符串
     * @return 规范化后的地址
     */
    private static String normalizeSuiAddress(String address) {
        return "0x" + address;
    }

    public static void main(String[] args) {
        String claimName = "sub";
        String claimValue = "222";
        String iss = "https://accounts.xone.com";
        String aud = "huione";
        String userSalt = "1234567890";
        String address = computeZkLoginAddress(claimName, claimValue, iss, aud, userSalt, false);
        System.out.println("Computed ZkLogin Address: " + address);

    }
}