package io.onechain;

import com.google.common.collect.Lists;
import io.onechain.bcsgen.MoveCallParam;
import io.onechain.bcsgen.TransactionData;
import io.onechain.bcsgen.ZkLoginSignature;
import io.onechain.clients.OkhttpHuioneWalletClient;
import io.onechain.crypto.OneChainKeyGen;
import io.onechain.crypto.OneChainKeyPair;
import io.onechain.crypto.SignatureScheme;
import io.onechain.hchain.controller.DIDApi;
import io.onechain.hchain.controller.TransferApi;
import io.onechain.hchain.controller.WalletApi;
import io.onechain.hchain.domain.req.common.HeaderRequest;
import io.onechain.hchain.domain.req.did.*;
import io.onechain.hchain.domain.req.transfer.BuildSponsorTransactionRequest;
import io.onechain.hchain.domain.req.transfer.ProxyPayTxRequest;
import io.onechain.hchain.domain.req.transfer.TransferOrderReq;
import io.onechain.hchain.domain.req.transfer.TransferOrderTxReq;
import io.onechain.hchain.domain.req.wallet.QueryCurrencyReq;
import io.onechain.hchain.domain.resp.common.CommonResp;
import io.onechain.hchain.domain.resp.common.CreateOrderResp;
import io.onechain.hchain.domain.resp.did.*;
import io.onechain.hchain.domain.resp.transfer.GasTxBuilderResponse;
import io.onechain.hchain.domain.resp.transfer.ProxyPayTxResp;
import io.onechain.hchain.domain.resp.transfer.TransferOrderTxResp;
import io.onechain.hchain.domain.resp.wallet.CurrencyChainResp;
import io.onechain.models.ZKLoginDataResponse;
import io.onechain.models.coin.Balance;
import io.onechain.models.coin.Coin;
import io.onechain.models.objects.ZKLoginData;
import io.onechain.models.transactions.StructTag;
import io.onechain.models.transactions.TypeTag;
import io.onechain.utils.RsaSignUtil;
import io.onechain.zklogin.AddressSeedGenerator;
import io.onechain.zklogin.NonceGenerator;
import io.onechain.zklogin.ZkLoginAddressUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Base64;
import java.util.List;


/**
 * @author chiyu
 * @date 2024/12/2 15:01
 */
public class HuionePayTest {


    private static OneChain oneChain = OneChain.testNet();

    private static final String DEFAULT_COIN_TYPE = "0x2::oct::OCT";

    private static final String UN_KNOW_ADDRESS = "0x95cd84ea38d1ff518832bb6b47f151fa8f9833acfd106cfce14eca33f36ed213";



