package io.onechain.bcsgen;


import com.google.common.base.Objects;
import com.novi.serde.DeserializationError;
import com.novi.serde.SerializationError;

public final class ZkLoginSignatureInputs {

    public final ZkLoginSignatureInputsProofPoints proofPoints;
    public final ZkLoginSignatureInputsClaim issBase64Details;

    public final String headerBase64;
    public final String addressSeed;


    public ZkLoginSignatureInputs(ZkLoginSignatureInputsProofPoints proofPoints, ZkLoginSignatureInputsClaim issBase64Details, String headerBase64, String addressSeed) {
        java.util.Objects.requireNonNull(proofPoints, "proofPoints must not be null");
        java.util.Objects.requireNonNull(issBase64Details, "issBase64Details must not be null");
        java.util.Objects.requireNonNull(headerBase64, "headerBase64 must not be null");
        java.util.Objects.requireNonNull(addressSeed, "addressSeed must not be null");
        this.proofPoints = proofPoints;
        this.issBase64Details = issBase64Details;
        this.headerBase64 = headerBase64;
        this.addressSeed = addressSeed;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws SerializationError {
        serializer.increase_container_depth();
        proofPoints.serialize(serializer);
        issBase64Details.serialize(serializer);
        serializer.serialize_str(headerBase64);
        serializer.serialize_str(addressSeed);
        serializer.decrease_container_depth();
    }

    public byte[] bcsSerialize() throws SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bcs.BcsSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static ZkLoginSignatureInputs deserialize(com.novi.serde.Deserializer deserializer) throws DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.proofPoints = ZkLoginSignatureInputsProofPoints.deserialize(deserializer);
        builder.issBase64Details = ZkLoginSignatureInputsClaim.deserialize(deserializer);
        builder.headerBase64 = deserializer.deserialize_str();
        builder.addressSeed = deserializer.deserialize_str();
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static ZkLoginSignatureInputs bcsDeserialize(byte[] input) throws DeserializationError {
        if (input == null) {
            throw new DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bcs.BcsDeserializer(input);
        ZkLoginSignatureInputs value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZkLoginSignatureInputs that = (ZkLoginSignatureInputs) o;
        return Objects.equal(proofPoints, that.proofPoints) && Objects.equal(issBase64Details, that.issBase64Details) && Objects.equal(headerBase64, that.headerBase64) && Objects.equal(addressSeed, that.addressSeed);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(proofPoints, issBase64Details, headerBase64, addressSeed);
    }

    @Override
    public String toString() {
        return "ZkLoginSignatureInputs{" +
                "proofPoints=" + proofPoints +
                ", issBase64Details=" + issBase64Details +
                ", headerBase64='" + headerBase64 + '\'' +
                ", addressSeed='" + addressSeed + '\'' +
                '}';
    }


    public static final class Builder {

        public ZkLoginSignatureInputsProofPoints proofPoints;
        public ZkLoginSignatureInputsClaim issBase64Details;
        public String headerBase64;
        public String addressSeed;

        public Builder() {

        }

        public ZkLoginSignatureInputs build() {
            return new ZkLoginSignatureInputs(
                    proofPoints,
                    issBase64Details,
                    headerBase64,
                    addressSeed
            );
        }
    }




}
