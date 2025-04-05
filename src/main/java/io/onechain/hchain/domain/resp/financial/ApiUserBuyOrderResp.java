package io.onechain.hchain.domain.resp.financial;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Lhb
 * @version v0.0.1 2025-03-04 11:34:31
 */
@ApiModel("用户购买订单信息")
@Data
public class ApiUserBuyOrderResp {

    @ApiModelProperty("币种")
    private String currency;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("领取状态")
    private Integer receiveStatus;

    @ApiModelProperty("购买金额")
    private String buyAmount;

    @ApiModelProperty("购买时间")
    private LocalDateTime buyTime;

    @ApiModelProperty("可领取收益金额")
    private String waitReceiveIncomeAmount;

    @ApiModelProperty("已领取收益金额")
    private String receivedIncomeAmount;
}