    static String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr43KS8cko41MYEyDAlwqm3t9JRmBtTQQnm7l+RzrBCvPODRmpZGNhpO2MUgVFYdWkHlt/zTEGAqkhDUXkkwpeHebB9zWhTbhDGEdohW5T82MtdihNGgemoeNpC/eTt46o/5nqHzbe84CNhefEQdVMmYJcnX2Ma/g5VzFXOjOM7/ThE02L4TIMAjsFhapXRMcxZ4i0D2Xn0HVtl2uEURdXdQHnoAKjoGHukV4S/olMw8B6u2N0TpjJt9ORKCvIBYvsXgyVVcUzMLmUDIiS+RbhqZ60R9bTDeYSzm8ej/WgRM0ap6U89DDUvtEN1atb00rKqW+aU/ob0FU83Q2LeLq9QIDAQAB";
    static String REQUEST_PRIKEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCvjcpLxySjjUxgTIMCXCqbe30lGYG1NBCebuX5HOsEK884NGalkY2Gk7YxSBUVh1aQeW3/NMQYCqSENReSTCl4d5sH3NaFNuEMYR2iFblPzYy12KE0aB6ah42kL95O3jqj/meofNt7zgI2F58RB1UyZglydfYxr+DlXMVc6M4zv9OETTYvhMgwCOwWFqldExzFniLQPZefQdW2Xa4RRF1d1AeegAqOgYe6RXhL+iUzDwHq7Y3ROmMm305EoK8gFi+xeDJVVxTMwuZQMiJL5FuGpnrRH1tMN5hLObx6P9aBEzRqnpTz0MNS+0Q3Vq1vTSsqpb5pT+hvQVTzdDYt4ur1AgMBAAECggEAAgw0WEo3pZ3evFX12KsO1L27kvTHWdIo7uS6QSBSy7uEOkBRE+fjuNshpZ5eDSmFG2TfM3D/+kKrO7pmzrLCJ1xIEspnpHL/2dz4s18mWqDxAoMif1+QGq2dO8MuCDbmg+rkdlmmeuGisveuI3FsmIycbHSlyKSVifdZMfyqUxB3ysLl4SQLxoZ2x4NL9e/Jj8NlKKgLZeXgqp4/ojh3IUGwHIYnz2PVm+K55wbq55E61p3yyd+09kIOajqLx+d6CsfNY9MhOXdl9W7vZEGKeQ1HuCQ9muwEAmO32yELQC39t4Q7GuPD+0shTMmDs6QsUXzZC/XfqBd3xPBlwkvIgQKBgQDXiUNMfctRYQuFkLR3Ux+rpAxCOxLLdGjoSyVJaZzeAci5U28R4CbVeVV1HeRmy+x2kwe1YD/7x6qCxQUlRnDYATcZJf09YrrrZFXPQilCTqi2RdWy1Zq0M9sEhFWsJL4QF0fF/puXwXVbRB/uVMbH/jyT5wFNxbmmNxWixtK41QKBgQDQgvbxxDdc+WSWnAj0uTsiDloewmeueh/IdnGTPSx5qfF931VeWl4waOqhI8N6sDEYhvMa8+XjDdJZ08YdPh9bPQIhNCcEbL2u9SEt2VZ7nx/oVPQCyBIHsXaOoPtPH68qnTlSPhDajZALhPQVQwpxizmTfVuyi/hZG1OsYgB5oQKBgQCGva6uwO074JkdVIsdFX/1A0cOmHN1cT6sCV4z+KwyNZdQFBKZcDGWvpVn89n3UYBv2Ba3koYtVnMH8Tb4SIL+5jOVqyQXHgOQaFckjE3Sv+3ElP+1Hsfp44kF19zfEtEmqgcahcKrKiu9dGcpzSG/oPYp1/3+qp8Wg9Uov3a4SQKBgDT17s89rWo6FiiC/WtbWP+vcYh6jGcusb/zBaoGUbOdTK9R+Jb8kQvuuhmvwcj5056NOFZSOMPREOqr9Zgb3U8JUe8pFffzvsIflQvWNjc0FaCnY0sJkjrOAnT7wpk4TP+f651OEm3QoxOp820rGA369ObXYmEZWD0ZycjxI3nBAoGANwAhsbfdNuzl6wHHzjRu4kmZJhOSnK1/aHlBpXc/ynBR+BVBghHseKm50azOp6Tx3D19zgViaXGWiH/x3wr2qab5Jy33njx3VUu1r0lug9PoOfzhZ3HqfMT7hAqnuZDn4Ey/t4fzIn38o9yPd8tvZkLnMqZPGR6bE4kLYfforAs=";

    private static OneChainKeyPair<?> TEMP_KEYPAIR;

    private static String ZK_LOGIN_ADDRESS;

    private static String DID;

    private static BigInteger RANDOMNESS;


    private static AuthorizeTokenProfileResponse TOKEN_PROFILE;

    private static ZKLoginData ZK_LOGIN_DATA;


    private static BigInteger MAX_EPOCH;

    private static OkhttpHuioneWalletClient huioneWalletClient;

    private static DIDApi didApi;

    private static String MOBILE = "182828666";

    private static TransferApi transferApi;

    private static WalletApi walletApi;




    static {
        huioneWalletClient = new OkhttpHuioneWalletClient("http://10.71.9.46:8080");
        didApi = huioneWalletClient;
        transferApi = huioneWalletClient;
        walletApi = huioneWalletClient;
    }


