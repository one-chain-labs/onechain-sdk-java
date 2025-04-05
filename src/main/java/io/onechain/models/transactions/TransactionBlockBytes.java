package io.onechain.models.transactions;

import com.google.gson.JsonObject;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Lhb
 * @version v0.0.1 2024-12-19 17:14:00
 */
@Data
public class TransactionBlockBytes {

    private String txBytes;

    private List<Gas> gas;

    private List<JsonObject> inputObjects;

    @Data
    public static class Gas {
        private String objectId;
        private BigInteger version;
        private String digest;
    }
}
