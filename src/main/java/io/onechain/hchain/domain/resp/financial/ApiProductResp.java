package io.onechain.hchain.domain.resp.financial;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author: Bin
 * Date: 2025/3/14 11:44
 */
@Data
@ApiModel("理财产品")
public class ApiProductResp {

    @ApiModelProperty("产品ID")
    private String productId;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("币种")
    private String currency;

    @ApiModelProperty("日收益率")
    private String incomeRate;

    @ApiModelProperty("赎回锁定期")
    private Long lockPeriod;

    @ApiModelProperty("用户余额")
    private String balance;

    @ApiModelProperty("单笔最小限制金额")
    private String minLimit;
}