    @BeforeAll
    @SneakyThrows
    public static void login(){
        SmsCodeSendReq step1Req = new SmsCodeSendReq();
        step1Req.setMobile(MOBILE);
        step1Req.setMobilePrefix("855");
        step1Req.setTimestamp(System.currentTimeMillis());
        step1Req.setMerchantId("1000000");
        step1Req.setMerchantSign(RsaSignUtil.sign(step1Req,REQUEST_PRIKEY));
        CommonResp<String> step1Resp = didApi.sendCode(step1Req).get();
        assert step1Resp.isSuccess();
        //step2
        SmsAuthenticateRequest step2Req = new SmsAuthenticateRequest();
        step2Req.setMobile(MOBILE);
        step2Req.setMobilePrefix("855");
        step2Req.setSmsCode("000000");
        step2Req.setCode(step1Resp.getData());
        CommonResp<AuthenticateUserResponse> step2Resp = didApi.sms(step2Req).get();
        assert step2Resp.isSuccess();


        //step3
        AuthorizeTokenProfileRequest step3Req = new AuthorizeTokenProfileRequest();
        MAX_EPOCH = oneChain.getLatestOneChainSystemState().get().getEpoch().add(BigInteger.TEN);
        TEMP_KEYPAIR = OneChainKeyGen.getInstance().generateNewKeyPair(SignatureScheme.ED25519);
        RANDOMNESS = NonceGenerator.generateRandomness();
        String nonce = NonceGenerator.generateNonce(TEMP_KEYPAIR.publicKeyBytes(), MAX_EPOCH, RANDOMNESS);
        step3Req.setCode(step2Resp.getData().getCode());
        step3Req.setNonce(nonce);
        CommonResp<AuthorizeTokenProfileResponse> step3Resp = didApi.getToken(step3Req).get();
        TOKEN_PROFILE = step3Resp.getData();
        assert step3Resp.isSuccess();

        //step4
        AuthorizeTokenProfileResponse.AccessTokenProfile jwtToken = TOKEN_PROFILE.getAccessTokenProfile();
        ZK_LOGIN_ADDRESS = ZkLoginAddressUtil.computeZkLoginAddress("sub", jwtToken.getSub(), jwtToken.getIss(), jwtToken.getAud(), TOKEN_PROFILE.getSalt(), false);
        HeaderRequest headerRequest = new HeaderRequest();
        headerRequest.setAccessToken(TOKEN_PROFILE.getAccessToken());
        headerRequest.setTokenId(jwtToken.getJti());
        huioneWalletClient.fillHeader(headerRequest);
        CommonResp<UserTokenProfile> userTokenProfileCommonResp = didApi.getTokenUserProfile().get();
        assert userTokenProfileCommonResp.isSuccess();

        //step5
        String jwt = TOKEN_PROFILE.getJwtToken();
        ZK_LOGIN_DATA = requestZkService(MAX_EPOCH, RANDOMNESS.toString(), TEMP_KEYPAIR.publicKey(), jwt, TOKEN_PROFILE.getSalt());
        DID = userTokenProfileCommonResp.getData().getDid();
        requestOneChainFromFaucet();
    }



    @SneakyThrows
    @Test
    public void testQueryChainCurrencyForList(){
        CommonResp<List<CurrencyChainResp>> commonResp = walletApi.queryChainCurrencyForList(QueryCurrencyReq.builder().build()).get();
        assert commonResp.isSuccess();
    }

    @SneakyThrows
    @Test
    public void testTransfer() {
        TransferOrderReq transferOrderReq = new TransferOrderReq();
        transferOrderReq.setRemark("test");
        transferOrderReq.setCurrency("USDH");
        transferOrderReq.setAmount(BigDecimal.valueOf(0.5));
        transferOrderReq.setFromAddress(ZK_LOGIN_ADDRESS);
        transferOrderReq.setToAddress(UN_KNOW_ADDRESS);
        CommonResp<CreateOrderResp> createOrderRespCommonResp = transferApi.createOrder(transferOrderReq).get();
        assert createOrderRespCommonResp.isSuccess();
        CreateOrderResp order = createOrderRespCommonResp.getData();
        BigInteger addressSeed = AddressSeedGenerator.genAddressSeed(new BigInteger(TOKEN_PROFILE.getSalt()), "sub", TOKEN_PROFILE.getAccessTokenProfile().getSub(), TOKEN_PROFILE.getAccessTokenProfile().getAud());
        String signed = OneChain.signTransactionBlockByte(TEMP_KEYPAIR.encodePrivateKey(), Base64.getDecoder().decode(order.getRawTransaction()));
        ZkLoginSignature zkLoginSignature = ZkLoginSignature.createZkLoginSignature(ZK_LOGIN_DATA, Base64.getDecoder().decode(signed), MAX_EPOCH.longValue(), addressSeed.toString());
        String zkLoginSignatureStr = Base64.getEncoder().encodeToString(zkLoginSignature.bcsSerialize());
        TransferOrderTxReq transferOrderTxReq = new TransferOrderTxReq();
        transferOrderTxReq.setHash(order.getHash());
        transferOrderTxReq.setUserSig(zkLoginSignatureStr);
        transferOrderTxReq.setTxBytes(order.getRawTransaction());
        CommonResp<TransferOrderTxResp> transferOrderTxRespCommonResp = transferApi.sendTx(transferOrderTxReq).get();
        assert transferOrderTxRespCommonResp.isSuccess();
    }

