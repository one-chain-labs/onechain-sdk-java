package io.onechain.hchain.domain.resp.life;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-10 16:02:04
 */
@ApiModel("支付话费充值单响应")
@Data
public class PayPhoneChargesResp {

    @ApiModelProperty("交易hash")
    private String transactionHash;

    @ApiModelProperty("uuid")
    private String uuid;
}
