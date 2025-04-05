package io.onechain;

import com.google.common.collect.Lists;
import io.onechain.bcsgen.MoveCallParam;
import io.onechain.bcsgen.TransactionData;
import io.onechain.bcsgen.ZkLoginSignature;
import io.onechain.crypto.OneChainKeyGen;
import io.onechain.crypto.OneChainKeyPair;
import io.onechain.crypto.SignatureScheme;
import io.onechain.jwk.rsa.JwkWithRSA;
import io.onechain.models.ZKLoginDataResponse;
import io.onechain.models.coin.Balance;
import io.onechain.models.coin.Coin;
import io.onechain.models.objects.ZKLoginData;
import io.onechain.models.objects.ZkServiceReqeust;
import io.onechain.models.transactions.*;
import io.onechain.zklogin.AddressSeedGenerator;
import io.onechain.zklogin.NonceGenerator;
import io.onechain.zklogin.ZkLoginAddressUtil;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.Duration;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


/**
 * @author chiyu
 * @date 2024/12/2 15:01
 */
public class OneChainTest {

    private static final String DEFAULT_COIN_TYPE = "0x2::oct::OCT";

    private static final Long GAS_BUDGET = 100000000L;
    private static final String UN_KNOW_ADDRESS = "0x95cd84ea38d1ff518832bb6b47f151fa8f9833acfd106cfce14eca33f36ed213";


    private static final OkHttpClient httpClient =
            new OkHttpClient()
            .newBuilder()
            .pingInterval(Duration.ofSeconds(15))
            .writeTimeout(Duration.ofSeconds(15))
            .readTimeout(Duration.ofSeconds(15))
            .build();
    private OneChain oneChain = OneChain.testNet();



    @SneakyThrows
    @Test
    public void requestOneChainFromFaucet() {
        OneChainKeyPair<?> oneChainKeyPair = OneChainKeyGen.getInstance().generateNewKeyPair(SignatureScheme.ED25519);
        oneChain.requestOneChainFromFaucet(oneChainKeyPair.address()).get();
        Balance balance = oneChain.getBalance(oneChainKeyPair.address(), null).get();
        assert balance.getTotalBalance().compareTo(BigInteger.ZERO) > 0;
    }

    @SneakyThrows
    @Test
    public void queryResolveNameServiceAddress() {
        String s = oneChain.resolveNameServiceAddress("aaa.oct").get();
        System.out.println(s);
    }



    @Test
    @SneakyThrows
    public void testTransfer(){
        OneChainKeyPair<?> tempKeypair = OneChainKeyGen.getInstance().generateNewKeyPair(SignatureScheme.ED25519);
        Balance balance = oneChain.getBalance(tempKeypair.address(), null).get();
        if (balance.getTotalBalance().compareTo(BigInteger.ZERO) == 0){
            oneChain.requestOneChainFromFaucet(tempKeypair.address()).get();
            oneChain.requestOneChainFromFaucet(tempKeypair.address()).get();
        }
        TransactionData transactionData = buildTransferData(tempKeypair.address());
        String signed = OneChain.signTransactionBlock(tempKeypair.encodePrivateKey(), transactionData);
        TransactionBlockResponseOptions transactionBlockResponseOptions =
                new TransactionBlockResponseOptions();
        transactionBlockResponseOptions.setShowEffects(true);
        transactionBlockResponseOptions.setShowEvents(true);
        transactionBlockResponseOptions.setShowInput(false);
        transactionBlockResponseOptions.setShowObjectChanges(false);
        TransactionBlockResponse transactionBlockResponse = oneChain.executeTransaction(
                transactionData,
                Lists.newArrayList(signed),
                transactionBlockResponseOptions,
                ExecuteTransactionRequestType.WaitForLocalExecution).get();
        assert Objects.isNull(transactionBlockResponse.getErrors());
    }



    @Test
    @SneakyThrows
    public void testZkLoginTransfer() {
        String claimName = "sub";
        String claimValue = "333";
        String iss = "https://test.huionepay.com";
        String aud = "huione";
        String userSalt = "1234567890";
        String address = ZkLoginAddressUtil.computeZkLoginAddress(claimName, claimValue, iss, aud, userSalt, false);
        Balance balance = oneChain.getBalance(address, null).get();
        if (balance.getTotalBalance().compareTo(BigInteger.ZERO) == 0){
            oneChain.requestOneChainFromFaucet(address).get();
            oneChain.requestOneChainFromFaucet(address).get();
        }
        BigInteger maxEpoch = BigInteger.TEN;
        OneChainKeyPair<?> tempKeypair = OneChainKeyGen.getInstance().generateNewKeyPair(SignatureScheme.ED25519);
        BigInteger randomness = NonceGenerator.generateRandomness();
        String nonce = NonceGenerator.generateNonce(tempKeypair.publicKeyBytes(), maxEpoch, randomness);
        String jwt = getJwt(claimValue, nonce);
        BigInteger addressSeed = AddressSeedGenerator.genAddressSeed(new BigInteger(userSalt), claimName, claimValue, aud);
        ZKLoginData zkLoginData = requestZkService(maxEpoch, randomness.toString(), tempKeypair.publicKey(), jwt, userSalt);
        TransactionData transactionData = buildTransferData(address);
        String signed = OneChain.signTransactionBlock(tempKeypair.encodePrivateKey(), transactionData);
        ZkLoginSignature zkLoginSignature = ZkLoginSignature.createZkLoginSignature(zkLoginData, Base64.getDecoder().decode(signed), maxEpoch.longValue(), addressSeed.toString());
        String zkLoginSignatureStr = Base64.getEncoder().encodeToString(zkLoginSignature.bcsSerialize());
        TransactionBlockResponseOptions transactionBlockResponseOptions =
                new TransactionBlockResponseOptions();
        transactionBlockResponseOptions.setShowEffects(true);
        transactionBlockResponseOptions.setShowEvents(true);
        transactionBlockResponseOptions.setShowInput(false);
        transactionBlockResponseOptions.setShowObjectChanges(false);
        TransactionBlockResponse transactionBlockResponse = oneChain.executeTransaction(
                transactionData,
                Lists.newArrayList(zkLoginSignatureStr),
                transactionBlockResponseOptions,
                ExecuteTransactionRequestType.WaitForLocalExecution).get();
        System.out.println(transactionBlockResponse);
    }

