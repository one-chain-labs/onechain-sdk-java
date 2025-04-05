/*
 * Copyright 2022-2023 281165273grape@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.onechain;


import com.google.common.collect.Lists;
import com.novi.serde.SerializationError;
import io.onechain.models.ZKLoginDataResponse;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.onechain.bcsgen.Argument;
import io.onechain.bcsgen.*;
import io.onechain.bcsgen.OneChainAddress.Builder;
import io.onechain.clients.TransactionBlock;
import io.onechain.clients.*;
import io.onechain.crypto.*;
import io.onechain.json.GsonJsonHandler;
import io.onechain.json.JsonHandler;
import io.onechain.jsonrpc.JsonRpcClientProvider;
import io.onechain.jsonrpc.OkHttpJsonRpcClientProvider;
import io.onechain.models.FaucetResponse;
import io.onechain.models.OneChainApiException;
import io.onechain.models.coin.Balance;
import io.onechain.models.coin.CoinMetadata;
import io.onechain.models.coin.CoinSupply;
import io.onechain.models.coin.PaginatedCoins;
import io.onechain.models.events.EventFilter;
import io.onechain.models.events.EventId;
import io.onechain.models.events.PaginatedEvents;
import io.onechain.models.events.OneChainEvent;
import io.onechain.models.governance.DelegatedStake;
import io.onechain.models.governance.OneChainCommitteeInfo;
import io.onechain.models.governance.SystemStateSummary;
import io.onechain.models.governance.ValidatorsApy;
import io.onechain.models.objects.*;
import io.onechain.models.transactions.TypeTag;
import io.onechain.models.transactions.*;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jcajce.provider.digest.Blake2b.Blake2b256;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;

import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

/**
 * The type Sui.
 *
 * @author chiyu
 * @since 2022.11
 */
public class OneChain {

    private final KeyStore keyStore;

    private final QueryClient queryClient;

    public final ExecutionClient executionClient;

    private final SubscribeClient subscribeClient;

    private final FaucetClient faucetClient;

    private final TransactionClient transactionClient;

    private final ZkServiceClient zkServiceClient;


    public static OneChain testNet() {
        return new OneChain("https://rpc-devnet.onelabs.cc:443",
                "https://faucet-devnet.onelabs.cc",
                "https://prover-devnet.tpknp.com",
                Paths.get("src", "integrationTest", "sui.keystore").toAbsolutePath().toString());
    }

    public static OneChain mainNet() {
        return new OneChain("https://onechain-rpc-mainnet.huione.org",
                null,
                "https://prover-mainnet.huione.org",
                null);
    }


    public OneChain(String fullNodeEndpoint) {
        this.keyStore = null;
        final JsonHandler jsonHandler = new GsonJsonHandler();
        final JsonRpcClientProvider jsonRpcClientProvider =
                new OkHttpJsonRpcClientProvider(fullNodeEndpoint, jsonHandler);
        this.queryClient = new QueryClientImpl(jsonRpcClientProvider);
        this.executionClient = new ExecutionClientImpl(jsonRpcClientProvider);
        this.subscribeClient = new SubscribeClientImpl(jsonRpcClientProvider);
        this.transactionClient = new TransactionClientImpl(jsonRpcClientProvider);
        this.faucetClient = null;
        this.zkServiceClient = null;
    }

    public OneChain(String fullNodeEndpoint, String faucetEndpoint, String zkServiceEndpoint, String keyStorePath) {
        final JsonHandler jsonHandler = new GsonJsonHandler();
        final JsonRpcClientProvider jsonRpcClientProvider =
                new OkHttpJsonRpcClientProvider(fullNodeEndpoint, jsonHandler);
        this.queryClient = new QueryClientImpl(jsonRpcClientProvider);
        this.executionClient = new ExecutionClientImpl(jsonRpcClientProvider);
        this.subscribeClient = new SubscribeClientImpl(jsonRpcClientProvider);
        this.transactionClient = new TransactionClientImpl(jsonRpcClientProvider);
        if (StringUtils.isNotBlank(faucetEndpoint)) {
            this.faucetClient = new OkhttpFaucetClient(faucetEndpoint, jsonHandler);
        } else {
            this.faucetClient = null;
        }
        if (StringUtils.isNotBlank(keyStorePath)) {
            this.keyStore = new FileBasedKeyStore(keyStorePath);
        } else {
            this.keyStore = null;
        }
        this.zkServiceClient = new OkhttpZkServiceClient(zkServiceEndpoint, jsonHandler);
    }


    /**
     * Request sui from faucet completable future.
     *
     * @param address the address
     * @return the completable future
     */
    public CompletableFuture<FaucetResponse> requestOneChainFromFaucet(String address) {
        return this.faucetClient.requestOneChainFromFaucet(address);
    }


    /**
     * Request sui from faucet completable future.
     *
     * @param zkServiceReqeust the address
     * @return the completable future
     */
    public CompletableFuture<ZKLoginDataResponse> requestOneChainZkService(ZkServiceReqeust zkServiceReqeust) {
        return this.zkServiceClient.requestZkService(zkServiceReqeust);
    }


    /**
     * New address key response.
     *
     * @param signatureScheme the signature scheme
     * @return the key response
     */
    public KeyResponse newAddress(SignatureScheme signatureScheme) {
        return this.keyStore.generateNewKey(signatureScheme);
    }

