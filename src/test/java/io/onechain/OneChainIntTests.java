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
import io.onechain.clients.QueryClient;
import io.onechain.crypto.KeyResponse;
import io.onechain.crypto.SignatureScheme;
import io.onechain.models.FaucetResponse;
import io.onechain.models.coin.Balance;
import io.onechain.models.coin.CoinMetadata;
import io.onechain.models.coin.CoinSupply;
import io.onechain.models.coin.PaginatedCoins;
import io.onechain.models.events.EventFilter;
import io.onechain.models.events.OneChainEvent;
import io.onechain.models.events.PaginatedEvents;
import io.onechain.models.governance.DelegatedStake;
import io.onechain.models.governance.OneChainCommitteeInfo;
import io.onechain.models.governance.SystemStateSummary;
import io.onechain.models.governance.ValidatorsApy;
import io.onechain.models.objects.*;
import io.onechain.models.transactions.*;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The type OneChain int tests.
 *
 * @author grapebaba
 * @since 2022.11
 */
public class OneChainIntTests {

//  private static final String BASE_NODE_URL = "http://localhost:9000";
   private static final String BASE_NODE_URL = "https://rpc-devnet.onelabs.cc:443";

//  private static final String BASE_FAUCET_URL = "http://localhost:9123";
   private static final String BASE_FAUCET_URL = "https://faucet-devnet.onelabs.cc";

//    private static final String TEST_KEY_STORE_PATH =
//        System.getProperty("user.home") + "/.oct/oct_config/oct.keystore";

  private static final String KEY_STORE_PATH =
      Paths.get("src", "integrationTest", "sui.keystore").toAbsolutePath().toString();

  private static final OneChain SUI = new OneChain(BASE_NODE_URL, BASE_FAUCET_URL,null, KEY_STORE_PATH);

  @BeforeAll
  static void setUp() {
    newAddress();
    requestOneChainFromFaucet();
  }

  @AfterAll
  static void tearDown() throws IOException {
    Files.delete(Paths.get("src", "integrationTest", "sui.keystore").toAbsolutePath());
  }

  /** New address. */
  static void newAddress() {
    KeyResponse res1 = SUI.newAddress(SignatureScheme.ED25519);
    System.out.printf("mnemonic+address:%s%n", res1);
    System.out.println();

    KeyResponse res2 = SUI.newAddress(SignatureScheme.ED25519);
    System.out.printf("mnemonic+address:%s%n", res2);
    System.out.println();

    KeyResponse res3 = SUI.newAddress(SignatureScheme.Secp256k1);
    System.out.printf("mnemonic+address:%s%n", res3);
    System.out.println();
  }

