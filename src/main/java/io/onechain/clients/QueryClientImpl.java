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

package io.onechain.clients;


import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import io.onechain.jsonrpc.JsonRpc20Request;
import io.onechain.jsonrpc.JsonRpcClientProvider;
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
import io.onechain.models.transactions.PaginatedTransactionBlockResponse;
import io.onechain.models.transactions.TransactionBlockResponse;
import io.onechain.models.transactions.TransactionBlockResponseOptions;
import io.onechain.models.transactions.TransactionBlockResponseQuery;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

/**
 * The type Sui client.
 *
 * @author chiyu
 * @since 2022.11
 */
public class QueryClientImpl implements QueryClient {

    private final JsonRpcClientProvider jsonRpcClientProvider;

    private final static ConcurrentHashMap<String, CompletableFuture<MoveNormalizedFunction>> NORMALIZED_FUNCTION_PARAMS_CACHE = new ConcurrentHashMap<>();


    /**
     * Instantiates a new Sui client.
     *
     * @param jsonRpcClientProvider the json rpc client provider
     */
    public QueryClientImpl(JsonRpcClientProvider jsonRpcClientProvider) {
        this.jsonRpcClientProvider = jsonRpcClientProvider;
    }

    @Override
    public CompletableFuture<OneChainObjectResponse> getObject(
            String id, ObjectDataOptions objectDataOptions) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "sui_getObject", Lists.newArrayList(id, objectDataOptions));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/sui_getObject", request, new TypeToken<OneChainObjectResponse>() {
                }.getType());
    }

    public CompletableFuture<OneChainObjectRef> getObjectRef(
            String id, ObjectDataOptions objectDataOptions) {
        return this.getObject(id, objectDataOptions).thenApply(OneChainObjectResponse::getObjectRef);
    }

    @Override
    public CompletableFuture<PaginatedObjectsResponse> getOwnedObjects(
            String address, ObjectResponseQuery query, String cursor, Integer limit) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_getOwnedObjects", Lists.newArrayList(address, query, cursor, limit));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getOwnedObjects", request, new TypeToken<PaginatedObjectsResponse>() {
                }.getType());
    }

    @Override
    public CompletableFuture<Long> getTotalTransactionBlocks() {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "sui_getTotalTransactionBlocks", Lists.newArrayList());
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/sui_getTotalTransactionBlocks", request, new TypeToken<Long>() {
                }.getType());
    }

    @Override
    public CompletableFuture<TransactionBlockResponse> getTransactionBlock(
            String digest, TransactionBlockResponseOptions options) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "sui_getTransactionBlock", Lists.newArrayList(digest, options));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/sui_getTransactionBlock",
                request,
                new TypeToken<TransactionBlockResponse>() {
                }.getType());
    }

    @Override
    public CompletableFuture<List<TransactionBlockResponse>> multiGetTransactionBlocks(
            List<String> digests, TransactionBlockResponseOptions options) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "sui_multiGetTransactionBlocks", Lists.newArrayList(digests, options));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/sui_multiGetTransactionBlocks",
                request,
                new TypeToken<List<TransactionBlockResponse>>() {
                }.getType());
    }

    @Override
    public CompletableFuture<List<OneChainObjectResponse>> multiGetObjects(
            List<String> objectIds, ObjectDataOptions options) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "sui_multiGetObjects", Lists.newArrayList(objectIds, options));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/sui_multiGetObjects", request, new TypeToken<List<OneChainObjectResponse>>() {
                }.getType());
    }

    @Override
    public CompletableFuture<PaginatedEvents> queryEvents(
            EventFilter eventFilter, EventId cursor, Integer limit, boolean isDescOrder) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_queryEvents", Lists.newArrayList(eventFilter, cursor, limit, isDescOrder));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_queryEvents", request, new TypeToken<PaginatedEvents>() {
                }.getType());
    }

    @Override
    public CompletableFuture<Map<String, MoveNormalizedModule>> getNormalizedMoveModulesByPackage(
            String packageId) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "sui_getNormalizedMoveModulesByPackage", Lists.newArrayList(packageId));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/sui_getNormalizedMoveModulesByPackage",
                request,
                new TypeToken<Map<String, MoveNormalizedModule>>() {
                }.getType());
    }

    @Override
    public CompletableFuture<OneChainCommitteeInfo> getCommitteeInfo(BigInteger epoch) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_getCommitteeInfo", Lists.newArrayList(epoch.toString()));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getCommitteeInfo", request, new TypeToken<OneChainCommitteeInfo>() {
                }.getType());
    }

    @Override
    public CompletableFuture<List<MoveFunctionArgType>> getMoveFunctionArgTypes(
            String suiPackage, String module, String function) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "sui_getMoveFunctionArgTypes", Lists.newArrayList(suiPackage, module, function));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/sui_getMoveFunctionArgTypes",
                request,
                new TypeToken<List<MoveFunctionArgType>>() {
                }.getType());
    }


    @Override
    public CompletableFuture<MoveNormalizedFunction> getNormalizedMoveFunction(
            String suiPackage, String module, String function) {
        String key = String.format("%s%s%s", suiPackage, module, function);
        CompletableFuture<MoveNormalizedFunction> moveNormalizedFunctionCompletableFuture = NORMALIZED_FUNCTION_PARAMS_CACHE.get(key);
        if (Objects.nonNull(moveNormalizedFunctionCompletableFuture)) {
            return moveNormalizedFunctionCompletableFuture;
        }
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "sui_getNormalizedMoveFunction", Lists.newArrayList(suiPackage, module, function));
        CompletableFuture<MoveNormalizedFunction> objectCompletableFuture = this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/sui_getNormalizedMoveFunction",
                request,
                new TypeToken<MoveNormalizedFunction>() {
                }.getType());
        NORMALIZED_FUNCTION_PARAMS_CACHE.put(key, objectCompletableFuture);
        return objectCompletableFuture;
    }

    @Override
    public CompletableFuture<MoveNormalizedModule> getNormalizedMoveModule(
            String suiPackage, String module) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "sui_getNormalizedMoveModule", Lists.newArrayList(suiPackage, module));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/sui_getNormalizedMoveModule",
                request,
                new TypeToken<MoveNormalizedModule>() {
                }.getType());
    }

    @Override
    public CompletableFuture<MoveNormalizedStruct> getNormalizedMoveStruct(
            String suiPackage, String module, String struct) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "sui_getNormalizedMoveStruct", Lists.newArrayList(suiPackage, module, struct));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/sui_getNormalizedMoveStruct",
                request,
                new TypeToken<MoveNormalizedStruct>() {
                }.getType());
    }

    @Override
    public CompletableFuture<PaginatedTransactionBlockResponse> queryTransactionBlocks(
            TransactionBlockResponseQuery query, String cursor, Integer limit, boolean isDescOrder) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_queryTransactionBlocks", Lists.newArrayList(query, cursor, limit, isDescOrder));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_queryTransactionBlocks",
                request,
                new TypeToken<PaginatedTransactionBlockResponse>() {
                }.getType());
    }

    @Override
    public CompletableFuture<CoinMetadata> getCoinMetadata(String coinType) {
        if (StringUtils.isEmpty(coinType)) {
            coinType = DEFAULT_COIN_TYPE;
        }
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_getCoinMetadata", Lists.newArrayList(coinType));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getCoinMetadata", request, new TypeToken<CoinMetadata>() {
                }.getType());
    }

    @Override
    public CompletableFuture<Long> getReferenceGasPrice() {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_getReferenceGasPrice", Lists.newArrayList());
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getReferenceGasPrice", request, new TypeToken<Long>() {
                }.getType());
    }

    @Override
    public CompletableFuture<List<Balance>> getAllBalances(String address) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_getAllBalances", Lists.newArrayList(address));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getAllBalances", request, new TypeToken<List<Balance>>() {
                }.getType());
    }

    @Override
    public CompletableFuture<PaginatedCoins> getAllCoins(
            String address, String cursor, Integer limit) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_getAllCoins", Lists.newArrayList(address, cursor, limit));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getAllCoins", request, new TypeToken<PaginatedCoins>() {
                }.getType());
    }

    @Override
    public CompletableFuture<PaginatedCoins> getCoins(
            String address, String coinType, String cursor, long limit) {
        if (StringUtils.isEmpty(coinType)) {
            coinType = DEFAULT_COIN_TYPE;
        }
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_getCoins", Lists.newArrayList(address, coinType, cursor, limit));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getCoins", request, new TypeToken<PaginatedCoins>() {
                }.getType());
    }

    @Override
    public CompletableFuture<Balance> getBalance(String address, String coinType) {
        if (StringUtils.isEmpty(coinType)) {
            coinType = DEFAULT_COIN_TYPE;
        }
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_getBalance", Lists.newArrayList(address, coinType));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getBalance", request, new TypeToken<Balance>() {
                }.getType());
    }

    @Override
    public CompletableFuture<Checkpoint> getCheckpoint(String checkpointId) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "sui_getCheckpoint", Lists.newArrayList(checkpointId));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/sui_getCheckpoint", request, new TypeToken<Checkpoint>() {
                }.getType());
    }

    @Override
    public CompletableFuture<ValidatorsApy> getValidatorsApy() {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_getValidatorsApy", Lists.newArrayList());
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getValidatorsApy", request, new TypeToken<ValidatorsApy>() {
                }.getType());
    }

    @Override
    public CompletableFuture<CoinSupply> getTotalSupply(String coin) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_getTotalSupply", Lists.newArrayList(coin));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getTotalSupply", request, new TypeToken<CoinSupply>() {
                }.getType());
    }

    @Override
    public CompletableFuture<List<DelegatedStake>> getStakesByIds(List<String> stakedSuiIds) {
        List<List<String>> params = Lists.newArrayList();
        params.add(stakedSuiIds);
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request("suix_getStakesByIds", params);
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getStakesByIds", request, new TypeToken<List<DelegatedStake>>() {
                }.getType());
    }

    @Override
    public CompletableFuture<List<DelegatedStake>> getStakes(String owner) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_getStakes", Lists.newArrayList(owner));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getStakes", request, new TypeToken<List<DelegatedStake>>() {
                }.getType());
    }

    @Override
    public CompletableFuture<SystemStateSummary> getLatestOneChainSystemState() {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_getLatestSuiSystemState", Lists.newArrayList());
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getLatestSuiSystemState", request, new TypeToken<SystemStateSummary>() {
                }.getType());
    }

    @Override
    public CompletableFuture<PaginatedCheckpoint> getCheckpoints(
            String cursor, Integer limit, boolean isDescOrder) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "sui_getCheckpoints", Lists.newArrayList(cursor, limit, isDescOrder));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/sui_getCheckpoints", request, new TypeToken<PaginatedCheckpoint>() {
                }.getType());
    }

    @Override
    public CompletableFuture<List<OneChainEvent>> getEvents(String transactionDigest) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "sui_getEvents", Lists.newArrayList(transactionDigest));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/sui_getEvents", request, new TypeToken<List<OneChainEvent>>() {
                }.getType());
    }

    @Override
    public CompletableFuture<BigInteger> getLatestCheckpointSequenceNumber() {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "sui_getLatestCheckpointSequenceNumber", Lists.newArrayList());
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/sui_getLatestCheckpointSequenceNumber",
                request,
                new TypeToken<BigInteger>() {
                }.getType());
    }

    @Override
    public CompletableFuture<PaginatedDynamicFields> getDynamicFields(String dynamicId) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_getDynamicFields", Lists.newArrayList(dynamicId));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getDynamicFields", request, new TypeToken<PaginatedDynamicFields>() {
                }.getType());
    }

    @Override
    public CompletableFuture<OneChainObjectResponse> getDynamicFieldObject(String dynamicId, DynamicFieldName fieldName) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_getDynamicFieldObject", Lists.newArrayList(dynamicId, fieldName));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_getDynamicFieldObject", request, new TypeToken<OneChainObjectResponse>() {
                }.getType());
    }

    @Override
    public CompletableFuture<String> resolveNameServiceAddress(String name) {
        final JsonRpc20Request request =
                this.jsonRpcClientProvider.createJsonRpc20Request(
                        "suix_resolveNameServiceAddress", Lists.newArrayList(name));
        return this.jsonRpcClientProvider.callAndUnwrapResponse(
                "/suix_resolveNameServiceAddress", request, new TypeToken<String>() {
                }.getType());
    }
}