    @SneakyThrows
    private TransactionData buildTransferData(String address) {
        Coin coin = oneChain.getCoins(address, DEFAULT_COIN_TYPE, null, 100).get().getData().get(0);
        final TypeTag.StructType structType =
                new TypeTag.StructType();
        String[] split = DEFAULT_COIN_TYPE.split("::");
        StructTag structTag = new StructTag();
        structTag.setAddress(split[0]);
        structTag.setModule(split[1]);
        structTag.setName(split[2]);
        structType.setStructTag(structTag);
        MoveCallParam moveCallParam = new MoveCallParam();
        moveCallParam.setArguments(Lists.newArrayList(coin.getCoinObjectId(), 1L,
                UN_KNOW_ADDRESS));
        moveCallParam.setModule("pay");
        moveCallParam.setFunction("split_and_transfer");
        moveCallParam.setTypeArguments(Lists.newArrayList(structType));
        moveCallParam.setPackageObjectId("0x2");
        TransactionData transactionData = oneChain.multipleMoveCall(
                address,
                Lists.newArrayList(moveCallParam), null, GAS_BUDGET, null, null);
        return transactionData;
    }

    @SneakyThrows
    private ZKLoginData requestZkService(BigInteger maxEpoch, String jwtRandomness, String extendedEphemeralPublicKey, String jwt, String salt) {
        ZkServiceReqeust zkServiceReqeust = ZkServiceReqeust.builder()
                .jwt(jwt)
                .jwtRandomness(jwtRandomness)
                .extendedEphemeralPublicKey(extendedEphemeralPublicKey)
                .maxEpoch(maxEpoch)
                .salt(salt)
                .build();
        ZKLoginDataResponse zkLoginDataResponse = oneChain.requestOneChainZkService(zkServiceReqeust).get();
        if (StringUtils.isNotBlank(zkLoginDataResponse.getError())){
            System.out.println(zkLoginDataResponse.getError());
            throw new RuntimeException();
        }
        return zkLoginDataResponse.getZkLoginData();
    }

    private String getJwt(String sub, String nonce) {
//        JwkWithRSA jwkWithRSA = JwkWithRSA.devnetJwk();
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("iss", "https://test.huionepay.com");
        payload.put("azp", "111");
        payload.put("aud", "huione");
        payload.put("sub", sub);
        payload.put("nonce", nonce);
        payload.put("nbf", 2732533738L);
        payload.put("iat", 2732533738L);
        payload.put("exp", 2732533738L);
        payload.put("jti", "98467d698723826f74a502bcdbc278caf64ed3e0");
        //调用jwt接口
        return "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjEifQ" +
                ".eyJpc3MiOiJodHRwczovL3Rlc3QuaHVpb25lcGF5LmNvbSIsImF6cCI6IjExMSIsImF1ZCI6Imh1aW9uZSIsInN1YiI6IjMzMyIsIm5vbmNlIjoiZGNnMXVlaU42TXUtQy0wMl9sQ3o4QVp4OWNRIiwibmJmIjoyNzMyNTMzNzM4LCJpYXQiOjI3MzI1MzM3MzgsImV4cCI6MjczMjUzMzczOCwianRpIjoiOTg0NjdkNjk4NzIzODI2Zjc0YTUwMmJjZGJjMjc4Y2FmNjRlZDNlMCJ9" +
                ".LEWR_GMPkf4T2kOMHgUrKSVsL7jaFRJ4ULYEABvMnW7hSz5rN7qsACIsW9Y5ulhnN5Ub1pBx6Zcu0uCbKbfWDe-1VozVAxguRGlt0QYyPHHEdUW_4jDHwlkUJ67JyWg-p4WvY_p-LaXn3k_w9h1oh44befkV2yS7lGgGi_gC-c2lhmolh4PqEnbo1eLt-2cu89Kfi5ZfcY8HNbL45hp1_dYdw54hEeTRylnp5o44xXEH0FIwteI9EwO9owQxvAm4ELSSeeGI-EJTGEM1-SG_A-6Om2i8Ma1L3_o2z2RQD5m9Af-m_Y6136ONOXazKIM04Jy6lsmzFfO8-__PwxpNSw";
    }



}