  /** Request sui from faucet. */
  static void requestOneChainFromFaucet() {
    SUI.addresses()
        .forEach(
            s -> {
              System.out.printf("address:%s%n", s);
              CompletableFuture<FaucetResponse> res = SUI.requestOneChainFromFaucet(s);

              try {
                TimeUnit.SECONDS.sleep(2);
                System.out.printf("faucet response: %s%n%n", res.get());
              } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                Assertions.fail();
              }
            });
  }

  /**
   * Move call.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test moveCall.")
  void moveCall() throws ExecutionException, InterruptedException {
    ObjectResponseQuery objectResponseQuery = new ObjectResponseQuery();
    final Optional<String> sender =
        SUI.addresses().stream()
            .filter(
                s -> {
                  try {
                    return SUI.getOwnedObjects(s, objectResponseQuery, null, null)
                            .get()
                            .getData()
                            .size()
                        > 0;
                  } catch (InterruptedException | ExecutionException e) {
                    return false;
                  }
                })
            .findFirst();
    if (!sender.isPresent()) {
      Assertions.fail();
    }
    final OneChainObjectResponse oneChainObjectResponse =
        SUI.getOwnedObjects(sender.get(), objectResponseQuery, null, null).get().getData().get(0);
    TransactionBlockResponseOptions transactionBlockResponseOptions =
        new TransactionBlockResponseOptions();
    transactionBlockResponseOptions.setShowEffects(true);
    transactionBlockResponseOptions.setShowEvents(true);
    transactionBlockResponseOptions.setShowInput(true);
    transactionBlockResponseOptions.setShowObjectChanges(true);

    final TypeTag.StructType structType =
        new TypeTag.StructType();
    StructTag structTag = new StructTag();
    structTag.setAddress("0x02");
    structTag.setModule("oct");
    structTag.setName("OCT");
    structType.setStructTag(structTag);
    CompletableFuture<TransactionBlockResponse> res =
        SUI.moveCall(
            sender.get(),
            "0x02",
            "pay",
            "split",
            Lists.newArrayList(structType),
            Lists.newArrayList(oneChainObjectResponse.getData().getObjectId(), 10000L),
            null,
            3000000L,
            null,
            null,
            transactionBlockResponseOptions,
            ExecuteTransactionRequestType.WaitForLocalExecution);

    System.out.println(res.get());
  }

  /**
   * Transfer object.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test transferObject.")
  void transferObjects() throws ExecutionException, InterruptedException {
    ObjectResponseQuery objectResponseQuery = new ObjectResponseQuery();
    objectResponseQuery.setOptions(new ObjectDataOptions());
    final Optional<String> sender =
        SUI.addresses().stream()
            .filter(
                s -> {
                  try {
                    return SUI.getOwnedObjects(s, objectResponseQuery, null, null)
                            .get()
                            .getData()
                            .size()
                        > 1;
                  } catch (InterruptedException | ExecutionException e) {
                    return false;
                  }
                })
            .findFirst();

    if (sender.isPresent()) {
      final Optional<String> recipient =
          SUI.addresses().stream().filter(s -> !s.equals(sender.get())).findFirst();
      if (recipient.isPresent()) {
        List<OneChainObjectResponse> objects =
            SUI.getOwnedObjects(sender.get(), objectResponseQuery, null, null).get().getData();
        TransactionBlockResponseOptions transactionBlockResponseOptions =
            new TransactionBlockResponseOptions();
        transactionBlockResponseOptions.setShowEffects(true);
        transactionBlockResponseOptions.setShowEvents(true);
        transactionBlockResponseOptions.setShowInput(true);
        transactionBlockResponseOptions.setShowObjectChanges(true);
        CompletableFuture<TransactionBlockResponse> res =
            SUI.transferObjects(
                sender.get(),
                Lists.newArrayList(objects.get(0).getData().getObjectId()),
                recipient.get(),
                null,
                3000000L,
                null,
                null,
                transactionBlockResponseOptions,
                ExecuteTransactionRequestType.WaitForLocalExecution);
        CompletableFuture<Object> future = new CompletableFuture<>();
        res.whenComplete(
            (transactionResponse, throwable) -> {
              if (throwable != null) {
                future.complete(throwable);
              } else {
                future.complete(transactionResponse);
              }
            });
        System.out.println(future.get());

      } else {
        Assertions.fail();
      }
    } else {
      Assertions.fail();
    }
  }

  /**
   * Publish.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test publish.")
  void publish() throws ExecutionException, InterruptedException {
    ObjectResponseQuery objectResponseQuery = new ObjectResponseQuery();
    objectResponseQuery.setOptions(new ObjectDataOptions());
    final Optional<String> sender =
        SUI.addresses().stream()
            .filter(
                s -> {
                  try {
                    return SUI.getOwnedObjects(s, objectResponseQuery, null, null)
                            .get()
                            .getData()
                            .size()
                        > 1;
                  } catch (InterruptedException | ExecutionException e) {
                    return false;
                  }
                })
            .findFirst();
    if (!sender.isPresent()) {
      Assertions.fail();
    }
    TransactionBlockResponseOptions transactionBlockResponseOptions =
        new TransactionBlockResponseOptions();
    transactionBlockResponseOptions.setShowEffects(true);
    transactionBlockResponseOptions.setShowEvents(true);
    transactionBlockResponseOptions.setShowInput(true);
    transactionBlockResponseOptions.setShowObjectChanges(true);
    CompletableFuture<TransactionBlockResponse> res =
        SUI.publish(
            sender.get(),
            Lists.newArrayList(
                "oRzrCwYAAAAKAQAUAhQsA0BJBIkBEgWbAWcHggLNAgjPBGAGrwXCAwrxCC0MngnUAQAMAR4B"
                    + "JAIRAh0CHwIlAiYCJwIoAAACAAABDAAAAwQAAQQHAQAAAgYHAAMCDAEIAQQIBAAFBQwABwcCAAkJ"
                    + "BwAAFgABAAEcARUBAAEjFBUBAAIpCwwAAwoNAQEIAxoJCgEIBBoSEwAFDgYHAQIGIREBAQwGJREB"
                    + "AQgHIg4PAAgXBAUBAgkbCxYACwMHAwUIBAgIEAgHAgwBDAkIAggABwgIAAILBQEIAQgHAQgAAQYJ"
                    + "AAEBAgkABwgIAQgHAQgBAgYIBwcICAELBQEJAAEKAgEIBAMHCwUBCQAKCAQKCAQBBggIAQUBCwUB"
                    + "CAECCQAFAQcICAEIBgEJAAELAwEJAAEICQVCT0FSUwRCb2FyB0Rpc3BsYXkITWV0YWRhdGEGT3B0"
                    + "aW9uCVB1Ymxpc2hlcgZTdHJpbmcJVHhDb250ZXh0A1VJRANVcmwMYWRkX211bHRpcGxlA2FnZQVi"
                    + "b2FycwVidXllcgVjbGFpbQdjcmVhdG9yC2Rlc2NyaXB0aW9uB2Rpc3BsYXkLZHVtbXlfZmllbGQI"
                    + "ZnVsbF91cmwCaWQHaW1nX3VybARpbml0E2lzX29uZV90aW1lX3dpdG5lc3MIbWV0YWRhdGEEbmFt"
                    + "ZQNuZXcVbmV3X3Vuc2FmZV9mcm9tX2J5dGVzBG5vbmUGb2JqZWN0Bm9wdGlvbgdwYWNrYWdlBXBy"
                    + "aWNlD3B1YmxpY190cmFuc2ZlcgZzZW5kZXIEc29tZQZzdHJpbmcIdHJhbnNmZXIKdHhfY29udGV4"
                    + "dAV0eXBlcwN1cmwEdXRmOAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                    + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgMI"
                    + "AAAAAAAAAAAKAgUEbmFtZQoCDAtkZXNjcmlwdGlvbgoCCAdpbWdfdXJsCgIIB2NyZWF0b3IKAgYF"
                    + "cHJpY2UKAgwLcHJvamVjdF91cmwKAgQDYWdlCgIGBWJ1eWVyCgIJCGZ1bGxfdXJsCgIODWVzY2Fw"
                    + "ZV9zeW50YXgKAgcGe25hbWV9CgI7OlVuaXF1ZSBCb2FyIGZyb20gdGhlIEJvYXJzIGNvbGxlY3Rp"
                    + "b24gd2l0aCB7bmFtZX0gYW5kIHtpZH0KAiEgaHR0cHM6Ly9nZXQtYS1ib2FyLmNvbS97aW1nX3Vy"
                    + "bH0KAgoJe2NyZWF0b3J9CgIIB3twcmljZX0KAhgXaHR0cHM6Ly9nZXQtYS1ib2FyLmNvbS8KAg8O"
                    + "e21ldGFkYXRhLmFnZX0KAggHe2J1eWVyfQoCCwp7ZnVsbF91cmx9CgIJCFx7bmFtZVx9CgIKCWZp"
                    + "cnN0LnBuZwoCCwpGaXJzdCBCb2FyCgImJUZpcnN0IEJvYXIgZnJvbSB0aGUgQm9hcnMgY29sbGVj"
                    + "dGlvbiEKAgYFQ2hyaXMKAiAfaHR0cHM6Ly9nZXQtYS1ib2FyLmZ1bGx1cmwuY29tLwACARIBAQIJ"
                    + "FAgGFQgEGQgEEAgEDwsDAQgEIAsDAQgEGAgCDQUTCAkCAgELAwAAAAACXw4AOAAEBAUICwEBBwAn"
                    + "CwAKATgBDAMOAwoBOAIMAg0CBwERAwcCEQMHAxEDBwQRAwcFEQMHBhEDBwcRAwcIEQMHCREDBwoR"
                    + "A0AMCgAAAAAAAAAHCxEDBwwRAwcNEQMHDhEDBw8RAwcQEQMHEREDBxIRAwcTEQMHFBEDQAwKAAAA"
                    + "AAAAADgDCwIKAS4RCjgECwMKAS4RCjgFCgERBgcVEQMHFhEDBxcRAwcYEQM4BjgHBgoAAAAAAAAA"
                    + "EgIKAS4RCgcZEQwSAQsBLhEKOAgCAA=="),
            Lists.newArrayList(
                "0x0000000000000000000000000000000000000000000000000000000000000001",
                "0x0000000000000000000000000000000000000000000000000000000000000002"),
            null,
            30000000L,
            null,
            null,
            transactionBlockResponseOptions,
            ExecuteTransactionRequestType.WaitForLocalExecution);

    System.out.println(res.get());
  }



  /**
   * Subscribe transaction.
   *
   * @throws InterruptedException the interrupted exception
   * @throws ExecutionException the execution exception
   */
  @Test
  @DisplayName("Test subscribeTransaction.")
  void subscribeTransaction() throws InterruptedException, ExecutionException {
    ObjectResponseQuery objectResponseQuery = new ObjectResponseQuery();
    final Optional<String> sender =
        SUI.addresses().stream()
            .filter(
                s -> {
                  try {
                    return SUI.getOwnedObjects(s, objectResponseQuery, null, null)
                            .get()
                            .getData()
                            .size()
                        > 1;
                  } catch (InterruptedException | ExecutionException e) {
                    return false;
                  }
                })
            .findFirst();
    if (!sender.isPresent()) {
      Assertions.fail();
    }

    TransactionFilter.FromAddressFilter transactionFilter = new TransactionFilter.FromAddressFilter();
    transactionFilter.setFromAddress(sender.get());

    final OneChainObjectResponse oneChainObjectResponseObjectResponse =
        SUI.getOwnedObjects(sender.get(), objectResponseQuery, null, null).get().getData().get(0);
    TransactionBlockResponseOptions transactionBlockResponseOptions =
        new TransactionBlockResponseOptions();
    transactionBlockResponseOptions.setShowEffects(true);
    transactionBlockResponseOptions.setShowEvents(true);
    transactionBlockResponseOptions.setShowInput(true);
    transactionBlockResponseOptions.setShowObjectChanges(true);

    final TypeTag.StructType structType =
        new TypeTag.StructType();
   StructTag structTag = new StructTag();
    structTag.setAddress("0x02");
    structTag.setModule("oct");
    structTag.setName("OCT");
    structType.setStructTag(structTag);

    Disposable disposable =
        SUI.subscribeTransaction(transactionFilter, System.out::println, System.out::println);

    SUI.moveCall(
        sender.get(),
        "0x02",
        "pay",
        "split",
        Lists.newArrayList(structType),
        Lists.newArrayList(oneChainObjectResponseObjectResponse.getData().getObjectId(), 10000L),
        null,
        3000000L,
        null,
        null,
        transactionBlockResponseOptions,
        ExecuteTransactionRequestType.WaitForLocalExecution);

    TimeUnit.SECONDS.sleep(5);

    disposable.dispose();
  }


  /**
   * Gets all coins.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getAllCoins.")
  void getAllCoins() throws ExecutionException, InterruptedException {
    ObjectResponseQuery objectResponseQuery = new ObjectResponseQuery();
    objectResponseQuery.setOptions(new ObjectDataOptions());
    final Optional<String> sender =
        SUI.addresses().stream()
            .filter(
                s -> {
                  try {
                    return SUI.getOwnedObjects(s, objectResponseQuery, null, null)
                            .get()
                            .getData()
                            .size()
                        > 1;
                  } catch (InterruptedException | ExecutionException e) {
                    return false;
                  }
                })
            .findFirst();
    if (!sender.isPresent()) {
      Assertions.fail();
    }
    CompletableFuture<PaginatedCoins> res = SUI.getAllCoins(sender.get(), null, null);
    System.out.printf("paginated coins:%s%n", res.get());
  }

  /**
   * Gets balance.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getBalance.")
  void getBalance() throws ExecutionException, InterruptedException {
    ObjectResponseQuery objectResponseQuery = new ObjectResponseQuery();
    objectResponseQuery.setOptions(new ObjectDataOptions());
    final Optional<String> sender =
        SUI.addresses().stream()
            .filter(
                s -> {
                  try {
                    return SUI.getOwnedObjects(s, objectResponseQuery, null, null)
                            .get()
                            .getData()
                            .size()
                        > 1;
                  } catch (InterruptedException | ExecutionException e) {
                    return false;
                  }
                })
            .findFirst();
    if (!sender.isPresent()) {
      Assertions.fail();
    }
    CompletableFuture<Balance> res = SUI.getBalance(sender.get(), null);
    System.out.printf("balance:%s%n", res.get());
  }

  /**
   * Transfer coin.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @SuppressWarnings("checkstyle:CommentsIndentation")
  @Test
  @DisplayName("Test transferOneChain.")
  void transferOneChain() throws ExecutionException, InterruptedException {
    ObjectResponseQuery objectResponseQuery = new ObjectResponseQuery();
    objectResponseQuery.setOptions(new ObjectDataOptions());
    final Optional<String> sender =
        SUI.addresses().stream()
            .filter(
                s -> {
                  try {
                    return SUI.getOwnedObjects(s, objectResponseQuery, null, null)
                            .get()
                            .getData()
                            .size()
                        > 1;
                  } catch (InterruptedException | ExecutionException e) {
                    return false;
                  }
                })
            .findFirst();
    if (!sender.isPresent()) {
      Assertions.fail();
    }

    final Optional<String> recipient =
        SUI.addresses().stream().filter(s -> !s.equals(sender.get())).findFirst();
    if (!recipient.isPresent()) {
      Assertions.fail();
    }

    TransactionBlockResponseOptions transactionBlockResponseOptions =
        new TransactionBlockResponseOptions();
    transactionBlockResponseOptions.setShowEffects(true);
    transactionBlockResponseOptions.setShowEvents(true);
    transactionBlockResponseOptions.setShowInput(true);
    transactionBlockResponseOptions.setShowObjectChanges(true);
    CompletableFuture<TransactionBlockResponse> res =
        SUI.transferCoin(
            sender.get(),
            null,
            recipient.get(),
            20000L,
            null,
            3000000L,
            null,
            null,
            transactionBlockResponseOptions,
            ExecuteTransactionRequestType.WaitForLocalExecution);

    System.out.println(res.get());
  }

  /**
   * Merge coin.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test mergeCoin.")
  void mergeCoin() throws ExecutionException, InterruptedException {
    ObjectResponseQuery objectResponseQuery = new ObjectResponseQuery();
    final Optional<String> sender =
        SUI.addresses().stream()
            .filter(
                s -> {
                  try {
                    return SUI.getOwnedObjects(s, objectResponseQuery, null, null)
                            .get()
                            .getData()
                            .size()
                        > 3;
                  } catch (InterruptedException | ExecutionException e) {
                    return false;
                  }
                })
            .findFirst();
    if (!sender.isPresent()) {
      Assertions.fail();
    }

    TransactionBlockResponseOptions transactionBlockResponseOptions =
        new TransactionBlockResponseOptions();
    transactionBlockResponseOptions.setShowEffects(true);
    transactionBlockResponseOptions.setShowEvents(true);
    transactionBlockResponseOptions.setShowInput(true);
    transactionBlockResponseOptions.setShowObjectChanges(true);

    String dest =
        SUI.getOwnedObjects(sender.get(), objectResponseQuery, null, null)
            .get()
            .getData()
            .get(0)
            .getData()
            .getObjectId();
    List<String> source =
        SUI.getOwnedObjects(sender.get(), objectResponseQuery, null, null).get().getData()
            .subList(1, 2).stream()
            .map(oneChainObjectResponse -> oneChainObjectResponse.getData().getObjectId())
            .collect(Collectors.toList());
    CompletableFuture<TransactionBlockResponse> res =
        SUI.mergeCoin(
            sender.get(),
            dest,
            source,
            null,
            3000000L,
            null,
            null,
            transactionBlockResponseOptions,
            ExecuteTransactionRequestType.WaitForLocalExecution);

    System.out.println(res.get());
  }

  /**
   * Gets total transaction number.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getTotalTransactionBlocks.")
  void getTotalTransactionBlocks() throws ExecutionException, InterruptedException {
    CompletableFuture<Long> res = SUI.getTotalTransactionBlocks();
    System.out.printf("total transaction blocks:%d%n", res.get());
  }

  /**
   * Gets objects owned by address.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getOwnedObjects.")
  void getObjectsOwnedByAddress() throws ExecutionException, InterruptedException {
    final Optional<String> sender =
        SUI.addresses().stream()
            .filter(
                s -> {
                  try {
                    return SUI.getOwnedObjects(s, new ObjectResponseQuery(), null, null)
                            .get()
                            .getData()
                            .size()
                        > 1;
                  } catch (InterruptedException | ExecutionException e) {
                    return false;
                  }
                })
            .findFirst();
    if (!sender.isPresent()) {
      Assertions.fail();
    }
    System.out.println(sender.get());
    CompletableFuture<PaginatedObjectsResponse> res =
        SUI.getOwnedObjects(sender.get(), null, null, null);
    System.out.printf("paginated objects:%s%n", res.get());
  }

  /**
   * Query transaction blocks.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test queryTransactionBlocks.")
  void queryTransactionBlocks() throws ExecutionException, InterruptedException {
    TransactionBlockResponseQuery query = new TransactionBlockResponseQuery();
    CompletableFuture<PaginatedTransactionBlockResponse> res =
        SUI.queryTransactionBlocks(query, null, 10, false);

    System.out.printf("paginated transaction blocks:%s%n", res.get());
  }

  /**
   * Gets transaction block.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getTransactionBlock.")
  void getTransactionBlock() throws ExecutionException, InterruptedException {
    TransactionBlockResponseQuery query = new TransactionBlockResponseQuery();
    CompletableFuture<PaginatedTransactionBlockResponse> res =
        SUI.queryTransactionBlocks(query, null, 10, false);

    TransactionBlockResponseOptions options = new TransactionBlockResponseOptions();
    options.setShowInput(true);
    options.setShowEffects(true);
    CompletableFuture<TransactionBlockResponse> res1 =
        SUI.getTransactionBlock(res.get().getData().get(2).getDigest(), options);
    System.out.printf("transaction block:%s%n", res1.get());
  }

  /**
   * Multi get transaction blocks.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test multiGetTransactionBlocks.")
  void multiGetTransactionBlocks() throws ExecutionException, InterruptedException {
    TransactionBlockResponseQuery query = new TransactionBlockResponseQuery();
    CompletableFuture<PaginatedTransactionBlockResponse> res =
        SUI.queryTransactionBlocks(query, null, 10, false);

    TransactionBlockResponseOptions options = new TransactionBlockResponseOptions();
    options.setShowInput(true);
    options.setShowEffects(true);
    CompletableFuture<List<TransactionBlockResponse>> res1 =
        SUI.multiGetTransactionBlocks(
            Lists.newArrayList(
                res.get().getData().get(0).getDigest(), res.get().getData().get(1).getDigest()),
            options);
    System.out.printf("transaction blocks:%s%n", res1.get());
  }

  /**
   * Multi get objects.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test multiGetObjects.")
  void multiGetObjects() throws ExecutionException, InterruptedException {
    ObjectResponseQuery objectResponseQuery = new ObjectResponseQuery();
    objectResponseQuery.setOptions(new ObjectDataOptions());
    final Optional<String> sender =
        SUI.addresses().stream()
            .filter(
                s -> {
                  try {
                    return SUI.getOwnedObjects(s, objectResponseQuery, null, null)
                            .get()
                            .getData()
                            .size()
                        > 1;
                  } catch (InterruptedException | ExecutionException e) {
                    return false;
                  }
                })
            .findFirst();
    if (!sender.isPresent()) {
      Assertions.fail();
    }
    List<String> objects =
        SUI.getOwnedObjects(sender.get(), objectResponseQuery, null, null).get().getData().stream()
            .map(oneChainObjectResponse -> oneChainObjectResponse.getData().getObjectId())
            .collect(Collectors.toList());
    CompletableFuture<List<OneChainObjectResponse>> res1 =
        SUI.multiGetObjects(objects, new ObjectDataOptions());
    System.out.printf("object responses:%s%n", res1.get());
  }


  /**
   * Gets validators apy.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getValidatorsApy.")
  void getValidatorsApy() throws ExecutionException, InterruptedException {
    CompletableFuture<ValidatorsApy> res = SUI.getValidatorsApy();
    System.out.println(res.get());
  }

  /**
   * Gets total supply.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getTotalSupply.")
  void getTotalSupply() throws ExecutionException, InterruptedException {
    CompletableFuture<CoinSupply> res = SUI.getTotalSupply("0x2::oct::OCT");
    System.out.println(res.get());
  }

  /**
   * Gets stakes by ids.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getStakesByIds.")
  void getStakesByIds() throws ExecutionException, InterruptedException {
    ObjectResponseQuery objectResponseQuery = new ObjectResponseQuery();
    objectResponseQuery.setOptions(new ObjectDataOptions());
    final Optional<String> sender =
        SUI.addresses().stream()
            .filter(
                s -> {
                  try {
                    return SUI.getOwnedObjects(s, objectResponseQuery, null, null)
                            .get()
                            .getData()
                            .size()
                        > 1;
                  } catch (InterruptedException | ExecutionException e) {
                    return false;
                  }
                })
            .findFirst();
    if (!sender.isPresent()) {
      Assertions.fail();
    }
    List<String> objects =
        SUI.getOwnedObjects(sender.get(), objectResponseQuery, null, null).get().getData().stream()
            .map(oneChainObjectData -> oneChainObjectData.getData().getObjectId())
            .collect(Collectors.toList());
    CompletableFuture<List<DelegatedStake>> res = SUI.getStakesByIds(Lists.newArrayList());
    res.whenComplete(
        (delegatedStake, throwable) -> {
          if (throwable != null) {
            System.out.println(throwable.getMessage());
          } else {
            System.out.println(delegatedStake);
          }
        });
  }

  /**
   * Gets stakes.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getStakes.")
  void getStakes() throws ExecutionException, InterruptedException {
    ObjectResponseQuery objectResponseQuery = new ObjectResponseQuery();
    objectResponseQuery.setOptions(new ObjectDataOptions());
    final Optional<String> sender =
        SUI.addresses().stream()
            .filter(
                s -> {
                  try {
                    return SUI.getOwnedObjects(s, objectResponseQuery, null, null)
                            .get()
                            .getData()
                            .size()
                        > 1;
                  } catch (InterruptedException | ExecutionException e) {
                    return false;
                  }
                })
            .findFirst();
    if (!sender.isPresent()) {
      Assertions.fail();
    }
    CompletableFuture<List<DelegatedStake>> res = SUI.getStakes(sender.get());
    System.out.println(res.get());
  }

  /**
   * Gets latest onechain system state.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getLatestOneChainSystemState.")
  void getLatestOneChainSystemState() throws ExecutionException, InterruptedException {
    CompletableFuture<SystemStateSummary> res = SUI.getLatestOneChainSystemState();
    System.out.println(res.get());
  }

  /**
   * Gets committee info.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getLatestOneChainSystemState.")
  void getCommitteeInfo() throws ExecutionException, InterruptedException {
    CompletableFuture<OneChainCommitteeInfo> res = SUI.getCommitteeInfo(BigInteger.ONE);
    System.out.println(res.get());
  }

  /**
   * Gets coins.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getCoins.")
  void getCoins() throws ExecutionException, InterruptedException {
    ObjectResponseQuery objectResponseQuery = new ObjectResponseQuery();
    objectResponseQuery.setOptions(new ObjectDataOptions());
    final Optional<String> sender =
        SUI.addresses().stream()
            .filter(
                s -> {
                  try {
                    return SUI.getOwnedObjects(s, objectResponseQuery, null, 10)
                            .get()
                            .getData()
                            .size()
                        > 1;
                  } catch (InterruptedException | ExecutionException e) {
                    return false;
                  }
                })
            .findFirst();
    if (!sender.isPresent()) {
      Assertions.fail();
    }
    CompletableFuture<PaginatedCoins> res = SUI.getCoins(sender.get(), null, null, 10);
    System.out.println(res.get());
  }

  /**
   * Gets coin metadata.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getCoinMetadata.")
  void getCoinMetadata() throws ExecutionException, InterruptedException {
    CompletableFuture<CoinMetadata> res = SUI.getCoinMetadata(QueryClient.DEFAULT_COIN_TYPE);
    System.out.println(res.get());
  }

  /**
   * Gets all balances.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getAllBalances.")
  void getAllBalances() throws ExecutionException, InterruptedException {
    ObjectResponseQuery objectResponseQuery = new ObjectResponseQuery();
    objectResponseQuery.setOptions(new ObjectDataOptions());
    final Optional<String> sender =
        SUI.addresses().stream()
            .filter(
                s -> {
                  try {
                    return SUI.getOwnedObjects(s, objectResponseQuery, null, null)
                            .get()
                            .getData()
                            .size()
                        > 1;
                  } catch (InterruptedException | ExecutionException e) {
                    return false;
                  }
                })
            .findFirst();
    if (!sender.isPresent()) {
      Assertions.fail();
    }
    CompletableFuture<List<Balance>> res = SUI.getAllBalances(sender.get());
    System.out.println(res.get());
  }

  /**
   * Gets checkpoint.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getCheckpoint.")
  void getCheckpoint() throws ExecutionException, InterruptedException {
    CompletableFuture<Checkpoint> res = SUI.getCheckpoint(BigInteger.ZERO.toString());
    System.out.println(res.get());
  }

  /**
   * Gets checkpoints.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getCheckpoints.")
  void getCheckpoints() throws ExecutionException, InterruptedException {
    CompletableFuture<PaginatedCheckpoint> res = SUI.getCheckpoints(null, 10, true);
    System.out.println(res.get());
  }

  /**
   * Gets events.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getEvents.")
  void getEvents() throws ExecutionException, InterruptedException {
    EventFilter.AllEventFilter eventFilter = new EventFilter.AllEventFilter();
    CompletableFuture<PaginatedEvents> res = SUI.queryEvents(eventFilter, null, 10, false);
    CompletableFuture<List<OneChainEvent>> res1 =
        SUI.getEvents(res.get().getData().get(0).getId().getTxDigest());
    System.out.println(res1.get());
  }

  /**
   * Gets latest checkpoint sequence number.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getLatestCheckpointSequenceNumber.")
  void getLatestCheckpointSequenceNumber() throws ExecutionException, InterruptedException {
    CompletableFuture<BigInteger> res = SUI.getLatestCheckpointSequenceNumber();
    System.out.println(res.get());
  }

  /**
   * Gets normalized move modules by package.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getLatestCheckpointSequenceNumber.")
  void getNormalizedMoveModulesByPackage() throws ExecutionException, InterruptedException {
    CompletableFuture<Map<String, MoveNormalizedModule>> res =
        SUI.getNormalizedMoveModulesByPackage("0x2");
    System.out.println(res.get());
  }

  /**
   * Gets normalized move struct.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getNormalizedMoveStruct.")
  void getNormalizedMoveStruct() throws ExecutionException, InterruptedException {
    CompletableFuture<MoveNormalizedStruct> res = SUI.getNormalizedMoveStruct("0x2", "bag", "Bag");
    System.out.println(res.get());
  }

  /**
   * Gets normalized move module.
   *
   * @throws ExecutionException the execution exception
   * @throws InterruptedException the interrupted exception
   */
  @Test
  @DisplayName("Test getNormalizedMoveModule.")
  void getNormalizedMoveModule() throws ExecutionException, InterruptedException {
    CompletableFuture<MoveNormalizedModule> res = SUI.getNormalizedMoveModule("0x2", "bag");
    System.out.println(res.get());
  }

  @Test
  @DisplayName("Test getMoveFunctionArgTypes.")
  void getMoveFunctionArgTypes() throws ExecutionException, InterruptedException {
    CompletableFuture<List<MoveFunctionArgType>> res =
        SUI.getMoveFunctionArgTypes("0x2", "bag", "add");
    System.out.println(res.get());
  }

  @Test
  @DisplayName("Test getNormalizedMoveFunction.")
  void getNormalizedMoveFunction() throws ExecutionException, InterruptedException {
    CompletableFuture<MoveNormalizedFunction> res =
        SUI.getNormalizedMoveFunction("0x2", "bag", "add");
    System.out.println(res.get());
  }
}