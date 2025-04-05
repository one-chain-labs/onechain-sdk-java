package io.onechain.hchain.domain.req.common;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Author: Bin
 * Date: 2025/3/14 14:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("只要一个订单号的通用请求")
public class OrderNoReq extends TypeBaseUserReq {

    @ApiModelProperty(value = "订单编号/提取收益业务订单号/赎回业务订单号",required = true)
    @NotBlank(message = "orderNo must not be blank")
    private String orderNo;
}
