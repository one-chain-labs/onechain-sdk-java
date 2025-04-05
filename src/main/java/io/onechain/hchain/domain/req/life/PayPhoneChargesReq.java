package io.onechain.hchain.domain.req.life;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Lhb
 * @version v0.0.1 2025-03-10 15:21:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("支付话费充值单")
public class PayPhoneChargesReq extends BaseUserReq {

    @ApiModelProperty("原始交易信息")
    @NotBlank(message = "rawTransaction is not blank")
    private String rawTransaction;

    @ApiModelProperty("签名交易信息")
    @NotBlank(message = "signature is not blank")
    private String signature;

    @ApiModelProperty("uuid")
    @NotBlank(message = "uuid is not blank")
    private String uuid;
}