    @SneakyThrows
    @Test
    public void testSponsorTransaction() {
        BuildSponsorTransactionRequest buildSponsorTransactionRequest = new BuildSponsorTransactionRequest();
        buildSponsorTransactionRequest.setRawTransaction(buildTransferData(ZK_LOGIN_ADDRESS));
        buildSponsorTransactionRequest.setAddress(ZK_LOGIN_ADDRESS);
        CommonResp<GasTxBuilderResponse> gasTxBuilderResponseCommonResp = transferApi.buildSponsorTransaction(buildSponsorTransactionRequest).get();
        assert gasTxBuilderResponseCommonResp.isSuccess();
        GasTxBuilderResponse data = gasTxBuilderResponseCommonResp.getData();
        BigInteger addressSeed = AddressSeedGenerator.genAddressSeed(new BigInteger(TOKEN_PROFILE.getSalt()), "sub", TOKEN_PROFILE.getAccessTokenProfile().getSub(), TOKEN_PROFILE.getAccessTokenProfile().getAud());
        String signed = OneChain.signTransactionBlockByte(TEMP_KEYPAIR.encodePrivateKey(), Base64.getDecoder().decode(data.getRawTransaction()));
        ZkLoginSignature zkLoginSignature = ZkLoginSignature.createZkLoginSignature(ZK_LOGIN_DATA, Base64.getDecoder().decode(signed), MAX_EPOCH.longValue(), addressSeed.toString());
        String zkLoginSignatureStr = Base64.getEncoder().encodeToString(zkLoginSignature.bcsSerialize());
        ProxyPayTxRequest proxyPayTxRequest = new ProxyPayTxRequest();
        proxyPayTxRequest.setReservationId(data.getReservationId());
        proxyPayTxRequest.setUserSig(zkLoginSignatureStr);
        proxyPayTxRequest.setTxBytes(data.getRawTransaction());
        CommonResp<ProxyPayTxResp> proxyPayTxRespCommonResp = transferApi.doProxyPayTx(proxyPayTxRequest).get();
        assert proxyPayTxRespCommonResp.isSuccess();
        System.out.println(proxyPayTxRespCommonResp);
    }

    @SneakyThrows
    private String buildTransferData(String address) {
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
        TransactionData transactionData = oneChain.multipleMoveCallForSponsorTransaction(
                address,
                Lists.newArrayList(moveCallParam), 100000000L,null,null);
        return Base64.getEncoder().encodeToString(transactionData.bcsSerialize());
    }


    @SneakyThrows
    public static void requestOneChainFromFaucet() {
        Balance balance = oneChain.getBalance(ZK_LOGIN_ADDRESS, DEFAULT_COIN_TYPE).get();
        if (balance.getTotalBalance().compareTo(BigInteger.ZERO) > 0){
            return;
        };
        oneChain.requestOneChainFromFaucet(ZK_LOGIN_ADDRESS).get();
    }

    @SneakyThrows
    private static ZKLoginData requestZkService(BigInteger maxEpoch, String jwtRandomness, String extendedEphemeralPublicKey, String jwt, String salt) {
        ZkProofsReq zkServiceReqeust = ZkProofsReq.builder()
                .jwt(jwt)
                .jwtRandomness(jwtRandomness)
                .extendedEphemeralPublicKey(extendedEphemeralPublicKey)
                .maxEpoch(maxEpoch.longValue())
                .salt(salt)
                .keyClaimName("sub")
                .build();
        CommonResp<ZKLoginData> zkLoginDataResponseCommonResp = huioneWalletClient.getZkProofs(zkServiceReqeust).get();
        assert zkLoginDataResponseCommonResp.isSuccess();
        return zkLoginDataResponseCommonResp.getData();
    }




}
