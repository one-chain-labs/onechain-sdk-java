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

package io.onechain.json;


import com.google.gson.*;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import io.onechain.jsonrpc.JsonRpc20Request;
import io.onechain.jsonrpc.JsonRpc20Response;
import io.onechain.jsonrpc.JsonRpc20Response.Error.ErrorCode;
import io.onechain.jsonrpc.JsonRpc20WSResponse;
import io.onechain.models.FaucetResponse;
import io.onechain.models.events.EventFilter;
import io.onechain.models.events.EventFilter.PackageEventFilter;
import io.onechain.models.governance.Validator;
import io.onechain.models.objects.*;
import io.onechain.models.objects.MoveFunctionArgType.ObjectValueKindMoveFunctionArgType;
import io.onechain.models.objects.MoveFunctionArgType.PureFunctionMoveFunctionArgType;
import io.onechain.models.objects.MoveNormalizedType.*;
import io.onechain.models.objects.ObjectChange.ObjectChangeType;
import io.onechain.models.transactions.*;
import io.onechain.models.transactions.Argument.NestedResult;
import io.onechain.models.transactions.Argument.NestedResultArgument;
import io.onechain.models.transactions.Command.*;
import io.onechain.models.transactions.TypeTag.StructType;
import io.onechain.models.transactions.TypeTag.VectorType;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * The type Gson json handler.
 *
 * @author chiyu
 * @since 2022.11
 */
public class GsonJsonHandler implements JsonHandler {

    /** The type Error code deserializer. */
    public static class ErrorCodeDeserializer implements JsonDeserializer<ErrorCode> {

