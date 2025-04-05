package io.onechain.hchain.domain.resp.financial;

import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author: Bin
 * Date: 2025/3/10 11:54
 */
@Data
public class ApiUserOrderBillDetailsResp {

    @ApiModelProperty("账单ID")
    private String billId;

    @ApiModelProperty("账单类型")
    private String type;

    @ApiModelProperty("账单子类型:BUY_ORDER-购买,INCOME_ORDER-收益,RANSOM_ORDER-赎回")
    private String subType;

    @ApiModelProperty("账单金额")
    private String amount;

    @ApiModelProperty("币种")
    private String currency;

    @ApiModelProperty("账单状态 10-待支付,20-支付中,30-支付成功,40-支付失败,50-已取消,60-计息锁定期,70-收益中,80-赎回中,90-赎回完成,100-赎回失败")
    private Integer status;

    @ApiModelProperty("业务订单号")
    private String bizOrderId;

    @ApiModelProperty("交易hash")
    private String transactionHash;

    @ApiModelProperty("购买地址")
    private String buyAddress;

    @ApiModelProperty("收益地址")
    private String incomeAddress;

    @ApiModelProperty("赎回地址")
    private String ransomAddress;

    @ApiModelProperty("交易时间")
    private Long transactionTime;

    @ApiModelProperty("完成时间")
    private Long completeTime;
}
