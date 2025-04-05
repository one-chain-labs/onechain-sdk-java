package io.onechain.hchain.domain.resp.financial;

import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author: Bin
 * Date: 2025/3/10 11:52
 */
@Data
public class ApiUserOrderBillResp {

    @ApiModelProperty("账单ID")
    private String billId;

    @ApiModelProperty("账单类型:FINANCIAL_ORDER-理财")
    private String type;

    @ApiModelProperty("账单子类型:BUY_ORDER-购买,INCOME_ORDER-收益,RANSOM_ORDER-赎回")
    private String subType;

    @ApiModelProperty("账单金额")
    private String amount;

    @ApiModelProperty("币种")
    private String currency;

    @ApiModelProperty("账单状态")
    private Integer status;

    @ApiModelProperty("交易时间")
    private Long transactionTime;

    @ApiModelProperty("完成时间")
    private Long completeTime;
}
