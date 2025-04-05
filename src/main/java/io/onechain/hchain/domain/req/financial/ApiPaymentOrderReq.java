package io.onechain.hchain.domain.req.financial;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.TypeBaseUserReq;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Author: Bin
 * Date: 2025/3/14 14:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("支付订单请求")
public class ApiPaymentOrderReq extends TypeBaseUserReq {

    @ApiModelProperty(value = "业务订单号",required = true)
    @NotBlank(message = "orderNo must not be blank")
    private String orderNo;

    @NotBlank(message = "交易hash不能为空")
    @ApiModelProperty(value = "交易hash", required = true)
    private String hash;

    @NotBlank(message = "数字签名字符串不能为空")
    @ApiModelProperty(value = "数字签名", required = true)
    private String userSign;

    @ApiModelProperty("代签名交易字符串")
    private String txBytes;
}
