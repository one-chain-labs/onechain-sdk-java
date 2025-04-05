package io.onechain.clients;

import io.onechain.models.transactions.TransactionBlockBytes;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Lhb
 * @version v0.0.1 2024-12-19 17:09:56
 */
public interface TransactionClient {

    /**
     * 将 OneChain 币发送到地址列表
     *
     * @param signer     交易签名者的 Sui 地址
     * @param inputCoins 本次交易中要使用的 Sui 币，包括用于支付 Gas 的币
     * @param recipients 收件人的地址，此向量的长度必须与金额相同
     * @param amounts    按照顺序向收款人转账的金额 单位是 MIST
     * @param gasBudget  gas 预算，如果 gas 成本超过预算，交易将失败 单位是 MIST
     * @return 交易的二进制数据
     */
    CompletableFuture<TransactionBlockBytes> unsafePayOneChain(String signer, List<String> inputCoins,
                                                               List<String> recipients, List<String> amounts,
                                                               String gasBudget);

    /**
     * 将其他代币发送到地址列表
     *
     * @param signer      交易签名者的 Sui 地址
     * @param inputCoins  本次交易中要使用的 Sui 币，包括用于支付 Gas 的币
     * @param recipients  收件人的地址，此向量的长度必须与金额相同
     * @param amounts     按照顺序向收款人转账的金额 单位是 MIST
     * @param gasObjectId 用于支付gas费的对象
     * @param gasBudget   gas 预算，如果 gas 成本超过预算，交易将失败 单位是 MIST
     * @return 交易的二进制数据
     */
    CompletableFuture<TransactionBlockBytes> unsafePay(String signer, List<String> inputCoins,
                                                       List<String> recipients, List<String> amounts,
                                                       String gasObjectId, String gasBudget);
}
