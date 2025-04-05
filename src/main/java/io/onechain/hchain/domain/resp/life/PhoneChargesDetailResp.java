package io.onechain.hchain.domain.resp.life;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-10 17:23:37
 */
@Data
@ApiModel("话费充值单详情")
public class PhoneChargesDetailResp {

    @ApiModelProperty("uuid")
    private String uuid;

    @ApiModelProperty("充值手机号码")
    private String phoneNumber;

    @ApiModelProperty("运营商类型")
    private String carrier;

    @ApiModelProperty("充值币种")
    private String coin;

    @ApiModelProperty("金额")
    private String amount;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("完成时间")
    private Long completeTime;

    @ApiModelProperty("交易hash")
    private String transactionHash;

    @ApiModelProperty("订单状态")
    private String orderStatus;
}
