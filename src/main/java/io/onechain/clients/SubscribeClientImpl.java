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
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.onechain.jsonrpc.JsonRpc20Request;
import io.onechain.jsonrpc.JsonRpcClientProvider;
import io.onechain.models.OneChainApiException;
import io.onechain.models.events.EventFilter;
import io.onechain.models.events.OneChainEvent;
import io.onechain.models.transactions.TransactionBlockEffects;
import io.onechain.models.transactions.TransactionFilter;

/**
 * The type Event client.
 *
 * @author chiyu
 * @since 2022.12
 */
public class SubscribeClientImpl implements SubscribeClient {

  private final JsonRpcClientProvider jsonRpcClientProvider;

  public SubscribeClientImpl(JsonRpcClientProvider jsonRpcClientProvider) {
    this.jsonRpcClientProvider = jsonRpcClientProvider;
  }

  @Override
  public Disposable subscribeEvent(
          EventFilter eventFilter, Consumer<OneChainEvent> onNext, Consumer<OneChainApiException> onError) {
    final JsonRpc20Request request =
        this.jsonRpcClientProvider.createJsonRpc20Request(
            "suix_subscribeEvent", Lists.newArrayList(eventFilter));
    return this.jsonRpcClientProvider.subscribe(request, onNext, onError);
  }

  @Override
  public Disposable subscribeTransaction(
      TransactionFilter transactionFilter,
      Consumer<TransactionBlockEffects> onNext,
      Consumer<OneChainApiException> onError) {
    final JsonRpc20Request request =
        this.jsonRpcClientProvider.createJsonRpc20Request(
            "suix_subscribeTransaction", Lists.newArrayList(transactionFilter));
    return this.jsonRpcClientProvider.subscribe(request, onNext, onError);
  }
}
