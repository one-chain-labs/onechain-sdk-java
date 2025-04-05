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


import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.onechain.models.OneChainApiException;
import io.onechain.models.events.EventFilter;
import io.onechain.models.events.OneChainEvent;
import io.onechain.models.transactions.TransactionBlockEffects;
import io.onechain.models.transactions.TransactionFilter;

/**
 * The interface SubscribeClient.
 *
 * @author chiyu
 * @since 2022.12
 */
public interface SubscribeClient {

  /**
   * Subscribe event disposable.
   *
   * @param eventFilter the event filter
   * @param onNext the on next
   * @param onError the on error
   * @return the disposable
   */
  Disposable subscribeEvent(
          EventFilter eventFilter, Consumer<OneChainEvent> onNext, Consumer<OneChainApiException> onError);

  /**
   * Subscribe transaction disposable.
   *
   * @param transactionFilter the transaction filter
   * @param onNext the on next
   * @param onError the on error
   * @return the disposable
   */
  Disposable subscribeTransaction(
      TransactionFilter transactionFilter,
      Consumer<TransactionBlockEffects> onNext,
      Consumer<OneChainApiException> onError);
}
