package io.onechain.hchain.domain.resp.financial;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Author: Bin
 * Date: 2025/3/14 14:52
 */
@ApiModel("创建收益订单响应")
@Data
@Accessors(chain = true)
public class ApiWithdrawIncomeOrderResp {

    @ApiModelProperty(value = "可领取收益金额")
    private String waitReceiveIncomeAmount;

    @ApiModelProperty("币种")
    private String currency;

    @ApiModelProperty("收益地址")
    private String incomeAddress;

    @ApiModelProperty("提取收益业务订单号")
    private String orderNo;

    @ApiModelProperty("待签名交易数据")
    private String rawTransaction;

    @ApiModelProperty("订单签名过期时间")
    private LocalDateTime expirations;

}
