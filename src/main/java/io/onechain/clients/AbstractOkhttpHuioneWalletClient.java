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


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import io.onechain.hchain.domain.req.common.BaseReq;
import io.onechain.hchain.domain.req.common.HeaderRequest;
import io.onechain.models.OneChainApiException;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The type Okhttp faucet client.
 *
 * @author chiyu
 * @since 2023.04
 */
abstract class AbstractOkhttpHuioneWalletClient {

    protected final OkHttpClient httpClient;

    protected final String baseUrl;

    protected final Gson gson = new Gson();


    /**
     * Instantiates a new Okhttp faucet client.
     *
     * @param baseUrl the base url
     */
    public AbstractOkhttpHuioneWalletClient(String baseUrl) {
        this.httpClient =
                new OkHttpClient()
                        .newBuilder()
                        .pingInterval(Duration.ofSeconds(15))
                        .writeTimeout(Duration.ofSeconds(15))
                        .readTimeout(Duration.ofSeconds(15))
                        .build();
        this.baseUrl = baseUrl;
    }


    public <T> CompletableFuture<T> call(Object request, String url, Type type) {
        return call(request, null, url, type);
    }

    public <T> CompletableFuture<T> call(Object request, HeaderRequest header, String url, Type type) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        final Request okhttpRequest;
        try {
            final String requestBodyJsonStr =
                    gson.toJson(request);
            final RequestBody requestBody =
                    RequestBody.create(requestBodyJsonStr, MediaType.get("application/json; charset=utf-8"));
            Headers.Builder builder = new Headers.Builder();
            Optional.ofNullable(header).ifPresent(hd -> {
                builder.add("access_token", hd.getAccessToken());
                builder.add("token_id", hd.getTokenId());
            });
            okhttpRequest =
                    new Request.Builder()
                            .url(String.format("%s%s", this.baseUrl, url))
                            .post(requestBody)
                            .headers(builder.build())
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
                                    final ResponseBody responseBody = response.body();
                                    if (response.isSuccessful()) {
                                        future.complete(JSON.parseObject(Objects.requireNonNull(responseBody).string(), type));
                                    } else if (response.code() == 403) {
                                        future.completeExceptionally(new OneChainApiException(new HttpForbiddenException()));
                                    }
                                } catch (Throwable throwable) {
                                    future.completeExceptionally(throwable);
                                }
                            }
                        });

        return future;
    }


    public <T> CompletableFuture<T> call(BaseReq request, HeaderRequest header, File file, String url, Type type) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        final Request okhttpRequest;
        try {

            // 构建多部分请求体
            MultipartBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(),
                            RequestBody.create(MediaType.parse("application/octet-stream"), file))
                    .build();

            if (Objects.isNull(header)) {
                header = new HeaderRequest();
            }
            Headers headers = new Headers.Builder()
                    .add("access_token", Optional.ofNullable(header.getAccessToken()).orElse("-"))
                    .add("token_id", Optional.ofNullable(header.getTokenId()).orElse("-"))
                    .build();
            okhttpRequest =
                    new Request.Builder()
                            .url(String.format("%s%s", this.baseUrl, url))
                            .post(body)
                            .headers(headers)
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
                                    final ResponseBody responseBody = response.body();
                                    if (response.isSuccessful()) {
                                        future.complete(gson.fromJson(Objects.requireNonNull(responseBody).string(), type));
                                    } else if (response.code() == 403) {
                                        future.completeExceptionally(new OneChainApiException(new HttpForbiddenException()));
                                    }
                                } catch (Throwable throwable) {
                                    future.completeExceptionally(throwable);
                                }
                            }
                        });

        return future;
    }


}
