package io.onechain.bcsgen;


import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.novi.serde.DeserializationError;
import com.novi.serde.SerializationError;
import lombok.SneakyThrows;

import java.util.List;

public final class ZkLoginSignatureInputsClaim {

    public final String value;
    public final Byte indexMod4;


    public ZkLoginSignatureInputsClaim(String value, Byte indexMod4) {
        java.util.Objects.requireNonNull(value, "value must not be null");
        java.util.Objects.requireNonNull(indexMod4, "indexMod4 must not be null");
        this.value = value;
        this.indexMod4 = indexMod4;

    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        serializer.serialize_str(value);
        serializer.serialize_u8(indexMod4);
        serializer.decrease_container_depth();
    }

    public byte[] bcsSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bcs.BcsSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static ZkLoginSignatureInputsClaim deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.value = deserializer.deserialize_str();
        builder.indexMod4 = deserializer.deserialize_u8();
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static ZkLoginSignatureInputsClaim bcsDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
            throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bcs.BcsDeserializer(input);
        ZkLoginSignatureInputsClaim value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    @Override
    public String toString() {
        return "ZkLoginSignatureInputsClaim{" +
                "value='" + value + '\'' +
                ", indexMod4=" + indexMod4 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZkLoginSignatureInputsClaim that = (ZkLoginSignatureInputsClaim) o;
        return Objects.equal(value, that.value) && Objects.equal(indexMod4, that.indexMod4);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value, indexMod4);
    }


    public static final class Builder {

        public String value;
        public Byte indexMod4;

        public Builder() {
        }

        public ZkLoginSignatureInputsClaim build() {
            return new ZkLoginSignatureInputsClaim(
                    value,
                    indexMod4
            );
        }
    }

    public static void main(String[] args) throws SerializationError, DeserializationError {
        Builder builder = new Builder();
        builder.value = "123";
        builder.indexMod4 = 1;
        ZkLoginSignatureInputsClaim build = builder.build();
        System.out.println(ZkLoginSignatureInputsClaim.bcsDeserialize(build.bcsSerialize()));
    }


}
