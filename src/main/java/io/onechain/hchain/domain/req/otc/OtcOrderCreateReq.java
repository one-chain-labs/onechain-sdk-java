package io.onechain.hchain.domain.req.otc;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Author: Bin
 * Date: 2025/3/10 17:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户订单-创建订单")
public class OtcOrderCreateReq  extends BaseUserReq {

    @ApiModelProperty(value = "广告id",required = true)
    @NotEmpty
    private String advertiseId;

    @ApiModelProperty(value = "价格",required = true)
    @NotNull
    private BigDecimal price;

    @ApiModelProperty(value = "支付(出售)金额",required = true)
    @NotNull
    private BigDecimal payMoney;

    @ApiModelProperty(value = "收到金额",required = true)
    @NotNull
    private BigDecimal receiveMoney;

    @ApiModelProperty(value = "计算标准,1-支付(出售)金额, 2-收到金额",required = true)
    @NotNull
    private Integer calculateStandard;
}
