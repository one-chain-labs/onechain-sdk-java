package io.onechain.models.transactions;

import com.google.gson.JsonElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * 适配新的解析交易
 *
 * @author Lhb
 * @version v0.0.1 2024-12-16 17:24:54
 */
@Data
public class TransactionKind {

    private String kind;

    private List<Input> inputs;

    private List<Map<String, JsonElement>> transactions;

    /**
     * 输入基础对象
     */

    @Data
    public static class Input {
        private String type;

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ObjectInput extends Input {
        private String objectType;
        private String objectId;
        private String initialSharedVersion;
        private Boolean mutable;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class PureInput extends Input {
        private String valueType;
        private JsonElement value;
    }

    public enum InputType {
        object, pure, move
    }

    @Data
    public static class MoveCall {
        private String module;
        private String function;
        private List<JsonElement> arguments;
    }
    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class fixMoveCall extends MoveCall {
        private String kPackage;
    }
}
