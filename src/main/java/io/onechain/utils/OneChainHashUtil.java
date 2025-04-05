package io.onechain.utils;

import org.bitcoinj.core.Base58;
import org.bouncycastle.jcajce.provider.digest.Blake2b;

/**
 * @author chiyu
 * @date 2023/7/11 14:21
 */
public class OneChainHashUtil {


    public static String transactionDataHash(byte[] txBytes) {
        return Base58.encode(hashTypedData("TransactionData", txBytes));
    }

    private static byte[] hashTypedData(String typeTag, byte[] data) {
        byte[] typeTagBytes = String.format("%s::", typeTag).getBytes();
        byte[] dataWithTag = new byte[typeTagBytes.length + data.length];
        System.arraycopy(typeTagBytes, 0, dataWithTag, 0, typeTagBytes.length);
        System.arraycopy(data, 0, dataWithTag, typeTagBytes.length, data.length);
        final Blake2b.Blake2b256 blake2b256 = new Blake2b.Blake2b256();
        return blake2b256.digest(dataWithTag);
    }





}
