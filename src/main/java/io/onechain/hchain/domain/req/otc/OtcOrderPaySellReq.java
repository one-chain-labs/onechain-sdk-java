package io.onechain.hchain.domain.req.otc;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Author: Bin
 * Date: 2025/3/10 17:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "用户订单-支付订单(出售)")
public class OtcOrderPaySellReq extends BaseUserReq {

    @ApiModelProperty(value = "订单号", required = true)
    @NotEmpty
    private String orderNo;

    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty
    private String password;

    @ApiModelProperty(value = "签名", required = true)
    @NotEmpty
    private String sign;

    @ApiModelProperty(value = "待签名交易", required = true)
    @NotEmpty
    private String rawTransaction;
}
