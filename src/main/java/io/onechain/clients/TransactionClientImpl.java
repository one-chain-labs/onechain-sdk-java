package io.onechain.clients;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import io.onechain.jsonrpc.JsonRpc20Request;
import io.onechain.jsonrpc.JsonRpcClientProvider;
import io.onechain.models.transactions.TransactionBlockBytes;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Lhb
 * @version v0.0.1 2024-12-19 17:22:31
 */
public class TransactionClientImpl implements TransactionClient {

    private final JsonRpcClientProvider jsonRpcClientProvider;

    public TransactionClientImpl(JsonRpcClientProvider jsonRpcClientProvider) {
        this.jsonRpcClientProvider = jsonRpcClientProvider;
    }

    @Override
    public CompletableFuture<TransactionBlockBytes> unsafePayOneChain(String signer, List<String> inputCoins,
                                                                      List<String> recipients, List<String> amounts,
                                                                      String gasBudget) {
        JsonRpc20Request request = jsonRpcClientProvider.createJsonRpc20Request("unsafe_paySui",
                                                                                Lists.newArrayList(signer, inputCoins,
                                                                                                   recipients, amounts, gasBudget));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/unsafe_paySui", request, new TypeToken<TransactionBlockBytes>() {
                }.getType());
    }

    @Override
    public CompletableFuture<TransactionBlockBytes> unsafePay(String signer, List<String> inputCoins,
                                                              List<String> recipients, List<String> amounts,
                                                              String gasObjectId, String gasBudget) {
        JsonRpc20Request request = jsonRpcClientProvider.createJsonRpc20Request("unsafe_pay",
                                                                                Lists.newArrayList(signer, inputCoins,
                                                                                                   recipients, amounts,
                                                                                                   gasObjectId, gasBudget));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/unsafe_pay", request, new TypeToken<TransactionBlockBytes>() {
                }.getType());
    }
}
