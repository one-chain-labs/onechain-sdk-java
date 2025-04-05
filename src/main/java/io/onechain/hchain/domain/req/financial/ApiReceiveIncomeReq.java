package io.onechain.hchain.domain.req.financial;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.OrderNoReq;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Author: Bin
 * Date: 2025/3/14 14:55
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("确认领取收益REQ")
@Data
public class ApiReceiveIncomeReq extends OrderNoReq {

    @ApiModelProperty(value = "用户签名", required = true)
    @NotBlank(message = "userSig must not be blank")
    private String userSig;

    @ApiModelProperty(value = "用户支付密码", required = true)
    @NotBlank(message = "password must not be blank")
    private String password;
}
