package io.onechain.models.objects;

import lombok.Data;

/**
 * @author Lhb
 * @version v0.0.1 2024-12-17 09:52:03
 */
@Data
public class BalanceChange {

    private Owner owner;

    private String coinType;

    private String amount;

    @Data
    public static class Owner {

        private String addressOwner;
    }
}
