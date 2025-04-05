package io.onechain.hchain.domain.req.common;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Lhb
 * @version v0.0.1 2025-03-04 10:32:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("支付兑汇订单请求")
public class PaymentOrderReq extends OrderIdReq {

    @NotBlank(message = "交易hash不能为空")
    @ApiModelProperty(value = "交易hash", required = true)
    private String hash;

    @NotBlank(message = "数字签名字符串不能为空")
    @ApiModelProperty(value = "数字签名", required = true)
    private String userSign;

    @NotBlank(message = "ReservationId不能为空")
    @ApiModelProperty(value = "ReservationId", required = true)
    private Long reservationId;

    @ApiModelProperty("代签名交易字符串")
    private String txBytes;
}