    /**
     * Transfer sui completable future.
     *
     * @param sender                          the signer
     * @param coin                            the coin
     * @param recipient                       the recipient
     * @param amount                          the amount
     * @param gas                             the gas
     * @param gasBudget                       the gas budget
     * @param gasPrice                        the gas price
     * @param expiration                      the expiration
     * @param transactionBlockResponseOptions the transaction response options
     * @param requestType                     the request type
     * @return the completable future
     */
    public CompletableFuture<TransactionBlockResponse> transferCoin(
            String sender,
            String coin,
            String recipient,
            Long amount,
            String gas,
            Long gasBudget,
            Long gasPrice,
            Long expiration,
            TransactionBlockResponseOptions transactionBlockResponseOptions,
            ExecuteTransactionRequestType requestType) {
        return this.newTransactionBlock()
                .thenCompose(
                        (Function<TransactionBlock, CompletableFuture<TransactionBlockResponse>>)
                                transactionBlock -> {
                                    transactionBlock.setExpiration(expiration);
                                    transactionBlock.setSender(sender);
                                    return transactionBlock
                                            .splitCoins(coin, Lists.newArrayList(amount))
                                            .thenCompose(
                                                    (Function<Argument, CompletableFuture<TransactionBlockResponse>>)
                                                            argument -> {
                                                                OneChainAddress.Builder recipientAddressBuilder = new Builder();
                                                                recipientAddressBuilder.value =
                                                                        transactionBlock.geAddressBytes(recipient);
                                                                transactionBlock.transferObjects(
                                                                        Lists.newArrayList(argument),
                                                                        transactionBlock.pure(recipientAddressBuilder.build()));
                                                                CompletableFuture<TransactionData>
                                                                        transactionDataCompletableFuture =
                                                                        transactionBlock
                                                                                .setGasData(
                                                                                        gas != null
                                                                                                ? Lists.newArrayList(gas)
                                                                                                : Lists.newArrayList(),
                                                                                        sender,
                                                                                        gasBudget,
                                                                                        gasPrice)
                                                                                .thenCompose(
                                                                                        (Function<Void, CompletableFuture<TransactionData>>)
                                                                                                unused -> transactionBlock.build());

                                                                return transactionDataCompletableFuture.thenCompose(
                                                                        (Function<
                                                                                TransactionData,
                                                                                CompletableFuture<TransactionBlockResponse>>)
                                                                                transactionData ->
                                                                                        executeTransaction(
                                                                                                sender, transactionData,
                                                                                                transactionBlockResponseOptions, requestType));
                                                            });
                                });
    }

    /**
     * New transaction block completable future.
     *
     * @return the completable future
     */
    public CompletableFuture<TransactionBlock> newTransactionBlock() {
        return CompletableFuture.completedFuture(new TransactionBlock(queryClient));
    }

    public CompletableFuture<TransactionBlock> newTransactionBlock(boolean useSelfGas) {
        return CompletableFuture.completedFuture(new TransactionBlock(queryClient, useSelfGas));
    }


    /**
     * Move call completable future.
     *
     * @param sender                          the signer
     * @param packageObjectId                 the package object id
     * @param module                          the module
     * @param function                        the function
     * @param typeArguments                   the type arguments
     * @param arguments                       the arguments
     * @param gas                             the gas
     * @param gasBudget                       the gas budget
     * @param gasPrice                        the gas price
     * @param expiration                      the expiration
     * @param transactionBlockResponseOptions the transaction response options
     * @param requestType                     the request type
     * @return the completable future
     */
    public CompletableFuture<TransactionBlockResponse> moveCall(
            String sender,
            String packageObjectId,
            String module,
            String function,
            List<TypeTag> typeArguments,
            List<?> arguments,
            String gas,
            Long gasBudget,
            Long gasPrice,
            Long expiration,
            TransactionBlockResponseOptions transactionBlockResponseOptions,
            ExecuteTransactionRequestType requestType) {
        return this.newTransactionBlock()
                .thenCompose(
                        (Function<TransactionBlock, CompletableFuture<TransactionBlockResponse>>)
                                transactionBlock -> {
                                    transactionBlock.setExpiration(expiration);
                                    transactionBlock.setSender(sender);
                                    return transactionBlock
                                            .moveCall(packageObjectId, module, function, typeArguments, arguments, null)
                                            .thenCompose(
                                                    (Function<Argument, CompletableFuture<TransactionBlockResponse>>)
                                                            argument -> {
                                                                CompletableFuture<TransactionData>
                                                                        transactionDataCompletableFuture =
                                                                        transactionBlock
                                                                                .setGasData(
                                                                                        gas != null
                                                                                                ? Lists.newArrayList(gas)
                                                                                                : Lists.newArrayList(),
                                                                                        sender,
                                                                                        gasBudget,
                                                                                        gasPrice)
                                                                                .thenCompose(
                                                                                        (Function<Void, CompletableFuture<TransactionData>>)
                                                                                                unused -> transactionBlock.build());

                                                                return transactionDataCompletableFuture.thenCompose(
                                                                        (Function<
                                                                                TransactionData,
                                                                                CompletableFuture<TransactionBlockResponse>>)
                                                                                transactionData ->
                                                                                        executeTransaction(
                                                                                                sender, transactionData,
                                                                                                transactionBlockResponseOptions, requestType));
                                                            });
                                });
    }


