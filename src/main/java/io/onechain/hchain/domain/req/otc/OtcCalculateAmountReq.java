package io.onechain.hchain.domain.req.otc;

import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Author: Bin
 * Date: 2025/3/11 10:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OtcCalculateAmountReq extends BaseUserReq {

    @ApiModelProperty(value = "用于计算的金额", required = true)
    @NotNull
    @DecimalMin(value = "0.000000001", message = "金额不能小于0")
    private BigDecimal amount;

    @ApiModelProperty(value = "支付(出售)金额(用于后台计算)", hidden = true)
    private BigDecimal payMoney;

    @ApiModelProperty(value = "收到金额(用于后台计算)", hidden = true)
    private BigDecimal receiveMoney;

    @ApiModelProperty(value = "支付(出售)币种", required = true)
    @NotEmpty
    private String payMoneyCurrency;

    @ApiModelProperty(value = "收到币种", required = true)
    @NotEmpty
    private String receiveMoneyCurrency;

    @ApiModelProperty(value = "用户交易类型，1-购买，2-出售", required = true)
    @NotNull
    private Integer userTradeType;

    @ApiModelProperty(value = "价格", required = true)
    @NotNull
    private BigDecimal price;

    @ApiModelProperty(value = "计算标准,1-支付(出售)金额, 2-收到金额", required = true)
    @NotNull
    private Integer calculateStandard;
}
