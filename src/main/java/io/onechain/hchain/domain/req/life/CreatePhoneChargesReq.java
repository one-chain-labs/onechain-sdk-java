package io.onechain.hchain.domain.req.life;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import io.onechain.hchain.domain.req.common.BaseUserReq;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Lhb
 * @version v0.0.1 2025-03-10 15:08:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("创建话费充值单请求")
public class CreatePhoneChargesReq extends BaseUserReq {

    @ApiModelProperty("手机号码")
    @NotBlank(message = "phoneNumber is not blank")
    private String phoneNumber;

    @ApiModelProperty("充值金额")
    @NotBlank(message = "amount is not blank")
    private String amount;

    @ApiModelProperty("充值币种")
    @NotBlank(message = "coin is not blank")
    private String coin;
}