    public TransactionData multipleMoveCall(
            String sender,
            String sponsored,
            List<MoveCallParam> params,
            String gas,
            Long gasBudget,
            Long gasPrice,
            Long expiration) {
        CompletableFuture<TransactionData> transactionDataCompletableFuture = this.newTransactionBlock()
                .thenCompose(
                        (Function<TransactionBlock, CompletableFuture<TransactionData>>)
                                transactionBlock -> {
                                    transactionBlock.setExpiration(expiration);
                                    transactionBlock.setSender(sender);
                                    return transactionBlock
                                            .multipleMoveCall(params)
                                            .thenCompose(
                                                    (Function<Argument, CompletableFuture<TransactionData>>)
                                                            argument -> transactionBlock
                                                                    .setGasData(
                                                                            gas != null
                                                                                    ? Lists.newArrayList(gas)
                                                                                    : Lists.newArrayList(),
                                                                            sponsored,
                                                                            gasBudget,
                                                                            gasPrice)
                                                                    .thenCompose(
                                                                            (Function<Void, CompletableFuture<TransactionData>>)
                                                                                    unused -> transactionBlock.build()));
                                });
        TransactionData transactionData = null;
        try {
            transactionData = transactionDataCompletableFuture.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return transactionData;
    }

    public TransactionData multipleMoveCall(
            String sender,
            List<MoveCallParam> params,
            String gas,
            Long gasBudget,
            Long gasPrice,
            Long expiration) {
        CompletableFuture<TransactionData> transactionDataCompletableFuture = this.newTransactionBlock()
                .thenCompose(
                        (Function<TransactionBlock, CompletableFuture<TransactionData>>)
                                transactionBlock -> {
                                    transactionBlock.setExpiration(expiration);
                                    transactionBlock.setSender(sender);
                                    return transactionBlock
                                            .multipleMoveCall(params)
                                            .thenCompose(
                                                    (Function<Argument, CompletableFuture<TransactionData>>)
                                                            argument -> transactionBlock
                                                                    .setGasData(
                                                                            gas != null
                                                                                    ? Lists.newArrayList(gas)
                                                                                    : Lists.newArrayList(),
                                                                            sender,
                                                                            gasBudget,
                                                                            gasPrice)
                                                                    .thenCompose(
                                                                            (Function<Void, CompletableFuture<TransactionData>>)
                                                                                    unused -> transactionBlock.build()));
                                });
        TransactionData transactionData = null;
        try {
            transactionData = transactionDataCompletableFuture.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return transactionData;
    }


    public TransactionData multipleMoveCallForSponsorTransaction(
            String sender,
            List<MoveCallParam> params,
            Long gasBudget,
            Long gasPrice,
            Long expiration) {
        CompletableFuture<TransactionData> transactionDataCompletableFuture = this.newTransactionBlock(false)
                .thenCompose(
                        (Function<TransactionBlock, CompletableFuture<TransactionData>>)
                                transactionBlock -> {
                                    transactionBlock.setExpiration(expiration);
                                    transactionBlock.setSender(sender);
                                    return transactionBlock
                                            .multipleMoveCall(params)
                                            .thenCompose(
                                                    (Function<Argument, CompletableFuture<TransactionData>>)
                                                            argument -> transactionBlock
                                                                    .setGasData(
                                                                            Lists.newArrayList(),
                                                                            sender,
                                                                            gasBudget,
                                                                            gasPrice)
                                                                    .thenCompose(
                                                                            (Function<Void, CompletableFuture<TransactionData>>)
                                                                                    unused -> transactionBlock.build()));
                                });
        TransactionData transactionData = null;
        try {
            transactionData = transactionDataCompletableFuture.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return transactionData;
    }

    /**
     * Merge coin completable future.
     *
     * @param sender                          the sender
     * @param destCoin                        the dest coin
     * @param sourceCoins                     the source coins
     * @param gas                             the gas
     * @param gasBudget                       the gas budget
     * @param gasPrice                        the gas price
     * @param expiration                      the expiration
     * @param transactionBlockResponseOptions the transaction response options
     * @param requestType                     the request type
     * @return the completable future
     */
    public CompletableFuture<TransactionBlockResponse> mergeCoin(
            String sender,
            String destCoin,
            List<String> sourceCoins,
            String gas,
            Long gasBudget,
            Long gasPrice,
            Long expiration,
            TransactionBlockResponseOptions transactionBlockResponseOptions,
            ExecuteTransactionRequestType requestType) {
        return this.newTransactionBlock()
                .thenCompose(
                        (Function<TransactionBlock, CompletableFuture<TransactionBlockResponse>>)
                                transactionBlock -> {
                                    transactionBlock.setExpiration(expiration);
                                    transactionBlock.setSender(sender);
                                    return transactionBlock
                                            .mergeCoins(destCoin, sourceCoins)
                                            .thenCompose(
                                                    (Function<Argument, CompletableFuture<TransactionBlockResponse>>)
                                                            argument -> {
                                                                CompletableFuture<TransactionData>
                                                                        transactionDataCompletableFuture =
                                                                        transactionBlock
                                                                                .setGasData(
                                                                                        gas != null
                                                                                                ? Lists.newArrayList(gas)
                                                                                                : Lists.newArrayList(),
                                                                                        sender,
                                                                                        gasBudget,
                                                                                        gasPrice)
                                                                                .thenCompose(
                                                                                        (Function<Void, CompletableFuture<TransactionData>>)
                                                                                                unused -> transactionBlock.build());

                                                                return transactionDataCompletableFuture.thenCompose(
                                                                        (Function<
                                                                                TransactionData,
                                                                                CompletableFuture<TransactionBlockResponse>>)
                                                                                transactionData ->
                                                                                        executeTransaction(
                                                                                                sender, transactionData,
                                                                                                transactionBlockResponseOptions, requestType));
                                                            });
                                });
    }

    /**
     * Transfer objects completable future.
     *
     * @param sender                          the sender
     * @param suiObjects                      the sui objects
     * @param recipient                       the recipient
     * @param gas                             the gas
     * @param gasBudget                       the gas budget
     * @param gasPrice                        the gas price
     * @param expiration                      the expiration
     * @param transactionBlockResponseOptions the transaction response options
     * @param requestType                     the request type
     * @return the completable future
     */
    public CompletableFuture<TransactionBlockResponse> transferObjects(
            String sender,
            List<String> suiObjects,
            String recipient,
            String gas,
            Long gasBudget,
            Long gasPrice,
            Long expiration,
            TransactionBlockResponseOptions transactionBlockResponseOptions,
            ExecuteTransactionRequestType requestType) {
        return this.newTransactionBlock()
                .thenCompose(
                        (Function<TransactionBlock, CompletableFuture<TransactionBlockResponse>>)
                                transactionBlock -> {
                                    transactionBlock.setExpiration(expiration);
                                    transactionBlock.setSender(sender);
                                    return transactionBlock
                                            .transferObjects(suiObjects, recipient)
                                            .thenCompose(
                                                    (Function<Argument, CompletableFuture<TransactionBlockResponse>>)
                                                            argument -> {
                                                                CompletableFuture<TransactionData>
                                                                        transactionDataCompletableFuture =
                                                                        transactionBlock
                                                                                .setGasData(
                                                                                        gas != null
                                                                                                ? Lists.newArrayList(gas)
                                                                                                : Lists.newArrayList(),
                                                                                        sender,
                                                                                        gasBudget,
                                                                                        gasPrice)
                                                                                .thenCompose(
                                                                                        (Function<Void, CompletableFuture<TransactionData>>)
                                                                                                unused -> transactionBlock.build());

                                                                return transactionDataCompletableFuture.thenCompose(
                                                                        (Function<
                                                                                TransactionData,
                                                                                CompletableFuture<TransactionBlockResponse>>)
                                                                                transactionData ->
                                                                                        executeTransaction(
                                                                                                sender, transactionData,
                                                                                                transactionBlockResponseOptions, requestType));
                                                            });
                                });
    }

    /**
     * Publish completable future.
     *
     * @param sender                          the signer
     * @param compiledModules                 the compiled modules
     * @param depIds                          the dep ids
     * @param gas                             the gas
     * @param gasBudget                       the gas budget
     * @param gasPrice                        the gas price
     * @param expiration                      the expiration
     * @param transactionBlockResponseOptions the transaction response options
     * @param requestType                     the request type
     * @return the completable future
     */
    public CompletableFuture<TransactionBlockResponse> publish(
            String sender,
            List<String> compiledModules,
            List<String> depIds,
            String gas,
            Long gasBudget,
            Long gasPrice,
            Long expiration,
            TransactionBlockResponseOptions transactionBlockResponseOptions,
            ExecuteTransactionRequestType requestType) {

        return this.newTransactionBlock()
                .thenCompose(
                        (Function<TransactionBlock, CompletableFuture<TransactionBlockResponse>>)
                                transactionBlock -> {
                                    transactionBlock.setExpiration(expiration);
                                    transactionBlock.setSender(sender);
                                    Argument result = transactionBlock.publish(compiledModules, depIds);
                                    OneChainAddress.Builder senderAddressBuilder = new Builder();
                                    senderAddressBuilder.value = transactionBlock.geAddressBytes(sender);
                                    transactionBlock.transferObjects(
                                            Lists.newArrayList(result),
                                            transactionBlock.pure(senderAddressBuilder.build()));

                                    CompletableFuture<TransactionData> transactionDataCompletableFuture =
                                            transactionBlock
                                                    .setGasData(
                                                            gas != null ? Lists.newArrayList(gas) : Lists.newArrayList(),
                                                            sender,
                                                            gasBudget,
                                                            gasPrice)
                                                    .thenCompose(
                                                            (Function<Void, CompletableFuture<TransactionData>>)
                                                                    unused -> transactionBlock.build());

                                    return transactionDataCompletableFuture.thenCompose(
                                            (Function<TransactionData, CompletableFuture<TransactionBlockResponse>>)
                                                    transactionData ->
                                                            executeTransaction(
                                                                    sender, transactionData,
                                                                    transactionBlockResponseOptions, requestType));
                                });
    }

    /**
     * Subscribe event disposable.
     *
     * @param eventFilter the event filter
     * @param onNext      the on next
     * @param onError     the on error
     * @return the disposable
     */
    public Disposable subscribeEvent(
            EventFilter eventFilter, Consumer<OneChainEvent> onNext, Consumer<OneChainApiException> onError) {
        return this.subscribeClient.subscribeEvent(eventFilter, onNext, onError);
    }

    /**
     * Subscribe transaction disposable.
     *
     * @param transactionFilter the transaction filter
     * @param onNext            the on next
     * @param onError           the on error
     * @return the disposable
     */
    public Disposable subscribeTransaction(
            TransactionFilter transactionFilter,
            Consumer<TransactionBlockEffects> onNext,
            Consumer<OneChainApiException> onError) {
        return this.subscribeClient.subscribeTransaction(transactionFilter, onNext, onError);
    }

    /**
     * Gets object.
     *
     * @param id                the id
     * @param objectDataOptions the object data options
     * @return the object
     */
    public CompletableFuture<OneChainObjectResponse> getObject(
            String id, ObjectDataOptions objectDataOptions) {
        return queryClient.getObject(id, objectDataOptions);
    }

    /**
     * Gets objects owned by address.
     *
     * @param address the address
     * @param query   the query
     * @param cursor  the cursor
     * @param limit   the limit
     * @return the objects owned by address
     */
    public CompletableFuture<PaginatedObjectsResponse> getOwnedObjects(
            String address, ObjectResponseQuery query, String cursor, Integer limit) {
        return queryClient.getOwnedObjects(address, query, cursor, limit);
    }

    /**
     * Gets total transaction number.
     *
     * @return the total transaction number
     */
    public CompletableFuture<Long> getTotalTransactionBlocks() {
        return queryClient.getTotalTransactionBlocks();
    }

    /**
     * Gets transaction.
     *
     * @param digest  the digest
     * @param options the options
     * @return the transaction
     */
    public CompletableFuture<TransactionBlockResponse> getTransactionBlock(
            String digest, TransactionBlockResponseOptions options) {
        return queryClient.getTransactionBlock(digest, options);
    }

    /**
     * Query transaction blocks completable future.
     *
     * @param query       the query
     * @param cursor      the cursor
     * @param limit       the limit
     * @param isDescOrder the is desc order
     * @return the completable future
     */
    public CompletableFuture<PaginatedTransactionBlockResponse> queryTransactionBlocks(
            TransactionBlockResponseQuery query, String cursor, Integer limit, boolean isDescOrder) {
        return queryClient.queryTransactionBlocks(query, cursor, limit, isDescOrder);
    }

    /**
     * Multi get transaction blocks completable future.
     *
     * @param digests the digests
     * @param options the options
     * @return the completable future
     */
    public CompletableFuture<List<TransactionBlockResponse>> multiGetTransactionBlocks(
            List<String> digests, TransactionBlockResponseOptions options) {
        return queryClient.multiGetTransactionBlocks(digests, options);
    }

    /**
     * Multi get objects completable future.
     *
     * @param objectIds the object ids
     * @param options   the options
     * @return the completable future
     */
    public CompletableFuture<List<OneChainObjectResponse>> multiGetObjects(
            List<String> objectIds, ObjectDataOptions options) {
        return queryClient.multiGetObjects(objectIds, options);
    }

    public CompletableFuture<PaginatedDynamicFields> getDynamicFields(String dynamicId) {
        return queryClient.getDynamicFields(dynamicId);
    }

    public CompletableFuture<OneChainObjectResponse> getDynamicFieldObject(String dynamicId, DynamicFieldName fieldName) {
        return queryClient.getDynamicFieldObject(dynamicId, fieldName);
    }


    /**
     * Gets events.
     *
     * @param eventFilter the event filter
     * @param cursor      the cursor
     * @param limit       the limit
     * @param isDescOrder the is desc order
     * @return the events
     */
    public CompletableFuture<PaginatedEvents> queryEvents(
            EventFilter eventFilter, EventId cursor, Integer limit, boolean isDescOrder) {
        return queryClient.queryEvents(eventFilter, cursor, limit, isDescOrder);
    }

    /**
     * Gets normalized move modules by package.
     *
     * @param packageId the package id
     * @return the normalized move modules by package
     */
    public CompletableFuture<Map<String, MoveNormalizedModule>> getNormalizedMoveModulesByPackage(
            String packageId) {
        return queryClient.getNormalizedMoveModulesByPackage(packageId);
    }

    /**
     * Gets committee info.
     *
     * @param epoch the epoch
     * @return the committee info
     */
    public CompletableFuture<OneChainCommitteeInfo> getCommitteeInfo(BigInteger epoch) {
        return queryClient.getCommitteeInfo(epoch);
    }

    /**
     * Gets move function arg types.
     *
     * @param suiPackage the sui package
     * @param module     the module
     * @param function   the function
     * @return the move function arg types
     */
    public CompletableFuture<List<MoveFunctionArgType>> getMoveFunctionArgTypes(
            String suiPackage, String module, String function) {
        return queryClient.getMoveFunctionArgTypes(suiPackage, module, function);
    }

    /**
     * Gets normalized move function.
     *
     * @param suiPackage the sui package
     * @param module     the module
     * @param function   the function
     * @return the normalized move function
     */
    public CompletableFuture<MoveNormalizedFunction> getNormalizedMoveFunction(
            String suiPackage, String module, String function) {
        return queryClient.getNormalizedMoveFunction(suiPackage, module, function);
    }

    /**
     * Gets normalized move module.
     *
     * @param suiPackage the sui package
     * @param module     the module
     * @return the normalized move module
     */
    public CompletableFuture<MoveNormalizedModule> getNormalizedMoveModule(
            String suiPackage, String module) {
        return queryClient.getNormalizedMoveModule(suiPackage, module);
    }

    /**
     * Gets normalized move struct.
     *
     * @param suiPackage the sui package
     * @param module     the module
     * @param struct     the struct
     * @return the normalized move struct
     */
    public CompletableFuture<MoveNormalizedStruct> getNormalizedMoveStruct(
            String suiPackage, String module, String struct) {
        return queryClient.getNormalizedMoveStruct(suiPackage, module, struct);
    }

    /**
     * Gets checkpoints.
     *
     * @param cursor      the cursor
     * @param limit       the limit
     * @param isDescOrder the is desc order
     * @return the checkpoints
     */
    public CompletableFuture<PaginatedCheckpoint> getCheckpoints(
            String cursor, Integer limit, boolean isDescOrder) {
        return queryClient.getCheckpoints(cursor, limit, isDescOrder);
    }

    /**
     * Gets coin metadata.
     *
     * @param coinType the coin type
     * @return the coin metadata
     */
    public CompletableFuture<CoinMetadata> getCoinMetadata(String coinType) {
        return queryClient.getCoinMetadata(coinType);
    }

    /**
     * Gets reference gas price.
     *
     * @return the reference gas price
     */
    public CompletableFuture<Long> getReferenceGasPrice() {
        return queryClient.getReferenceGasPrice();
    }

    /**
     * get all balances by address.
     *
     * @param address the sui address
     * @return the completable future
     */
    public CompletableFuture<List<Balance>> getAllBalances(String address) {
        return queryClient.getAllBalances(address);
    }

    /**
     * get all coins owned by address.
     *
     * @param address the sui address
     * @param cursor  the cursor
     * @param limit   the limit
     * @return the completable future
     */
    public CompletableFuture<PaginatedCoins> getAllCoins(
            String address, String cursor, Integer limit) {
        return queryClient.getAllCoins(address, cursor, limit);
    }

    /**
     * get all Coin with coin_type objects owned by an address.
     *
     * @param address  the owner address
     * @param coinType the coin type
     * @param cursor   the cursor
     * @param limit    the limit
     * @return the completable future
     */
    public CompletableFuture<PaginatedCoins> getCoins(
            String address, String coinType, String cursor, Integer limit) {
        return queryClient.getCoins(address, coinType, cursor, limit);
    }

    /**
     * get the total coin balance for one coin type, owned by the address owner.
     *
     * @param address  the owner address
     * @param coinType the coin type
     * @return the completable future
     */
    public CompletableFuture<Balance> getBalance(String address, String coinType) {
        return queryClient.getBalance(address, coinType);
    }

    /**
     * get a checkpoint based on a checkpoint sequence number or digest.
     *
     * @param checkpointId the checkpoint sequence number or digest
     * @return the completable future
     */
    public CompletableFuture<Checkpoint> getCheckpoint(String checkpointId) {
        return queryClient.getCheckpoint(checkpointId);
    }

    /**
     * Gets validators apy.
     *
     * @return the validators apy
     */
    public CompletableFuture<ValidatorsApy> getValidatorsApy() {
        return queryClient.getValidatorsApy();
    }

    /**
     * Gets total supply.
     *
     * @param coin the coin
     * @return the total supply
     */
    public CompletableFuture<CoinSupply> getTotalSupply(String coin) {
        return queryClient.getTotalSupply(coin);
    }

    /**
     * Gets stakes by ids.
     *
     * @param stakeIds the stake ids
     * @return the stakes by ids
     */
    public CompletableFuture<List<DelegatedStake>> getStakesByIds(List<String> stakeIds) {
        return queryClient.getStakesByIds(stakeIds);
    }

    /**
     * Gets stakes.
     *
     * @param owner the owner
     * @return the stakes
     */
    public CompletableFuture<List<DelegatedStake>> getStakes(String owner) {
        return queryClient.getStakes(owner);
    }

    /**
     * Gets latest onechain system state.
     *
     * @return the latest onechain system state
     */
    public CompletableFuture<SystemStateSummary> getLatestOneChainSystemState() {
        return queryClient.getLatestOneChainSystemState();
    }

    /**
     * Gets events.
     *
     * @param transactionDigest the transaction digest
     * @return the events
     */
    public CompletableFuture<List<OneChainEvent>> getEvents(String transactionDigest) {
        return queryClient.getEvents(transactionDigest);
    }

    /**
     * Gets latest checkpoint sequence number.
     *
     * @return the latest checkpoint sequence number
     */
    public CompletableFuture<BigInteger> getLatestCheckpointSequenceNumber() {
        return queryClient.getLatestCheckpointSequenceNumber();
    }

    /**
     * Dry run transaction completable future.
     *
     * @param txBytes the tx bytes
     * @return the completable future
     */
    public CompletableFuture<TransactionBlockEffects> dryRunTransaction(String txBytes) {
        return executionClient.dryRunTransaction(txBytes);
    }

    /**
     * Gets by address.
     *
     * @param address the address
     * @return the by address
     */
    public OneChainKeyPair<?> getByAddress(String address) {
        return keyStore.getByAddress(address);
    }

    /**
     * Addresses navigable set.
     *
     * @return the navigable set
     */
    public NavigableSet<String> addresses() {
        return keyStore.addresses();
    }

    /**
     * Execute transaction completable future.
     *
     * @param signer                          the signer
     * @param transactionData                 the transaction data
     * @param transactionBlockResponseOptions the transaction response options
     * @param requestType                     the request type
     * @return the completable future
     */
    public CompletableFuture<TransactionBlockResponse> executeTransaction(
            String signer,
            TransactionData transactionData,
            TransactionBlockResponseOptions transactionBlockResponseOptions,
            ExecuteTransactionRequestType requestType) {
        return this.executeTransaction(
                signer,
                transactionData,
                transactionDataIntent(),
                transactionBlockResponseOptions,
                requestType);
    }

    /**
     * Execute transaction completable future.
     *
     * @param transactionData                 the transaction data
     * @param signatures                      the signatures
     * @param transactionBlockResponseOptions the transaction block response options
     * @param requestType                     the request type
     * @return the completable future
     */
    public CompletableFuture<TransactionBlockResponse> executeTransaction(
            TransactionData transactionData,
            List<String> signatures,
            TransactionBlockResponseOptions transactionBlockResponseOptions,
            ExecuteTransactionRequestType requestType) {
        try {
            return executionClient.executeTransaction(
                    Base64.toBase64String(transactionData.bcsSerialize()),
                    signatures,
                    transactionBlockResponseOptions,
                    requestType);
        } catch (SerializationError e) {
            throw new BcsSerializationException(e);
        }
    }

    /**
     * Execute transaction completable future.
     *
     * @param signer                          the signer
     * @param transactionData                 the transaction data
     * @param intent                          the intent
     * @param transactionBlockResponseOptions the transaction response options
     * @param requestType                     the request type
     * @return the completable future
     */
    public CompletableFuture<TransactionBlockResponse> executeTransaction(
            String signer,
            TransactionData transactionData,
            Intent intent,
            TransactionBlockResponseOptions transactionBlockResponseOptions,
            ExecuteTransactionRequestType requestType) {
        final OneChainKeyPair<?> oneChainKeyPair = keyStore.getByAddress(signer);
        final byte[] publicKey = oneChainKeyPair.publicKeyBytes();
        final SignatureScheme signatureScheme = oneChainKeyPair.signatureScheme();

        final byte[] txBytes;
        final byte[] intentBytes;
        try {
            txBytes = transactionData.bcsSerialize();
            intentBytes = intent.bcsSerialize();
        } catch (SerializationError e) {
            CompletableFuture<TransactionBlockResponse> future = new CompletableFuture<>();
            future.completeExceptionally(new OneChainApiException(new BcsSerializationException(e)));
            return future;
        }

        return signAndExecuteTransaction(
                txBytes,
                intentBytes,
                transactionBlockResponseOptions,
                requestType,
                oneChainKeyPair,
                publicKey,
                signatureScheme);
    }

    /**
     * Sign transaction block string.
     *
     * @param signer          the signer
     * @param transactionData the transaction data
     * @param intent          the intent
     * @return the string
     * @throws SigningException the signing exception
     */
    public String signTransactionBlock(String signer, TransactionData transactionData, Intent intent)
            throws SigningException {
        final OneChainKeyPair<?> oneChainKeyPair = keyStore.getByAddress(signer);
        final byte[] publicKey = oneChainKeyPair.publicKeyBytes();
        final SignatureScheme signatureScheme = oneChainKeyPair.signatureScheme();

        final byte[] txBytes;
        final byte[] intentBytes;
        try {
            txBytes = transactionData.bcsSerialize();
            intentBytes = intent.bcsSerialize();

            final byte[] signature;
            final Blake2b256 blake2b256 = new Blake2b256();
            final byte[] hash = blake2b256.digest(Arrays.concatenate(intentBytes, txBytes));
            signature = oneChainKeyPair.sign(hash);

            final byte[] serializedSignatureBytes =
                    Arrays.concatenate(new byte[]{signatureScheme.getScheme()}, signature, publicKey);
            return Base64.toBase64String(serializedSignatureBytes);
        } catch (SerializationError e) {
            throw new BcsSerializationException(e);
        }
    }

    public static String signTransactionBlock(String priKey, TransactionData transactionData)
            throws SigningException {
        final OneChainKeyPair<?> oneChainKeyPair = OneChainKeyPair.decodeBase64(priKey);
        final byte[] publicKey = oneChainKeyPair.publicKeyBytes();
        final SignatureScheme signatureScheme = oneChainKeyPair.signatureScheme();

        final byte[] txBytes;
        final byte[] intentBytes;
        Intent intent = staticTransactionDataIntent();
        try {
            txBytes = transactionData.bcsSerialize();
            intentBytes = intent.bcsSerialize();

            final byte[] signature;
            final Blake2b256 blake2b256 = new Blake2b256();
            final byte[] hash = blake2b256.digest(Arrays.concatenate(intentBytes, txBytes));
            signature = oneChainKeyPair.sign(hash);

            final byte[] serializedSignatureBytes =
                    Arrays.concatenate(new byte[]{signatureScheme.getScheme()}, signature, publicKey);
            return Base64.toBase64String(serializedSignatureBytes);
        } catch (SerializationError e) {
            throw new BcsSerializationException(e);
        }
    }

    public static String signTransactionBlockByte(String priKey, final byte[] txBytes)
            throws SigningException {
        final OneChainKeyPair<?> oneChainKeyPair = OneChainKeyPair.decodeBase64(priKey);
        final byte[] publicKey = oneChainKeyPair.publicKeyBytes();
        final SignatureScheme signatureScheme = oneChainKeyPair.signatureScheme();

        final byte[] intentBytes;
        Intent intent = staticTransactionDataIntent();
        try {
            intentBytes = intent.bcsSerialize();
            final byte[] signature;
            final Blake2b256 blake2b256 = new Blake2b256();
            final byte[] hash = blake2b256.digest(Arrays.concatenate(intentBytes, txBytes));


            signature = oneChainKeyPair.sign(hash);
            final byte[] serializedSignatureBytes =
                    Arrays.concatenate(new byte[]{signatureScheme.getScheme()}, signature, publicKey);
            return Base64.toBase64String(serializedSignatureBytes);
        } catch (SerializationError e) {
            throw new BcsSerializationException(e);
        }
    }

    private CompletableFuture<TransactionBlockResponse> signAndExecuteTransaction(
            byte[] transactionData,
            byte[] intentBytes,
            TransactionBlockResponseOptions transactionBlockResponseOptions,
            ExecuteTransactionRequestType requestType,
            OneChainKeyPair<?> oneChainKeyPair,
            byte[] publicKey,
            SignatureScheme signatureScheme) {
        final byte[] signature;
        try {
            final Blake2b256 blake2b256 = new Blake2b256();
            final byte[] hash = blake2b256.digest(Arrays.concatenate(intentBytes, transactionData));
            signature = oneChainKeyPair.sign(hash);

        } catch (SigningException e) {
            CompletableFuture<TransactionBlockResponse> future = new CompletableFuture<>();
            future.completeExceptionally(new OneChainApiException(e));
            return future;
        }

        final byte[] serializedSignatureBytes =
                Arrays.concatenate(new byte[]{signatureScheme.getScheme()}, signature, publicKey);
        final String serializedSignature = Base64.toBase64String(serializedSignatureBytes);

        return executionClient.executeTransaction(
                Base64.toBase64String(transactionData),
                Lists.newArrayList(serializedSignature),
                transactionBlockResponseOptions,
                requestType);
    }

    public TransactionBlockResponse executeTransaction(String txBytes,
                                                       List<String> signatures,
                                                       TransactionBlockResponseOptions transactionBlockResponseOptions,
                                                       ExecuteTransactionRequestType requestType) throws Exception {
        CompletableFuture<TransactionBlockResponse> transactionBlockResponseCompletableFuture = executionClient.executeTransaction(
                txBytes,
                signatures,
                transactionBlockResponseOptions,
                requestType);
        return transactionBlockResponseCompletableFuture.get();
    }

    /**
     * Transaction data intent intent.
     *
     * @return the intent
     */
    public Intent transactionDataIntent() {
        final Intent.Builder intentBuilder = new Intent.Builder();
        intentBuilder.app_id = 0;
        intentBuilder.scope = 0;
        intentBuilder.version = 0;

        return intentBuilder.build();
    }

    public static Intent staticTransactionDataIntent() {
        final Intent.Builder intentBuilder = new Intent.Builder();
        intentBuilder.app_id = 0;
        intentBuilder.scope = 0;
        intentBuilder.version = 0;

        return intentBuilder.build();
    }

    /**
     * 将 ONECHAIN 币发送到地址列表
     *
     * @param signer     交易签名者的 Sui 地址
     * @param inputCoins 本次交易中要使用的 Sui 币，包括用于支付 Gas 的币
     * @param recipients 收件人的地址，此向量的长度必须与金额相同
     * @param amounts    按照顺序向收款人转账的金额 单位是 MIST
     * @param gasBudget  gas 预算，如果 gas 成本超过预算，交易将失败 单位是 MIST
     * @return 交易的二进制数据
     */
    public CompletableFuture<TransactionBlockBytes> unsafePaySui(String signer, List<String> inputCoins,
                                                                 List<String> recipients, List<String> amounts,
                                                                 String gasBudget) {
        return transactionClient.unsafePayOneChain(signer, inputCoins, recipients, amounts, gasBudget);
    }

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
    public CompletableFuture<TransactionBlockBytes> unsafePay(String signer, List<String> inputCoins,
                                                              List<String> recipients, List<String> amounts,
                                                              String gasObjectId, String gasBudget) {
        return transactionClient.unsafePay(signer, inputCoins, recipients, amounts, gasObjectId, gasBudget);
    }


    /**
     * 通过域名查询地址
     * @param name
     * @return
     */
    CompletableFuture<String> resolveNameServiceAddress(String name){
        return queryClient.resolveNameServiceAddress(name);
    }

}
