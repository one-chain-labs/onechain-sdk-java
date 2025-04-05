package io.onechain.hchain.domain.req.financial;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.OrderNoReq;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Lhb
 * @version v0.0.1 2025-03-04 14:53:32
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "赎回订单确认")
@Data
public class ApiRansomOrderConfirmReq extends OrderNoReq {

    @ApiModelProperty(value = "用户支付密码", required = true)
    @NotBlank(message = "password must not be blank")
    private String password;


    @ApiModelProperty(value = "用户签名", required = true)
    @NotBlank(message = "userSig must not be blank")
    private String userSig;
}
