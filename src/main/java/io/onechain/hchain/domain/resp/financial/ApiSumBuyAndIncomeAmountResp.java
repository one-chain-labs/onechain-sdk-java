package io.onechain.hchain.domain.resp.financial;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: Bin
 * Date: 2025/3/14 11:38
 */
@ApiModel("用户持仓金额和已提取收益总金额resp")
@Data
public class ApiSumBuyAndIncomeAmountResp {

    @ApiModelProperty(value = "总持仓金额")
    private BigDecimal buyAmount;

    @ApiModelProperty(value = "总领取收益金额")
    private BigDecimal totalIncomeAmount;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "代币类型")
    private String coinType;

    @ApiModelProperty(value = "产品ID")
    private String productId;
}
