package io.onechain.hchain.domain.resp.financial;

import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Author: Bin
 * Date: 2025/3/14 15:03
 */
@Data
@Accessors(chain = true)
public class ApiOrderDetailsResp {

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("购买时间")
    private LocalDateTime buyTime;

    @ApiModelProperty("计息锁定时间")
    private LocalDateTime incomeLockEndTime;

    @ApiModelProperty("开始收益的时间")
    private LocalDateTime incomeStartTime;

    @ApiModelProperty("订单状态:10-待支付,20-支付中,40-支付失败,50-已取消,70-持仓中,80-赎回中,90-赎回完成,100-赎回失败")
    private Integer status;

    @ApiModelProperty("币种")
    private String currency;

    @ApiModelProperty("业务编号")
    private String orderNo;

    @ApiModelProperty("购买数量")
    private String buyAmount;

    @ApiModelProperty("账单时间")
    private LocalDateTime createTime;

    @ApiModelProperty("购买地址-支付地址")
    private String buyAddress;

    @ApiModelProperty("累计总收益")
    private String totalIncomeAmount;

    @ApiModelProperty("赎回状态:79-未申请,80-赎回中,90-赎回完成,100-赎回失败")
    private Integer ransomStatus;

    @ApiModelProperty("支付ID")
    private String paymentId;

    @ApiModelProperty("支付币种")
    private String paymentCurrency;

    @ApiModelProperty("支付金额")
    private String paymentAmount;

}
