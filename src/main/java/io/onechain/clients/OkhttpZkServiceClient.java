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


import com.google.gson.Gson;
import io.onechain.json.JsonHandler;
import io.onechain.models.FaucetResponse;
import io.onechain.models.OneChainApiException;
import io.onechain.models.ZKLoginDataResponse;
import io.onechain.models.objects.ZKLoginData;
import io.onechain.models.objects.ZkServiceReqeust;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * The type Okhttp faucet client.
 *
 * @author chiyu
 * @since 2023.04
 */
public class OkhttpZkServiceClient implements ZkServiceClient {

  private final OkHttpClient httpClient;

  private final String baseUrl;

  private final JsonHandler jsonHandler;

  /**
   * Instantiates a new Okhttp faucet client.
   *
   * @param baseUrl the base url
   * @param jsonHandler the json handler
   */
  public OkhttpZkServiceClient(String baseUrl, JsonHandler jsonHandler) {
    this.jsonHandler = jsonHandler;
    this.httpClient =
        new OkHttpClient()
            .newBuilder()
            .pingInterval(Duration.ofSeconds(15))
            .writeTimeout(Duration.ofSeconds(15))
            .readTimeout(Duration.ofSeconds(15))
            .build();
    this.baseUrl = baseUrl;
  }

  /**
   * request faucet.
   *
   * @param zkServiceReqeust the address.
   * @return FaucetResponse.
   */
  public CompletableFuture<ZKLoginDataResponse> requestZkService(ZkServiceReqeust zkServiceReqeust) {
    final CompletableFuture<ZKLoginDataResponse> future = new CompletableFuture<>();
    final Request okhttpRequest;
    try {
      final String requestBodyJsonStr =
          new Gson().toJson(zkServiceReqeust);
      final RequestBody requestBody =
          RequestBody.create(requestBodyJsonStr, MediaType.get("application/json; charset=utf-8"));
      okhttpRequest =
          new Request.Builder()
              .url(String.format("%s/v1", this.baseUrl))
              .post(requestBody)
              .build();
    } catch (Throwable throwable) {
      future.completeExceptionally(throwable);
      return future;
    }

    this.httpClient
        .newCall(okhttpRequest)
        .enqueue(
            new Callback() {
              @Override
              public void onFailure(Call call, IOException e) {
                future.completeExceptionally(e);
              }

              @Override
              public void onResponse(Call call, @NotNull Response response) {
                try {
                  ZKLoginDataResponse zkLoginDataResponse = new ZKLoginDataResponse();
                  final ResponseBody responseBody = response.body();
                  if (response.isSuccessful()) {
                    zkLoginDataResponse.setZkLoginData(new Gson().fromJson(Objects.requireNonNull(responseBody).string(),ZKLoginData.class));
                    future.complete(zkLoginDataResponse);
                  } else if (response.code() == 403) {
                    future.completeExceptionally(new OneChainApiException(new HttpForbiddenException()));
                  }else {
                    zkLoginDataResponse.setError(responseBody.string());
                    future.complete(zkLoginDataResponse);
                  }
                } catch (Throwable throwable) {
                  future.completeExceptionally(throwable);
                }
              }
            });

    return future;
  }


}
