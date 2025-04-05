package io.onechain.hchain.domain.req.financial;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.TypeBaseUserReq;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Author: Bin
 * Date: 2025/3/14 11:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("创建理财订单")
public class ApiCreateOrderReq extends TypeBaseUserReq {

    @ApiModelProperty(value = "产品ID",required = true)
    @NotBlank(message = "productId must not be blank")
    private String productId;

    @ApiModelProperty(value = "币种",required = true)
    @NotBlank(message = "currency must not be blank ")
    private String currency;

    @ApiModelProperty(value = "购买金额",required = true)
    @NotBlank(message = "buyAmount must not be blank ")
    private String buyAmount;
}
