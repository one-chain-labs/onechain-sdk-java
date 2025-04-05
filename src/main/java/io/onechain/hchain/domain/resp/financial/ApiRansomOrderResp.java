package io.onechain.hchain.domain.resp.financial;

import io.onechain.hchain.annotations.ApiModel;
import io.onechain.hchain.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Author: Bin
 * Date: 2025/3/14 15:06
 */
@Data
@ApiModel("赎回订单响应")
@Accessors(chain = true)
public class ApiRansomOrderResp {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("状态：80-赎回中,90-赎回完成,100-赎回失败")
    private Integer status;

    @ApiModelProperty("购买金额")
    private String buyAmount;

    @ApiModelProperty("已领取金额")
    private String incomeAmount;

    @ApiModelProperty("购买时间")
    private LocalDateTime buyTime;

    @ApiModelProperty("币种")
    private String currency;
}
