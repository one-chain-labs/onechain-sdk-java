package io.onechain.hchain.domain.req.otc;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Author: Bin
 * Date: 2025/3/10 17:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "用户订单-订单支付")
public class OtcOrderPayBuyReq extends BaseUserReq {

    @ApiModelProperty(value = "订单编号", required = true)
    @NotEmpty
    private String orderNo;

}
