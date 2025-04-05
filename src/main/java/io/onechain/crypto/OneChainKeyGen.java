package io.onechain.crypto;

/**
 * @author chiyu
 * @date 2023/7/18 14:58
 */
public class OneChainKeyGen extends AbstractKeyStore{

    public final static OneChainKeyGen INSTANCE = new OneChainKeyGen();

    private OneChainKeyGen(){

    }

    public static OneChainKeyGen getInstance(){
        return INSTANCE;
    }
    @Override
    public void addKey(String address, OneChainKeyPair<?> keyPair) {
        keys.put(address,keyPair);
    }





}
