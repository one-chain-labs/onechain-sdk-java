package io.onechain.bcsgen;


import com.google.common.base.Objects;

public final class ZkPoint {

    public final java.util.List<String> point;


    public ZkPoint(java.util.List<String> point) {
        java.util.Objects.requireNonNull(point, "point must not be null");
        this.point = point;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        TraitHelpers.serialize_vector_str(point, serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bcsSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bcs.BcsSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static ZkPoint deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.points = TraitHelpers.deserialize_vector_str(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static ZkPoint bcsDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
            throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bcs.BcsDeserializer(input);
        ZkPoint value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZkPoint zkPoint = (ZkPoint) o;
        return Objects.equal(point, zkPoint.point);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(point);
    }

    @Override
    public String toString() {
        return "ZkPoint{" +
                "point=" + point +
                '}';
    }


    public static final class Builder {
        public java.util.List<String> points;

        public ZkPoint build() {
            return new ZkPoint(
                    points
            );
        }
    }
}
