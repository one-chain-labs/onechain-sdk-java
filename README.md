

# onechain-sdk-java

onechain-sdk-java is a robust, reactive, type safe Java library for working with Smart Contracts on the
[@OneChainLabs/onechain](https://github.com/one-chain-labs/onechain) network.

This allows you to work with the [@OneChainLabs/onechain](https://github.com/one-chain-labs/onechain) blockchain, without the
additional overhead of having to write your own integration code for the platform in JVM ecosystem.



## How to use it
```java
mvn -DskipTests=true install
```
### Maven

```xml
<dependency>
    <groupId>io.xone.chain</groupId>
    <artifactId>onechain-sdk-java</artifactId>
    <version>1.0.0</version>
</dependency>
```




## How to use it

- devnet-fullnode:https://rpc-devnet.onelabs.cc:443
- devnet-faucet:https://faucet-devnet.onelabs.cc

### Connecting to Sui Network
```java
OneChain oneChain = new OneChain("<full_node_url>","<faucet_url>","<zk_Service_url>","<your_keystore_path>");
```

### New Address
```java
KeyResponse keyRes = sui.newAddress(SignatureScheme.ED25519);
```

### Request Faucet
```java
CompletableFuture<FaucetResponse> faucetRes = oneChain.requestSuiFromFaucet(s);
```

### Writing APIs

#### Move Call
```java
CompletableFuture<TransactionBlockResponse> callRes =
	oneChain.moveCall(
		"0x0a7421363a1f6a82800f7c9340ac02b5905798cb",
		"0x02",
		"pay",
		"split",
		Lists.newArrayList(structType),
		Lists.newArrayList("0x4b89576d18d500194f14c935bc8b297a8e1556f3217e5f125ae3d1c0f13408f9", 10000L),
		null,
		3000000L,
		null,
		null,
		transactionBlockResponseOptions,
		ExecuteTransactionRequestType.WaitForLocalExecution);
```

#### Transfer Object
```java
CompletableFuture<TransactionBlockResponse> res =
	oneChain.transferObjects(
		sender.get(),
		Lists.newArrayList(objects.get(0).getData().getObjectId()),
		recipient.get(),
		null,
		3000000L,
		null,
		null,
		transactionBlockResponseOptions,
		ExecuteTransactionRequestType.WaitForLocalExecution);
```

#### Publish
```java
CompletableFuture<TransactionBlockResponse> res =
	oneChain.publish(
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
```

### Reading APIs

#### Get Owned Objects
```java
CompletableFuture<PaginatedObjectsResponse> res =
	oneChain.getObjectsOwnedByAddress(sender.get(), null, null, null);
```

#### Get Coins
```java
CompletableFuture<PaginatedCoins> res = oneChain.getAllCoins(sender.get(), null, null);
```

#### Get Transaction Block
```java
CompletableFuture<PaginatedTransactionResponse> res =
	oneChain.queryTransactionBlocks(query, null, 10, false);
```

### Event APIs

#### Subscribe
```java
Disposable disposable =
	oneChain.subscribeEvent(eventFilter, System.out::println, System.out::println);

disposable.dispose();
```

For more examples, you can see [SuiIntTests](src/test/java/io/onechain/SuiIntTests.java)


