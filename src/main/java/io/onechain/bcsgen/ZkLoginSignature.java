package io.onechain.bcsgen;


import com.google.common.base.Objects;
import com.novi.serde.Bytes;
import com.novi.serde.DeserializationError;
import com.novi.serde.SerializationError;
import io.onechain.crypto.SignatureScheme;
import io.onechain.models.objects.ZKLoginData;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public final class ZkLoginSignature {

    public final ZkLoginSignatureInputs inputs;
    public final Long maxEpoch;
    public final Bytes userSignature;


    public ZkLoginSignature(ZkLoginSignatureInputs inputs, Long maxEpoch, Bytes userSignature) {
        java.util.Objects.requireNonNull(inputs, "inputs must not be null");
        java.util.Objects.requireNonNull(maxEpoch, "maxEpoch must not be null");
        java.util.Objects.requireNonNull(userSignature, "userSignature must not be null");
        this.inputs = inputs;
        this.maxEpoch = maxEpoch;
        this.userSignature = userSignature;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws SerializationError {
        serializer.increase_container_depth();
        serializer.serialize_variant_index(SignatureScheme.ZkLogin.getScheme());
        inputs.serialize(serializer);
        serializer.serialize_u64(maxEpoch);
        serializer.serialize_bytes(userSignature);
        serializer.decrease_container_depth();
    }

    public byte[] bcsSerialize() throws SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bcs.BcsSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static ZkLoginSignature deserialize(com.novi.serde.Deserializer deserializer) throws DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        int type = deserializer.deserialize_variant_index();
        if (SignatureScheme.ZkLogin.getScheme() == type) {
            builder.inputs = ZkLoginSignatureInputs.deserialize(deserializer);
            builder.maxEpoch = deserializer.deserialize_u64();
        }
        builder.userSignature = deserializer.deserialize_bytes();
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static ZkLoginSignature bcsDeserialize(byte[] input) throws DeserializationError {
        if (input == null) {
            throw new DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bcs.BcsDeserializer(input);
        ZkLoginSignature value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZkLoginSignature that = (ZkLoginSignature) o;
        return Objects.equal(inputs, that.inputs) && Objects.equal(maxEpoch, that.maxEpoch) && Objects.equal(userSignature, that.userSignature);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(inputs, maxEpoch, userSignature);
    }

    @Override
    public String toString() {
        return "ZkLoginSignature{" +
                "inputs=" + inputs +
                ", maxEpoch=" + maxEpoch +
                ", userSignature=" + userSignature +
                '}';
    }


    public static final class Builder {

        public ZkLoginSignatureInputs inputs;
        public Long maxEpoch;
        public Bytes userSignature;

        public Builder() {
        }

        public ZkLoginSignature build() {
            return new ZkLoginSignature(
                    inputs,
                    maxEpoch,
                    userSignature
            );
        }
    }

    public static ZkLoginSignature createZkLoginSignature(ZKLoginData zkLoginData, byte[] userSignature, Long maxEpoch, String addressSeed) {
        Builder builder = new Builder();
        builder.userSignature = Bytes.valueOf(userSignature);
        builder.maxEpoch = maxEpoch;
        builder.inputs = getInput(zkLoginData, addressSeed);
        return builder.build();
    }

    private static ZkLoginSignatureInputs getInput(ZKLoginData zkLoginData, String addressSeed) {
        ZkLoginSignatureInputs.Builder inputs = new ZkLoginSignatureInputs.Builder();
        inputs.addressSeed = addressSeed;
        inputs.headerBase64 = zkLoginData.getHeaderBase64();
        inputs.issBase64Details = getIssBase64Details(zkLoginData);
        inputs.proofPoints = getProofPoints(zkLoginData);
        return inputs.build();
    }

    private static ZkLoginSignatureInputsClaim getIssBase64Details(ZKLoginData zkLoginData) {
        ZkLoginSignatureInputsClaim.Builder issBase64Details = new ZkLoginSignatureInputsClaim.Builder();
        issBase64Details.indexMod4 = zkLoginData.getIssBase64Details().getIndexMod4();
        issBase64Details.value = zkLoginData.getIssBase64Details().getValue();
        return issBase64Details.build();
    }

    private static ZkLoginSignatureInputsProofPoints getProofPoints(ZKLoginData zkLoginData) {
        ZkLoginSignatureInputsProofPoints.Builder proofPoints = new ZkLoginSignatureInputsProofPoints.Builder();
        proofPoints.a = getZkPoint(zkLoginData.getProofPoints().getA());
        proofPoints.b = zkLoginData.getProofPoints().getB().stream().map(ZkLoginSignature::getZkPoint).collect(Collectors.toList());
        proofPoints.c = getZkPoint(zkLoginData.getProofPoints().getC());
        return proofPoints.build();
    }

    private static ZkPoint getZkPoint(List<String> point) {
        ZkPoint.Builder builder = new ZkPoint.Builder();
        builder.points = point;
        return builder.build();
    }

    public static void main(String[] args) throws SerializationError, DeserializationError {
        String zkloginSign = "BQNMNjMyMzkwMjk5NzAzNzAzMDA3ODI1MzI1MjgzNDgyOTE1ODUzOTU1MjA0MjYxMzM1MjI2NDAwNTIyNjY0NDk4MzQyMjg3MzI1NzUzMUw5MTYxNjA3NTQ5MDY2NzQyNjU3MTg3ODQyOTY2ODAwOTg2Mzc2OTE0OTI4NTc0NjY0MTcwMTg5MjQ0NDc1MzEzODY1MzE1NzA5ODg1ATEDAk0xNzc4OTgyNjYzNTg2MDgzMjEwNzE4NjE1ODUzMzM5NzYzNjIyNjQwMDcyMTg0NTY0Mjc1NzU4MTQ3NDk4NjYzMjE4NjM0NDI0MTg2NkwyNzYxNDgzOTM5MzYwOTU0MDMwODE2MTM4MDk4NjUzNjQ2OTAwMjk0ODk0MDkxODc3OTMxNzQyNDkzMTcyNTU1MzI0MDc1NjIxNDY5Ak0xOTg2MzM0MDk3MjU2NzEzNDYxNDA3ODEzOTkzNTk1OTU4MTY1MTg3MTI5NDI1NjY0OTQyNzEyNTExMTMzNTU2MDYzOTM1ODY2MjUyME0xMTI2OTI0NTAyNDU3MDc5MTk1MTgzNzE1ODI4MDA4NjgzMDUzMTA0NjMxMzE1ODUxODg5ODQ5MTUzOTcxOTIyMTA4OTE2MTIxNTU0OAIBMQEwA0w3MzcyNzIzNDc5NzIyMTA3MzEwMzg5MDYxODk4NDM5MjY4ODI1OTc5MjA0NjY5MTk4Nzc4Njc4NzU4Nzk5Nzc4Mjc5MDQwOTc3NDQ2TTEzNDA3NjU1NjYwOTc3MDgzNDk0MzU1NDc4MDE1NDE3MTU2MjY3OTYxODM5NDg0Njk5ODYyMTE3MDAwOTI4MzE1ODAwODI4MDcwNTUyATEud2lhWE56SWpvaWFIUjBjSE02THk5aFkyTnZkVzUwY3k1NGIyNWxMbU52YlNJcwIyZXlKaGJHY2lPaUpTVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0lzSW10cFpDSTZJakVpZlFNMTAzOTgxNDU3ODI0MjQ0MTIxMTU3NjIyNTUwNTY5ODYwMTYzMjEzOTc5MzIxODAxNzg4OTg5MTYzNzA4NDgyNDI4ODgyMjY5MDIxMTUSAAAAAAAAAGEAfvEE1hX8gYyVdwVVIb3ooon2VO9OuaxSSDcyXOz6sMi7h4ZCsjLTKmNaYgjL0YPra73EQNrzDDvt5Lgdk1W6CAmWdZ19FGp+Tru/Pudz+DKSGzM1npZZl53L3LcQztrb";
        byte[] decode = Base64.getDecoder().decode(zkloginSign);
        System.out.println(ZkLoginSignature.bcsDeserialize(decode));
        ;
    }


}