        @Override
        public ErrorCode deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return JsonRpc20Response.Error.ErrorCode.valueOfCode(json.getAsInt());
        }
    }

    /** The type Sui data deserializer. */
    public class SuiRawDataDeserializer implements JsonDeserializer<OneChainRawData> {

        @Override
        public OneChainRawData deserialize(
                JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if ("package".equals(json.getAsJsonObject().get("dataType").getAsString())) {
                return gson.fromJson(json, OneChainRawData.PackageObject.class);
            }
            if ("moveObject".equals(json.getAsJsonObject().get("dataType").getAsString())) {
                return gson.fromJson(json, OneChainRawData.MoveObject.class);
            }
            return null;
        }
    }

    /** The type Sui parsed data deserializer. */
    public class SuiParsedDataDeserializer implements JsonDeserializer<OneChainParsedData> {

        @Override
        public OneChainParsedData deserialize(
                JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if ("package".equals(json.getAsJsonObject().get("dataType").getAsString())) {
                return gson.fromJson(json, OneChainParsedData.PackageObject.class);
            }
            if ("moveObject".equals(json.getAsJsonObject().get("dataType").getAsString())) {
                return gson.fromJson(json, OneChainParsedData.MoveObject.class);
            }
            return null;
        }
    }

    /** The type Sui argument deserializer. */
    public class SuiArgumentDeserializer implements JsonDeserializer<Argument> {

        @Override
        public Argument deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json.isJsonObject()) {
                if (json.getAsJsonObject().get("Input") != null
                        && !json.getAsJsonObject().get("Input").isJsonNull()) {
                    return gson.fromJson(json, Argument.InputArgument.class);
                }
                if (json.getAsJsonObject().get("Result") != null
                        && !json.getAsJsonObject().get("Result").isJsonNull()) {
                    return gson.fromJson(json, Argument.ResultArgument.class);
                }
                if (json.getAsJsonObject().get("NestedResult") != null
                        && !json.getAsJsonObject().get("NestedResult").isJsonNull()) {
                    Argument.NestedResult nestedResult = new NestedResult();
                    nestedResult.setField0(
                            json.getAsJsonObject().getAsJsonArray("NestedResult").get(0).getAsShort());
                    nestedResult.setField1(
                            json.getAsJsonObject().getAsJsonArray("NestedResult").get(1).getAsShort());

                    NestedResultArgument nestedResultArgument = new NestedResultArgument();
                    nestedResultArgument.setNestedResult(nestedResult);
                    return nestedResultArgument;
                }
            } else {
                return Argument.GasCoinArgument.GasCoin;
            }
            return null;
        }
    }

    /** The type Object change deserializer. */
    public class ObjectChangeDeserializer implements JsonDeserializer<ObjectChange> {

        @Override
        public ObjectChange deserialize(
                JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json.getAsJsonObject().get("type") != null
                    && !json.getAsJsonObject().get("type").isJsonNull()) {
                if (ObjectChangeType.published
                        .toString()
                        .equals(json.getAsJsonObject().get("type").getAsString())) {
                    return gson.fromJson(json, ObjectChange.ObjectChangePublished.class);
                }
                if (ObjectChangeType.transferred
                        .toString()
                        .equals(json.getAsJsonObject().get("type").getAsString())) {
                    return gson.fromJson(json, ObjectChange.ObjectChangeTransferred.class);
                }
                if (ObjectChangeType.mutated
                        .toString()
                        .equals(json.getAsJsonObject().get("type").getAsString())) {
                    return gson.fromJson(json, ObjectChange.ObjectChangeMutated.class);
                }
                if (ObjectChangeType.deleted
                        .toString()
                        .equals(json.getAsJsonObject().get("type").getAsString())) {
                    return gson.fromJson(json, ObjectChange.ObjectChangeDeleted.class);
                }
                if (ObjectChangeType.wrapped
                        .toString()
                        .equals(json.getAsJsonObject().get("type").getAsString())) {
                    return gson.fromJson(json, ObjectChange.ObjectChangeWrapped.class);
                }
                if (ObjectChangeType.created
                        .toString()
                        .equals(json.getAsJsonObject().get("type").getAsString())) {
                    return gson.fromJson(json, ObjectChange.ObjectChangeCreated.class);
                }
            }
            return null;
        }
    }

    /** The type Sui object owner deserializer. */
    public class SuiObjectOwnerDeserializer implements JsonDeserializer<OneChainObjectOwner> {

        @Override
        public OneChainObjectOwner deserialize(
                JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json.isJsonObject()) {
                if (json.getAsJsonObject().get("AddressOwner") != null
                        && !json.getAsJsonObject().get("AddressOwner").isJsonNull()) {
                    return gson.fromJson(json, OneChainObjectOwner.AddressOwner.class);
                }
                if (json.getAsJsonObject().get("ObjectOwner") != null
                        && !json.getAsJsonObject().get("ObjectOwner").isJsonNull()) {
                    return gson.fromJson(json, OneChainObjectOwner.ObjectOwner.class);
                }
                if (json.getAsJsonObject().get("Shared") != null
                        && !json.getAsJsonObject().get("Shared").isJsonNull()) {
                    return gson.fromJson(json, OneChainObjectOwner.SharedOwner.class);
                }
            } else {
                return OneChainObjectOwner.StringOneChainObjectOwner.Immutable;
            }

            return null;
        }
    }

    /** The type Move call deserializer. */
    public class MoveCallDeserializer implements JsonDeserializer<MoveCall> {

        @Override
        public MoveCall deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            MoveCall moveCall = new MoveCall();
            List<Argument> arguments =
                    gson.fromJson(
                            json.getAsJsonObject().get("arguments"),
                            new com.google.common.reflect.TypeToken<List<Argument>>() {
                            }.getType());
            moveCall.setArguments(arguments);
            moveCall.setModule(json.getAsJsonObject().get("module").getAsString());
            moveCall.setFunction(json.getAsJsonObject().get("function").getAsString());
            List<String> typeArguments =
                    gson.fromJson(
                            json.getAsJsonObject().get("type_arguments"),
                            new com.google.common.reflect.TypeToken<List<String>>() {
                            }.getType());
            moveCall.setTypeArguments(typeArguments);
            String suiPackage = json.getAsJsonObject().get("package").getAsString();
            moveCall.setSuiPackage(suiPackage);
            return moveCall;
        }
    }

    /** The type Sui command deserializer. */
    public class SuiCommandDeserializer implements JsonDeserializer<Command> {

        @Override
        public Command deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json.getAsJsonObject().get("MoveCall") != null
                    && !json.getAsJsonObject().get("MoveCall").isJsonNull()) {
                return gson.fromJson(json, MoveCallCommand.class);
            }
            if (json.getAsJsonObject().get("TransferObjects") != null
                    && !json.getAsJsonObject().get("TransferObjects").isJsonNull()) {
                Command.TransferObjects transferObjects = new TransferObjects();
                List<Argument> field0 =
                        gson.fromJson(
                                json.getAsJsonObject().getAsJsonArray("TransferObjects").get(0),
                                new com.google.common.reflect.TypeToken<List<Argument>>() {
                                }.getType());
                Argument field1 =
                        gson.fromJson(
                                json.getAsJsonObject().getAsJsonArray("TransferObjects").get(1),
                                new com.google.common.reflect.TypeToken<Argument>() {
                                }.getType());
                transferObjects.setField0(field0);
                transferObjects.setField1(field1);
                TransferObjectsCommand transferObjectsCommand = new TransferObjectsCommand();
                transferObjectsCommand.setTransferObjects(transferObjects);
                return transferObjectsCommand;
            }
            if (json.getAsJsonObject().get("SplitCoin") != null
                    && !json.getAsJsonObject().get("SplitCoin").isJsonNull()) {
                Command.SplitCoin splitCoin = new SplitCoin();
                Argument field0 =
                        gson.fromJson(
                                json.getAsJsonObject().getAsJsonArray("SplitCoin").get(0),
                                new com.google.common.reflect.TypeToken<Argument>() {
                                }.getType());
                splitCoin.setField0(field0);
                splitCoin.setAddress(
                        json.getAsJsonObject().getAsJsonArray("SplitCoin").get(1).getAsString());
                SplitCoinCommand splitCoinCommand = new SplitCoinCommand();
                splitCoinCommand.setSplitCoin(splitCoin);
                return splitCoinCommand;
            }
            if (json.getAsJsonObject().get("MergeCoins") != null
                    && !json.getAsJsonObject().get("MergeCoins").isJsonNull()) {
                Command.MergeCoins mergeCoins = new MergeCoins();
                Argument field0 =
                        gson.fromJson(
                                json.getAsJsonObject().getAsJsonArray("MergeCoins").get(0),
                                new com.google.common.reflect.TypeToken<Argument>() {
                                }.getType());
                List<Argument> field1 =
                        gson.fromJson(
                                json.getAsJsonObject().getAsJsonArray("MergeCoins").get(1),
                                new com.google.common.reflect.TypeToken<List<Argument>>() {
                                }.getType());
                mergeCoins.setField0(field0);
                mergeCoins.setField1(field1);

                MergeCoinsCommand mergeCoinsCommand = new MergeCoinsCommand();
                mergeCoinsCommand.setMergeCoins(mergeCoins);
                return mergeCoinsCommand;
            }
            if (json.getAsJsonObject().get("Publish") != null
                    && !json.getAsJsonObject().get("Publish").isJsonNull()) {
                return gson.fromJson(json, PublishCommand.class);
            }
            if (json.getAsJsonObject().get("MakeMoveVec") != null
                    && !json.getAsJsonObject().get("MakeMoveVec").isJsonNull()) {
                Command.MakeMoveVec makeMoveVec = new MakeMoveVec();
                List<Argument> field1 =
                        gson.fromJson(
                                json.getAsJsonObject().getAsJsonArray("MakeMoveVec").get(1),
                                new com.google.common.reflect.TypeToken<List<Argument>>() {
                                }.getType());
                makeMoveVec.setField0(
                        json.getAsJsonObject().getAsJsonArray("MakeMoveVec").get(0).getAsString());
                makeMoveVec.setField1(field1);

                MakeMoveVecCommand makeMoveVecCommand = new MakeMoveVecCommand();
                makeMoveVecCommand.setMakeMoveVec(makeMoveVec);
                return makeMoveVecCommand;
            }
            return null;
        }
    }

    /** The type Transaction kind deserializer. */
    public class TransactionKindInputDeserializer implements JsonDeserializer<TransactionKind.Input> {

        @Override
        public TransactionKind.Input deserialize(
                JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonElement typeElement = json.getAsJsonObject().get("type");
            if (typeElement == null) {
                return null;
            }
            String type = typeElement.getAsString();
            if (StringUtils.equalsIgnoreCase(type, TransactionKind.InputType.object.name())) {
                return gson.fromJson(json, TransactionKind.ObjectInput.class);
            }
            if (StringUtils.equalsIgnoreCase(type, TransactionKind.InputType.pure.name())) {
                return gson.fromJson(json, TransactionKind.PureInput.class);
            }
            return null;
        }
    }

    /** The type Move module serializer. */
    public static class MoveModuleSerializer implements JsonSerializer<MoveModule> {

        @Override
        public JsonElement serialize(MoveModule src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("package", src.getSuiPackage());
            jsonObject.addProperty("module", src.getModule());
            return jsonObject;
        }
    }

    /** The type Move function serializer. */
    public static class MoveFunctionSerializer implements JsonSerializer<MoveFunction> {

        @Override
        public JsonElement serialize(
                MoveFunction src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("package", src.getSuiPackage());
            jsonObject.addProperty("module", src.getModule());
            jsonObject.addProperty("function", src.getFunction());
            return jsonObject;
        }
    }

    /** The type Package event filter serializer. */
    public static class PackageEventFilterSerializer
            implements JsonSerializer<EventFilter.PackageEventFilter> {

        @Override
        public JsonElement serialize(
                PackageEventFilter src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Package", src.getSuiPackage());
            return jsonObject;
        }
    }

    /** The type Committee info deserializer. */
    public static class CommitteeInfoDeserializer implements JsonDeserializer<Validator> {

        @Override
        public Validator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            List<JsonElement> committeeInfoStr = json.getAsJsonArray().asList();
            Validator validator = new Validator();
            validator.setAuthorityName(committeeInfoStr.get(0).getAsString());
            validator.setStakeUnit(committeeInfoStr.get(1).getAsBigInteger());
            return validator;
        }
    }

    /** The type Move normalized function type deserializer. */
    public class MoveNormalizedFunctionTypeDeserializer
            implements JsonDeserializer<MoveNormalizedFunction> {

        @Override
        public MoveNormalizedFunction deserialize(
                JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            MoveNormalizedFunction moveNormalizedFunction = new MoveNormalizedFunction();
            moveNormalizedFunction.setEntry(json.getAsJsonObject().get("isEntry").getAsBoolean());
            moveNormalizedFunction.setTypeParameters(
                    gson.fromJson(
                            json.getAsJsonObject().get("typeParameters"),
                            new com.google.common.reflect.TypeToken<List<MoveAbilitySet>>() {
                            }.getType()));
            moveNormalizedFunction.setParameters(
                    gson.fromJson(
                            json.getAsJsonObject().get("parameters"),
                            new com.google.common.reflect.TypeToken<List<MoveNormalizedType>>() {
                            }.getType()));
            moveNormalizedFunction.setVisibility(
                    gson.fromJson(json.getAsJsonObject().get("visibility"), MoveVisibility.class));
            moveNormalizedFunction.setReturnType(
                    gson.fromJson(
                            json.getAsJsonObject().get("return"),
                            new com.google.common.reflect.TypeToken<List<MoveNormalizedType>>() {
                            }.getType()));
            return moveNormalizedFunction;
        }
    }

    /** The type Move normalized type deserializer. */
    public class MoveNormalizedTypeDeserializer implements JsonDeserializer<MoveNormalizedType> {

        @Override
        public MoveNormalizedType deserialize(
                JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json.isJsonPrimitive()) {
                return TypeMoveNormalizedType.valueOf(json.getAsString());
            }
            if (json.isJsonObject()) {
                if (json.getAsJsonObject().get("TypeParameter") != null
                        && !json.getAsJsonObject().get("TypeParameter").isJsonNull()) {
                    return gson.fromJson(json, MoveNormalizedTypeParameterType.class);
                }
                if (json.getAsJsonObject().get("Reference") != null
                        && !json.getAsJsonObject().get("Reference").isJsonNull()) {
                    return gson.fromJson(json, ReferenceMoveNormalizedType.class);
                }
                if (json.getAsJsonObject().get("MutableReference") != null
                        && !json.getAsJsonObject().get("MutableReference").isJsonNull()) {
                    return gson.fromJson(json, MutableReferenceMoveNormalizedType.class);
                }
                if (json.getAsJsonObject().get("Vector") != null
                        && !json.getAsJsonObject().get("Vector").isJsonNull()) {
                    return gson.fromJson(json, VectorReferenceMoveNormalizedType.class);
                }
                if (json.getAsJsonObject().get("Struct") != null
                        && !json.getAsJsonObject().get("Struct").isJsonNull()) {
                    return gson.fromJson(json, MoveNormalizedStructType.class);
                }
            }
            return null;
        }
    }

    /** The type Move function arg type deserializer. */
    public class MoveFunctionArgTypeDeserializer implements JsonDeserializer<MoveFunctionArgType> {

        @Override
        public MoveFunctionArgType deserialize(
                JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json.isJsonPrimitive()) {
                return PureFunctionMoveFunctionArgType.Pure;
            }

            if (json.isJsonObject()) {
                return gson.fromJson(json, ObjectValueKindMoveFunctionArgType.class);
            }

            return null;
        }
    }

    /** The type Type tag serializer. */
    public static class TypeTagSerializer implements JsonSerializer<TypeTag> {

        @Override
        public JsonElement serialize(TypeTag src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }

    public class BalanceChangeOwnerDeserializer implements JsonDeserializer<BalanceChange.Owner> {

        @Override
        public BalanceChange.Owner deserialize(JsonElement json, Type typeOfT,
                                               JsonDeserializationContext context)
                throws JsonParseException {
            JsonElement addressOwner = json.getAsJsonObject().get("AddressOwner");
            if (addressOwner == null) {
                return null;
            }
            BalanceChange.Owner owner = new BalanceChange.Owner();
            owner.setAddressOwner(addressOwner.getAsString());
            return owner;
        }
    }

    private final Gson gson;

    /** Instantiates a new Gson json handler. */
    public GsonJsonHandler() {
        this.gson =
                new GsonBuilder()
                        .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                        .setNumberToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                        .registerTypeAdapter(
                                JsonRpc20Response.Error.ErrorCode.class, new ErrorCodeDeserializer())
                        .registerTypeAdapter(OneChainObjectOwner.class, new SuiObjectOwnerDeserializer())
                        .registerTypeAdapter(OneChainRawData.class, new SuiRawDataDeserializer())
                        .registerTypeAdapter(MoveCall.class, new MoveCallDeserializer())
                        .registerTypeAdapter(TransactionKind.Input.class, new TransactionKindInputDeserializer())
                        .registerTypeAdapter(MoveModule.class, new MoveModuleSerializer())
                        .registerTypeAdapter(MoveNormalizedType.class, new MoveNormalizedTypeDeserializer())
                        .registerTypeAdapter(Validator.class, new CommitteeInfoDeserializer())
                        .registerTypeAdapter(MoveFunctionArgType.class, new MoveFunctionArgTypeDeserializer())
                        .registerTypeAdapter(MoveFunction.class, new MoveFunctionSerializer())
                        .registerTypeAdapter(StructType.class, new TypeTagSerializer())
                        .registerTypeAdapter(VectorType.class, new TypeTagSerializer())
                        .registerTypeAdapter(TypeTag.class, new TypeTagSerializer())
                        .registerTypeAdapter(
                                EventFilter.PackageEventFilter.class, new PackageEventFilterSerializer())
                        .registerTypeAdapter(Argument.class, new SuiArgumentDeserializer())
                        .registerTypeAdapter(Command.class, new SuiCommandDeserializer())
                        .registerTypeAdapter(ObjectChange.class, new ObjectChangeDeserializer())
                        .registerTypeAdapter(OneChainParsedData.class, new SuiParsedDataDeserializer())
                        .registerTypeAdapter(BigInteger.class, TypeAdapters.BIG_INTEGER)
                        .registerTypeAdapter(BalanceChange.Owner.class, new BalanceChangeOwnerDeserializer())
                        .registerTypeAdapter(MoveNormalizedFunction.class,
                                             new MoveNormalizedFunctionTypeDeserializer())
                        .create();
    }

    @Override
    public <T> JsonRpc20Response<T> fromJson(String response, Type typeOfT) {
        Type type = TypeToken.getParameterized(JsonRpc20Response.class, typeOfT).getType();
        return this.gson.fromJson(response, type);
    }

    @Override
    public JsonRpc20WSResponse<?> fromWSJson(String response, Type typeOfT) {
        Type type = TypeToken.getParameterized(JsonRpc20WSResponse.class, typeOfT).getType();
        return this.gson.fromJson(response, type);
    }

    @Override
    public FaucetResponse fromJsonFaucet(String response) {
        return this.gson.fromJson(response, FaucetResponse.class);
    }

    @Override
    public Map<String, Object> fromJsonMap(String json) {
        return this.gson.fromJson(
                json, new com.google.common.reflect.TypeToken<Map<String, Object>>() {
                }.getType());
    }

    @Override
    public String toJson(JsonRpc20Request request) {
        return this.gson.toJson(request);
    }
}
