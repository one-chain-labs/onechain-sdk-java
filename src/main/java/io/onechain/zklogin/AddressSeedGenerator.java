package io.onechain.zklogin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddressSeedGenerator {

    private static final int MAX_KEY_CLAIM_NAME_LENGTH = 32;
    private static final int MAX_KEY_CLAIM_VALUE_LENGTH = 115;
    private static final int MAX_AUD_VALUE_LENGTH = 145;

    private static final int PACK_WIDTH = 248;

    /**
     * 计算地址种子
     *
     * @param salt 盐值，可以是字符串或 BigInteger 类型
     * @param name 名称
     * @param sub 值
     * @param aud 主题
     * @return 计算得到的地址种子
     */
    public static BigInteger genAddressSeed(
            BigInteger salt,
            String name,
            String sub,
            String aud
    ) {
        // 将盐值转换为 BigInteger 类型
        // 计算哈希值
        BigInteger nameHash = hashASCIIStrToField(name, MAX_KEY_CLAIM_NAME_LENGTH);
        BigInteger subHash = hashASCIIStrToField(sub, MAX_KEY_CLAIM_VALUE_LENGTH);
        BigInteger audHash = hashASCIIStrToField(aud, MAX_AUD_VALUE_LENGTH);
        BigInteger saltHash = poseidonHash(salt);
        // 计算最终的地址种子
        return poseidonHash(nameHash, subHash, audHash, saltHash);
    }

    /**
     * 计算字符串的哈希值
     *
     * @param str 输入的字符串
     * @param maxSize 最大长度
     * @return 哈希值
     */
    public static BigInteger hashASCIIStrToField(String str, int maxSize) {
        // 检查字符串长度是否超过最大长度
        if (str.length() > maxSize) {
            throw new IllegalArgumentException("String " + str + " is longer than " + maxSize + " chars");
        }

        // 填充字符串
        String strPadded = padString(str, maxSize);

        // 将字符串转换为字节数组
        byte[] strBytes = strPadded.getBytes();

        // 分割字节数组
        List<BigInteger> packed = chunkArray(strBytes, PACK_WIDTH / 8);

        // 使用 Poseidon 哈希函数进行哈希

        // 调用 poseidonHash 方法进行哈希计算
        return poseidonHash(packed.toArray(new BigInteger[0]));
    }

    private static String padString(String str, int maxSize) {
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() < maxSize) {
            sb.append((char) 0);
        }
        return sb.toString();
    }

    public static Byte[] convert(byte[] bytes) {
        Byte[] byteObjects = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byteObjects[i] = bytes[i];
        }
        return byteObjects;
    }

    public static byte[] listToByteArray(List<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            byteArray[i] = byteList.get(i);
        }
        return byteArray;
    }
    public static List<BigInteger> chunkArray(byte[] array, int chunkSize) {
        // 计算需要的子数组数量
        int chunkCount = (int) Math.ceil((double) array.length / chunkSize);
        List<BigInteger> chunks = new ArrayList<>(chunkCount);

        // 反转原数组
        List<Byte> revArray = new ArrayList<>(Arrays.asList(convert(array)));
        Collections.reverse(revArray);

        // 分割成子数组
        for (int i = 0; i < chunkCount; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, revArray.size());
            List<Byte> subList = new ArrayList<>(revArray.subList(start, end));
            // 反转当前子数组
            Collections.reverse(subList);
            byte[] bytes = listToByteArray(subList);
            chunks.add(new BigInteger(bytes));
        }

        // 反转最终结果列表
        Collections.reverse(chunks);
        return chunks;
    }



    /**
     * 计算 Poseidon 哈希值
     *
     * @param values 输入的 BigInteger 数组
     * @return 哈希值
     */
    private static BigInteger poseidonHash(BigInteger... values) {
        // 这里需要根据实际的 Poseidon 哈希算法实现，示例中简单返回所有值的异或结果
        return Poseidon.poseidon(values);
    }

    public static void main(String[] args) {
        BigInteger salt = new BigInteger("1234567890");
        String name = "sub";
        String sub = "222";
        String aud = "huione";

//        hash-sub:9102752833182448263444250585012134730074321235810986230287216596098480554553
//        hash-value:17968626922390829186448845863604205916096666100531192449311710960415442332302
//        hash-aud:19255327929347185065531463831505424124095991053735311073229939210071157975023
        BigInteger addressSeed = genAddressSeed(
                salt,
                name,
                sub,
                aud
        );

        System.out.println("Address Seed: " + addressSeed);
        System.out.println("Address Seed: " + "10667689953490817095173118570490819441160398579063499265468349684594952000213");
    }
}
