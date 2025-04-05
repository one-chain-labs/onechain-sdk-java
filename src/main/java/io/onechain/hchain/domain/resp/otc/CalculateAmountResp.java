package io.onechain.hchain.domain.resp.otc;

import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Author: Bin
 * Date: 2025/3/11 10:52
 */
@Data
@Accessors(chain = true)
public class CalculateAmountResp {

    @ApiModelProperty("显示金额")
    private String showAmount;

    @ApiModelProperty("计算金额")
    private String calculateAmount;

    @ApiModelProperty("币种")
    private String currencyCode;
}
