package io.onechain.hchain.domain.info.did;

import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lhb
 */
@Data
public class WalletAssetsInfo {

    @ApiModelProperty("资产")
    private String currency;

    @ApiModelProperty("钱包金额")
    private BigDecimal amount;

    @ApiModelProperty("usd钱包金额")
    private BigDecimal usdAmount;


}
