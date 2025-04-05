package io.onechain.bcsgen;


import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.List;

public final class ZkLoginSignatureInputsProofPoints {

    public final ZkPoint a;

    public final List<ZkPoint> b;


    public final ZkPoint c;


    public ZkLoginSignatureInputsProofPoints(ZkPoint a, List<ZkPoint> b, ZkPoint c) {
        java.util.Objects.requireNonNull(a, "a must not be null");
        java.util.Objects.requireNonNull(b, "b must not be null");
        java.util.Objects.requireNonNull(c, "c must not be null");
        this.a = a;
        this.b = b;
        this.c = c;

    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        a.serialize(serializer);
        TraitHelpers.serialize_vector_point(b, serializer);
        c.serialize(serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bcsSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bcs.BcsSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static ZkLoginSignatureInputsProofPoints deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.a = ZkPoint.deserialize(deserializer);
        builder.b = TraitHelpers.deserialize_vector_point(deserializer);
        builder.c = ZkPoint.deserialize(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static ZkLoginSignatureInputsProofPoints bcsDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
            throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bcs.BcsDeserializer(input);
        ZkLoginSignatureInputsProofPoints value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZkLoginSignatureInputsProofPoints that = (ZkLoginSignatureInputsProofPoints) o;
        return Objects.equal(a, that.a) && Objects.equal(b, that.b) && Objects.equal(c, that.c);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(a, b, c);
    }

    @Override
    public String toString() {
        return "ZkLoginSignatureInputsProofPoints{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }


    public static final class Builder {
        public ZkPoint a;

        public List<ZkPoint> b;


        public ZkPoint c;

        public Builder() {
        }

        public ZkLoginSignatureInputsProofPoints build() {
            return new ZkLoginSignatureInputsProofPoints(
                    a,
                    b,
                    c
            );
        }
    }

    public static void main(String[] args) {
        Builder builder = new Builder();
        ZkPoint.Builder point = new ZkPoint.Builder();
        point.points = Lists.newArrayList("123", "333");
        ZkPoint pointBuild = point.build();
        builder.a = pointBuild;
        builder.b = Lists.newArrayList(pointBuild);
        builder.c = pointBuild;
        ZkLoginSignatureInputsProofPoints zks = builder.build();
        try {
            byte[] bytes = zks.bcsSerialize();
            ZkLoginSignatureInputsProofPoints zkLoginSignatureInputsProofPoints = ZkLoginSignatureInputsProofPoints.bcsDeserialize(bytes);
            System.out.println(zkLoginSignatureInputsProofPoints.a);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
